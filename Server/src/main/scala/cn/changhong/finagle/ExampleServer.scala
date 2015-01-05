package cn.changhong.finagle

import java.net.InetSocketAddress
import java.util.UUID
import java.util.concurrent.{Executors, Executor, TimeUnit}
import cn.changhong.finagle.ExampleServer.{RestService, HttpTimeFilter, HeaderFilter, ExceptionFilter}
import com.google.common.base.Splitter
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import com.twitter.finagle.service.TimeoutFilter
import com.twitter.finagle.{Server, GlobalRequestTimeoutException, Service, SimpleFilter}
import com.twitter.util.{FuturePool, Duration, FutureTransformer, Future}
import io.netty.util.CharsetUtil
import org.codehaus.jackson.map.ObjectMapper
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.handler.codec.http._

import com.twitter.finagle.util.DefaultTimer

/**
 * Created by yangguo on 14-10-21.
 */
object start extends App{
  object ExceptionFilter extends ExceptionFilter
  object HeaderFilter extends HeaderFilter
  object HttpTimeFilter extends HttpTimeFilter(Duration(10,TimeUnit.SECONDS))
  object RestService extends RestService
  val service=ExceptionFilter andThen HeaderFilter andThen HttpTimeFilter andThen RestService

  val server=ServerBuilder()
    .codec(Http())
    .bindTo(new InetSocketAddress(8888))
    .name("httpserver")
    .build(service)

}
object ExampleServer {
  class ExceptionFilter extends SimpleFilter[HttpRequest,HttpResponse]{
    val transformer=
      new FutureTransformer[HttpResponse,HttpResponse] {
        override def map(value: HttpResponse): HttpResponse = value
        override def handle(throwable: Throwable): HttpResponse = Responses.InternaleServerError(throwable.getStackTraceString)
      }
    override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = {
      println("exceptionfilter run...")
      service(request).transformedBy(transformer)
    }
  }

  class HeaderFilter extends SimpleFilter[HttpRequest,HttpResponse]{
    override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = {
      println("HeaderFilter run...")
      val id=request .getHeader("X-Request-ID")
      if(id == null){
        request.setHeader("X-Request-ID",UUID.randomUUID().toString)
      }
      service(request).onSuccess{r=>
        r.addHeader("X-Processed","TRUE")
      }
    }
  }

  class HttpTimeFilter(timeout:Duration) extends TimeoutFilter[HttpRequest,HttpResponse](timeout,new GlobalRequestTimeoutException((timeout)), DefaultTimer.twitter){
  }

  class RestService extends Service[HttpRequest,HttpResponse]{
    val okService=new SimpleService()
    val timeoutService=new SimpleService(Some(1200))
    val futurePool=FuturePool(Executors.newFixedThreadPool(4))
    val splitter=Splitter.on('/').omitEmptyStrings()
    override def apply(request: HttpRequest): Future[HttpResponse] = {
      val path=splitter.split(request.getUri).iterator()
      println("Rest service run...>>>"+request.getUri)
      if(path.hasNext){
        path.next() match {
          case "OK"=>futurePool{
            Responses.Ok(request,(req:HttpRequest)=>{okService(req.getUri)})
          }
          case "timeout"=>futurePool(Responses.Ok(request,(req:HttpRequest)=>{timeoutService(req.getUri)}))
          case _=>Future.value(Responses.NotFound)
        }
      }
      null
    }

  }

  class SimpleService(waitFor:Option[Int]=None){
    def apply(name:String)={
      waitFor.foreach{t=>this.synchronized{wait(t)}}
      name
    }
  }

  object Responses{
    val mapper=new ObjectMapper()

    private def respond(status:HttpResponseStatus,content:ChannelBuffer):HttpResponse={
      val response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,status)
      response.setHeader("Content-Type","application/json")
      response.setHeader("Cache-Control","no-cache")
      response.setContent(content)
      response
    }
    object Ok{
      def apply(req:HttpRequest,service:(HttpRequest)=>Object):HttpResponse=
        respond(HttpResponseStatus.OK,ChannelBuffers.copiedBuffer(mapper.writeValueAsBytes(service(req))))
    }
    object NotFound{
      def apply():HttpResponse={
        respond(HttpResponseStatus.NOT_FOUND,ChannelBuffers.copiedBuffer("\"status\":\"NOT_FOUND\"",CharsetUtil.UTF_8))
      }
    }
    object InternaleServerError{
      def apply(message:String):HttpResponse={
        respond(HttpResponseStatus.INTERNAL_SERVER_ERROR,ChannelBuffers.copiedBuffer("\"status\":\"INTERNAL_SERVER_ERROR\",\"message\":\""+message+"\"",CharsetUtil.UTF_8))
      }
    }


  }
}

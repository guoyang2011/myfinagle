package cn.changhong.finagle.http

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import com.google.gson.Gson
import com.twitter.finagle.{SimpleFilter, Service}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Response, Http, Method}
import com.twitter.util.{FutureTransformer, Duration, Future}
import io.netty.util.CharsetUtil
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._


/**
 * Created by yangguo on 14-10-23.
 */
object HttpRestServer {
  implicit class Regex(sc: StringContext) {
    def r = new util.matching.Regex(sc.parts.mkString, sc.parts.tail.map(_ => "x"): _*)
  }


  def main(args:Array[String]):Unit={
    val service=DefautExceptionHandler andThen AuthService andThen DefaultRoutorService
    val server=ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(8887))
      .readTimeout(Duration(1,TimeUnit.SECONDS))
      .name("test")
      .build(service)
  }
  private[this] object ChCareDefaultRestService {
    object AccountService extends Service[HttpRequest, Any] {
      override def apply(request: HttpRequest): Future[Any] = {
        println("AccountService...")
        Future.value(User(request.getUri,request.getMethod.toString))
      }

      private[this] case class GetUser(userid:String) extends Service[HttpRequest,User] {
        override def apply(request: HttpRequest): Future[User] = {
          Future.value(User(userid,"password"))
        }
      }

    }

    object FamilyService extends Service[HttpRequest, HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = ???
    }

    object NoSmartPhoneService extends Service[HttpRequest, HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = ???
    }

    object LocationService extends Service[HttpRequest, HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = ???
    }

    object OfflineMessageService extends Service[HttpRequest, HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = ???
    }

    object HealthService extends Service[HttpRequest, HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = ???
    }

    object FileService extends Service[HttpRequest, HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = ???
    }

    object UtilService extends Service[HttpRequest, Any] {
      override def apply(request: HttpRequest): Future[Any] = Future.value{
        "not find resource!"+request.getUri
      }
    }
  }
  private[this] object DefautExceptionHandler extends SimpleFilter[HttpRequest,HttpResponse] {

    override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = {
      service.apply(request) handle(causeException)
    }
    case class DefaultHttpExceptionResponse(content:String) extends DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK){
      this.setContent(ChannelBuffers.copiedBuffer(content,CharsetUtil.UTF_8))
    }
    private[this] val causeException:PartialFunction[Throwable,HttpResponse]={
      case e:Exception=>DefaultHttpExceptionResponse(e.getStackTraceString)
    }
  }
  private[this] object AuthService extends SimpleFilter[HttpRequest,HttpResponse]{
    override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = {
//      request.getHeader(HttpHeaders.Names.AUTHORIZATION) match {

//        case "OAuth .*" => service(request)//token valid =>service(request)
//        case _ => ChCareDefaultRestService.UtilService(request)//判断是否为登陆服务请求，否请求资源非法，是跳转到DefaultRouterService
//      }
      DefaultRoutorService(request)
    }
  }
  object TransEntityToJsonObjHttpResponse {
    val gson=new Gson()
    def apply(request: Any): HttpResponse= {
          val response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)

          response.setContent(ChannelBuffers.copiedBuffer(gson.toJson(request),CharsetUtil.UTF_8))
          (response)

    }
  }
  private[this] object DefaultRoutorService extends Service[HttpRequest,HttpResponse] {
    val transformer=
      new FutureTransformer[Any,HttpResponse] {
        override def map(value: Any): HttpResponse = value match{
          case _=>{
            val startTime=System.currentTimeMillis()
            val result=TransEntityToJsonObjHttpResponse(value)
            println("total user time:"+(System.currentTimeMillis()-startTime))
            result
          }
        }
        override def handle(throwable: Throwable): HttpResponse = {
          val response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
          response.setContent(ChannelBuffers.copiedBuffer(throwable.getStackTraceString+"error",CharsetUtil.UTF_8))
          response
        }
      }

    override def apply(request: HttpRequest): Future[HttpResponse] = {
      BussinessService(request).transformedBy(transformer)
    }
  }
  private[this] object BussinessService extends Service[HttpRequest,Any]{
    override def apply(request: HttpRequest): Future[Any] = {
      try {
        println(request.getUri)
        request.getUri match {
          case r"/accountservice.*"=>ChCareDefaultRestService.AccountService(request)
          case r"/familyservice.*"=>ChCareDefaultRestService.FamilyService(request)
          case r"/nosmartphoneservice.*"=>ChCareDefaultRestService.NoSmartPhoneService(request)
          case _=>ChCareDefaultRestService.UtilService(request)//判断请求地址是否正确，否返回资源不存在
        }
      } catch {
        case e: Exception => Future.value(Response(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
      }
    }
  }
}

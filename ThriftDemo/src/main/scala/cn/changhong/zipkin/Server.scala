package cn.changhong.zipkin

import java.net.InetSocketAddress
import java.util.concurrent.Callable

import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.{Response, Http, RichHttp, Request}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.{Annotation, Trace}
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.HttpMethod

import scala.util.Random

/**
 * Created by yangguo on 14-12-25.
 */
object Server {
  lazy val tracer=ZipkinTracer.mk("10.9.52.31",9410,DefaultStatsReceiver,1)
  def doTracerServiceTask[R](service:String,method:String,callable:Callable[R]):R=Trace.unwind{
    Trace.pushTracerAndSetNextId(tracer,false)
    Trace.recordRpcname(service,method)
    Trace.record(Annotation.ClientSend())
    try{
      callable.call()
    }finally {
      Trace.record(Annotation.ClientRecv())
    }
  }
  def decoderTraceRequest(request:Request) {
    Trace.pushTracer(null)
    Client.decodeTracerHeader(request.headers()) foreach { id =>
      Trace.setId(id)
      if (Trace.isActivelyTracing) {
        Trace.record(Annotation.ServerRecv())
        Trace.recordBinary(request.getProtocolVersion().toString, request.getMethod().getName)
      }
    }
  }
  def main(args:Array[String]): Unit={
    val servicet=com.twitter.finagle.Http.newService("")
    val server=com.twitter.finagle.Http.serve("",servicet)
    if(args==null || args.length<2) throw new RuntimeException("server ip and port...")
//    val args=List("localhost","10000")
    val service=new Service[Request,Response](){
      override def apply(request: Request): Future[Response] = {

        decoderTraceRequest(request)
        Trace.pushTracerAndSetNextId(tracer,false)
        Trace.recordRpcname("secondary Service",HttpMethod.GET.getName)
        Trace.record(Annotation.ClientSend())
        Thread.sleep(new Random().nextLong()%1000)
        Trace.record(Annotation.ClientRecv())
        if(Trace.isActivelyTracing) Trace.record(Annotation.ServerSend())
        Future.value(Response())
      }
    }
    ServerBuilder()
      .codec(RichHttp[Request](Http().enableTracing(true)))
      .name("service_ok")
      .tracer(tracer)
      .bindTo(new InetSocketAddress(args(0),args(1).toInt))
      .reportTo(DefaultStatsReceiver)
      .build(service)
  }


}

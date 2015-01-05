package cn.changhong.core

import java.net.InetSocketAddress
import java.util.concurrent.Callable

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http._
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.{Annotation, Trace, Tracer}
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.util.Future
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.util.CharsetUtil

/**
 * Created by yangguo on 14-12-23.
 */
object Zipkin{
  lazy val  tracer=ZipkinTracer.mk(host="10.9.52.31",port = 1423,DefaultStatsReceiver,1)
  def invoke[R](service:String,method:String,callable:Callable[R]):R=Trace.unwind{
    Trace.pushTracerAndSetNextId(tracer,false)
    Trace.recordServiceName(service)
    Trace.recordRpc(method)
    Trace.record(new Annotation.ClientSend())
    try{
      callable.call()
    }finally {
      Trace.record(new Annotation.ClientRecv)
    }
  }
}
object Zpkdemo {
  val stats=DefaultStatsReceiver
  val tracer=ZipkinTracer.mk(host="10.9.52.31",port = 1463,statsReceiver = stats,sampleRate =1.0F)//("10.9.52.31",1463,1.0F)
  def main(args:Array[String]): Unit ={
    ServerBuilder()
      .codec(RichHttp[Request](Http().enableTracing(true)))
      .bindTo(new InetSocketAddress(10000))
      .name("zkp")
      .tracer(tracer)
      .reportTo(stats)
      .build(service)
  }
  implicit val format=net.liftweb.json.DefaultFormats
  val service=new Service[Request,Response]{
    override def apply(request: Request): Future[Response] = {
      Future.value{
        val response=Response()
        response.setContent(ChannelBuffers.wrappedBuffer("hello ziikin...".getBytes(CharsetUtil.UTF_8)))
        Trace.record(Annotation.ClientSend())
        Trace.record("starting some extremely expensive computation")
        Trace.recordBinary("http.response.code","500")
        println("spanId="+HttpTracing.Header.SpanId+",")
        println("traceId=["+Trace.id.traceId+","+Trace.id._parentId+","+Trace.id.spanId+"],nextId="+Trace.nextId+",state="+Trace.state.toString+",traceId="+Trace.id._traceId+",flags="+Trace.id.flags.toLong.toString+",traceIsActivelyTracing="+Trace.isActivelyTracing+","+Annotation.ClientSend)
        Trace.id.sampled foreach{sampled =>
          println("sampled_id"+sampled.toString)
        }
        response
      }
    }
  }
}

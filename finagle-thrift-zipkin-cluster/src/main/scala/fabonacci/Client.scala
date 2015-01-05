package fabonacci

import java.net.InetSocketAddress

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import org.jboss.netty.handler.codec.http.HttpMethod

/**
 * Created by Administrator on 2014/12/26.
 */
//https://github.com/kristofa/brave/tree/master/brave-apache-http-interceptors
object Client {
  lazy val tracer=ZipkinTracer.mk(host="10.9.52.31",port = 9410,DefaultStatsReceiver,1)
  def main(args:Array[String]): Unit ={
    val client=ClientBuilder()
      .codec(RichHttp[Request](Http().enableTracing(true)))
      .hosts(new InetSocketAddress("localhost",10000))
      .hostConnectionLimit(3)
      .name("client_01_win_yg")
      .build()
    (1 to 10) foreach { index =>
      println("succeed....")
      val flag=index%4
      val (method,url)=if(flag==0){
        (HttpMethod.GET,"/_"+index+"_GET")
      }else if(flag == 1) {
        (HttpMethod.PUT,"/_"+index+"_PUT")
      }else if(flag == 2){
        (HttpMethod.DELETE,"/_"+index+"_DELETE")
      } else{
        (HttpMethod.POST,"/_"+index+"_POST")
      }
      client(createTracerRequest(method,url)) onSuccess{rep=>
//        val headers=rep.headers()
//        val spanId=SpanId.fromString(headers.get(HttpTracing.Header.SpanId))
//        spanId foreach{sid=>
//          println(sid)
//          val traceId=SpanId.fromString(headers.get(HttpTracing.Header.TraceId))
//          val parentSpanId=headers.get(HttpTracing.Header.ParentSpanId) match{
//            case s:String=>SpanId.fromString(s)
//            case _=>None
//          }
//          val sampled=headers.get(HttpTracing.Header.Flags) match {
//            case s:String=>s.toBoolean
//            case _=>true
//          }
//          val flags=Flags(headers.get(HttpTracing.Header.Flags).toLong)
//          Trace.setId(TraceId(traceId,parentSpanId,sid,Option(sampled),flags))
//          if(Trace.isActivelyTracing) Trace.record(Annotation.ClientRecv())
//        }
        println("succeed")

      }onFailure{ex=>
        ex.printStackTrace()
      }
    }
  }
  def createTracerRequest(method:HttpMethod,url:String):Request={
    Trace.pushTracerAndSetNextId(tracer,true)
    val request=Request()
    request.setUri(url)
    request.setMethod(method)
//    val headers=request.headers()
//    headers.add(HttpTracing.Header.TraceId,Trace.id.traceId.toString())
//    headers.add(HttpTracing.Header.SpanId,Trace.id.spanId.toString())
//    Trace.id._parentId foreach{id=>
//      headers.add(HttpTracing.Header.ParentSpanId,id.toString())
//    }
//    Trace.id.sampled foreach{sampled=>headers.add(HttpTracing.Header.Flags,sampled.toString)}
//    headers.add(HttpTracing.Header.Flags,Trace.id.flags.toLong.toString)
//    if(Trace.isActivelyTracing){
//      Trace.recordRpcname("client_01_win_yg"+url,method.getName)
//      Trace.record(Annotation.ClientSend())
//    }
    request

  }
}

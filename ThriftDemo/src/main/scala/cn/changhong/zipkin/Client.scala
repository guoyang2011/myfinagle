package cn.changhong.zipkin

import java.net.InetSocketAddress

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http._
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import org.jboss.netty.handler.codec.http.{HttpHeaders, HttpMethod}

/**
 * Created by yangguo on 14-12-25.
 */
object Client {
  lazy val tracer=ZipkinTracer.mk("10.9.52.31",9410,DefaultStatsReceiver,1)
  def main(args:Array[String]){
    if(args==null || args.length<3) throw new RuntimeException("server id , port and request total count")
    val client=ClientBuilder()
      .codec(RichHttp[Request](Http().enableTracing(true)))
      .hosts(new InetSocketAddress(args(0),args(1).toInt))
      .tracer(tracer)
      .hostConnectionLimit(3)
      .reportTo(DefaultStatsReceiver)
      .build()

    (1 to args(2).toInt) foreach{index=>
      var url:String="/"
      var method:HttpMethod=null
      val flag=index%4
      if(flag==0) {
        method=(HttpMethod.POST)
        url="/"+index+"_post"
      }else if(flag==1){
        method=(HttpMethod.GET)
        url="/"+index+"_get?index="+index+"&method=post"
      }else if(flag==2){
        method=(HttpMethod.PUT)
        url="/"+index+"_put"
      }else if(flag==3){
        method=(HttpMethod.DELETE)
        url="/"+index+"_delete"
      }
      val request=encoderTracerRequest("client",tracer,method,url)
      client(request) onSuccess { response =>
        decodeTracerHeader(response.headers()) foreach {id=>
          Trace.setId(id)
          if(Trace.isActivelyTracing) Trace.record(Annotation.ClientRecv())
        }
        println(index+"succeed...")
      } onFailure{ex=>
        ex.printStackTrace()
        println(index+"failed")
      }
    }
  }
  def decodeTracerHeader(header:HttpHeaders):Option[TraceId]={
    val spanId=SpanId.fromString(header.get(HttpTracing.Header.SpanId))
    var result:Option[TraceId]=None
    spanId foreach{sid=>
      val traceId=SpanId.fromString(header.get(HttpTracing.Header.TraceId))
      val parentSpanId=header.get(HttpTracing.Header.ParentSpanId) match {
        case s:String=>SpanId.fromString(s)
        case _=>None
      }
      val sampled=header.get(HttpTracing.Header.Sampled) match{
        case s:String=>s.toBoolean
        case _=>true
      }
      val flags=header.get(HttpTracing.Header.Flags) match{
        case s:String=>Flags(s.toLong)
        case _=>Flags(0)
      }
     result=Some(TraceId(traceId,parentSpanId,sid,Option(sampled),flags)) //Trace.setId(,flags))
    }
    result
  }
  def encoderTracerRequest(name:String,tracer:Tracer,method:HttpMethod,url:String): Request ={
    Trace.pushTracerAndSetNextId(tracer,true)
    val request=Request()
    request.setMethod(method)
    request.setUri(url)
    val header=request.headers()
    header.add(HttpTracing.Header.TraceId,Trace.id.traceId.toString())
    header.add(HttpTracing.Header.SpanId,Trace.id.spanId.toString())
    Trace.id._parentId foreach {id=>
      header.add(HttpTracing.Header.ParentSpanId,id.toString())
    }
    Trace.id.sampled foreach{sampled=>header.add(HttpTracing.Header.Sampled,sampled.toString)}
    header.add(HttpTracing.Header.Flags,Trace.id.flags.toLong.toString)

    if(Trace.isActivelyTracing){
      println("its encoder doing...")
      Trace.recordRpcname(name,method.getName)
      Trace.recordBinary("http.uri",url)
      Trace.record(Annotation.ClientSend())
    }
    request
  }
}


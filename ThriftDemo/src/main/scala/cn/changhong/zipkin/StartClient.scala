package cn.changhong.zipkin

import java.net.InetSocketAddress

import com.twitter.finagle.http.HttpTracing.Header
import com.twitter.finagle.http.{Response, RichHttp, Request, Http}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing._
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.builder.{ClientBuilder}
import org.jboss.netty.handler.codec.http.HttpHeaders

/**
 * Created by Administrator on 2014/12/30.
 */

object StartClient {
  var client_name: String = ""
  var serverHost:InetSocketAddress=null

  var tracer_host:String=null
  var tracer_port:Int=0
  var request_url:String=null

  def main(arg: Array[String]): Unit = {
    val args=Array("localhost","10000","thrift_client_id","/thrift/test/id","10.9.52.31","9410")
    require(args!=null && args.length>=6)
    args.foreach(println(_))
    serverHost=new InetSocketAddress(args(0),args(1).toInt)
    client_name=args(2)
    request_url=args(3)
    tracer_host=args(4)
    tracer_port=args(5).toInt
    val tracer=ZipkinTracer.mk(tracer_host,tracer_port,DefaultStatsReceiver,1)
    val client = ClientBuilder()
      .codec(new RichHttp[Request](Http()))
      .name(client_name)
      .hostConnectionLimit(2)
      .hosts(serverHost)
      .build()
    val request=Request()
    traceClientSendRpc(request.headers(),client_name,request_url,tracer)
    client(request).onSuccess{rep=>
      traceClientRecvRpc(tracer,rep)
      println(new String(rep.getContent().array()))
    } onFailure{ex=>
      ex.printStackTrace()
    }
  }
  def traceClientSendRpc(headers:HttpHeaders,name:String,url:String,tracer:Tracer): Unit ={
    Trace.pushTracerAndSetNextId(tracer,true)

    headers.add(Header.TraceId,Trace.id.traceId.toString())
    Trace.id._parentId.foreach{id=>headers.add(Header.ParentSpanId,id.toString())}
    Trace.id.sampled.foreach{sampled=>headers.add(Header.Sampled,sampled.toString)}
    headers.add(Header.SpanId,Trace.id.spanId.toString())
    headers.add(Header.Flags,Trace.id.flags.toLong.toString())

    println(Trace.id.traceId+","+Trace.id.parentId.toLong+","+Trace.id.spanId.toLong)
    if(Trace.isActivelyTracing){
      Trace.recordRpcname(name,url)
      Trace.recordBinary("Client_Send_Msg","hello!")
      Trace.record(Annotation.ClientSend())
    }
  }
  def traceClientRecvRpc(tracer:Tracer,response:Response): Unit ={
    val headers=response.headers()
    val spanId=SpanId.fromString(headers.get(Header.SpanId))
    spanId.foreach { sid =>
      val traceId = SpanId.fromString(headers.get(Header.TraceId))
      val parentSpanId = SpanId.fromString(headers.get(Header.ParentSpanId))
      val sampled = headers.get(Header.Sampled) match {
        case s: String => s.toBoolean
        case _ => true
      }
      val flags = Flags(headers.get(Header.Flags).toLong)
      Trace.setId(TraceId(traceId, parentSpanId, spanId.get, Some(sampled), flags))
      if (Trace.isActivelyTracing) {
        Trace.recordBinary("Client_Recv_Msg", "Asw!")
        Trace.record(Annotation.ClientRecv())
      }
      println(Trace.id.traceId.toLong + "," + Trace.id.parentId.toLong + "," + Trace.id.spanId.toLong)

    }
  }
}

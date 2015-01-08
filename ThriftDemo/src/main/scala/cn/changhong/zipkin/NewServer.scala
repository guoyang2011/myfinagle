package cn.changhong.zipkin

import cn.changhong.dynamicservice.Start
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.{SpanId, TraceId, Annotation, Trace}
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.http.{Http, Request, RichHttp, codec}

/**
 * Created by yangguo on 15-1-6.
 */
object NewServer {
  val t=new RichHttp[Request](Http())
  lazy val tracer = ZipkinTracer.mk("10.9.52.31", 9410, DefaultStatsReceiver, 1)

  def main(args: Array[String]): Unit = {
    (1 to 10).foreach{index=>trace(index%2==0)}
  }
  def trace(isTerminal:Boolean): Unit = {
    if (Trace.isActivelyTracing) println("isActivelyTracing true...")
    else println("isActivelyTracing false ...")
    Trace.pushTracerAndSetNextId(tracer, isTerminal)
    val traceId = Trace.id
    Trace.recordRpcname("ttt_1", "Test_Method")
    Trace.record(Annotation.ClientSend())
    Trace.recordBinary("ClientSend", "Client Send...")
    Thread.sleep(200)

    Trace.recordRpcname("ttt_2", "Test_Method")
    val id = TraceId(Some(Trace.id.traceId), Trace.id._parentId, new SpanId(123456789), Some(true))
    Trace.pushTracer(tracer)
    Trace.setId(id)

    Trace.record(Annotation.ServerRecv())
    Trace.recordBinary("ServerRecv", "Server Recv")
    println("parentId=" + traceId.parentId.toString() + ",traceId=" + traceId.traceId.toString() + ",spanId=" + traceId.spanId.toString())
    Thread.sleep(200)
    Trace.recordRpcname("ttt_3", "Test_Method")
    val ids = TraceId(Some(Trace.id.traceId), Trace.id._parentId, new SpanId(223456789), Some(true))
    Trace.pushTracer(tracer)
    Trace.setId(ids)
    Trace.record(Annotation.ServerSend())
    Trace.recordBinary("server send", "server send")
    Thread.sleep(200)
    Trace.recordRpcname("ttt_4", "Test_Method")

    val idss = TraceId(Some(Trace.id.traceId), Trace.id._parentId, new SpanId(323456789), Some(true))
    Trace.pushTracer(tracer)
    Trace.setId(idss)

    Trace.record(Annotation.ClientRecv())
    Trace.recordBinary("Client Recv", "Client Recv")
    println("" + traceId._parentId.toString + ",parentId=" + traceId.parentId.toString() + ",traceId=" + traceId.traceId.toString() + ",spanId=" + traceId.spanId.toString())
    //    Trace.pushTracer(tracer)
    Thread.sleep(1000)
//    if (Trace.isActivelyTracing) println("isActivelyTracing true...")
//    else println("isActivelyTracing false ...")
  }
}

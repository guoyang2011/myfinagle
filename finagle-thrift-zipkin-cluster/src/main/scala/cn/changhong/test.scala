package cn.changhong

import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.{TraceId, Annotation, Trace}
import com.twitter.finagle.zipkin.thrift.ZipkinTracer

/**
 * Created by Administrator on 2014/12/27.
 */
object test {
  lazy val tracer = ZipkinTracer.mk(host = "10.9.52.31", port = 9410, DefaultStatsReceiver, 1)
  def main(args:Array[String]): Unit ={
    println(Trace.pushTracerAndSetNextId(tracer,true))
    Trace.recordRpc("recordRpc_1")
    Trace.recordServiceName("Test_recordServiceName_1")
    if(Trace.isActivelyTracing){
      Trace.record(Annotation.ClientSend())
      Trace.recordBinary("SendMsg","Start Encoder...")
    }
    Thread.sleep(1000)
    println(Trace.id.traceId+","+Trace.id._parentId+","+Trace.id.spanId+","+Trace.id.sampled+","+Trace.isActivelyTracing+","+Trace.id.flags.toLong)
    Trace.pushTracer(tracer)
    val (tracerId,parantId,spanId,smapled,flags)=(Trace.id.traceId,Trace.id.parentId,Trace.id.spanId,Trace.id.sampled,Trace.id.flags)
    val id=TraceId(Some(tracerId),Some(parantId),spanId,smapled,flags)
    Trace.setId(id)

//    Trace.recordRpc("Test_recordRpc_2")
//    Trace.recordServiceName("Test_recordServiceName_2")
//    Trace.recordBinary("RecvMsg","Start Decoder...")
//    println(Trace.id.traceId+","+Trace.id._parentId+","+Trace.id.spanId+","+Trace.id.sampled+","+Trace.isActivelyTracing+","+Trace.id.flags.toLong)

  }
}

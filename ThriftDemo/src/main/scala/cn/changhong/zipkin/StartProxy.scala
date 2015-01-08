package cn.changhong.zipkin

import java.net.InetSocketAddress

import cn.changhong.core.IndexNewsOperatorServices
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.finagle.{SimpleFilter, Service}
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.HttpTracing.Header
import com.twitter.finagle.http.{Http, RichHttp, Response, Request}
import com.twitter.finagle.tracing._
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol
import org.jboss.netty.buffer.ChannelBuffers

/**
 * Created by Administrator on 2015/1/7.
 */
object StartProxyServer {

  var serverHost: InetSocketAddress = null
  var tracer_host: String = ""
  var tracer_port: Int = 0

   var request_url: String = null

  var tracer: Tracer = null

  var serverName: String = "zk_front_server_01"

  object Filter extends SimpleFilter[Request, Response] {
    override def apply(request: Request, service: Service[Request, Response]): Future[Response] = frontTracer(request)(service.apply)
  }

  class EndPointService(endServerName: String, method: String, dest: String) extends Service[Request, Response] {
    val client_service = ClientBuilder()
      .codec(ThriftClientFramedCodec())
      .tracer(tracer)
      .hostConnectionLimit(3)
      .dest(dest)
      .build()
    val client = new IndexNewsOperatorServices.FinagledClient(client_service, new TBinaryProtocol.Factory,  "endServerName")

    override def apply(request: Request): Future[Response] = {
      endTracer(endServerName, method, request)(endpointTask)
    }

    def endpointTask(request: Request): Future[Response] = {
      client.deleteArtificaillyNes(1).map { rep =>
        val resp = Response()
        resp.setContent(ChannelBuffers.wrappedBuffer((rep + "endPointTask").getBytes()))
        resp
      }
    }
  }

  def main(argt: Array[String]): Unit = {
    val args = Array("localhost", "10000", "zk_http_proxy_server_01", "10.9.52.31", "9410","localhost:10002")
    require(args != null && args.length >= 6)
    serverName = args(2)
    println("server name=" + serverName)
    this.tracer_host = args(3)
    this.tracer_port = args(4).toInt

    tracer = ZipkinTracer.mk(tracer_host, tracer_port, DefaultStatsReceiver, 1)
    val service = Filter andThen new EndPointService("thrift_endpoint_server", "thrift_endpoint_request_method", args(5))
    ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .bindTo(new InetSocketAddress(args(0), args(1).toInt))
      .name(serverName)
      .build(service)

  }

  def frontTracer(request: Request)(block: (Request) => Future[Response]): Future[Response] = {
    Trace.pushTracer(tracer)
    val headers = request.headers()
    val id = {
      headers.get(Header.SpanId) match {
        case s: String => SpanId.fromString(s) map { sid =>
          def createSpanId(s: String): Option[SpanId] = {
            s match {
              case s: String =>
                SpanId.fromString(s)
              case _ => None
            }
          }
          val traceId = createSpanId(headers.get(Header.TraceId))
          val parentSpanId = createSpanId(headers.get(Header.ParentSpanId))

          val sampled = headers.get(Header.Sampled).toBoolean

          val flags = Flags(headers.get(Header.Flags).toLong)
          println(traceId.get + "," + parentSpanId + "," + sid)
          val id = TraceId(traceId, parentSpanId, sid, Some(sampled), flags)
          Trace.setId(id)
          if (Trace.isActivelyTracing) {
            Trace.recordRpcname("front_server_test_1", "front_handler_method")
            Trace.record(Annotation.ServerRecv())
          }
          id
        }
        case _ => None
      }
    }
    block(request).map { rep =>
      if (Trace.isActivelyTracing) {
        id map { id => Trace.record(Annotation.ServerSend())}
      }
      rep
    }
  }
  def endTracer[Req, Rep](endServerName: String, method: String, request: Req)(block: (Req) => Future[Rep]): Future[Rep] = {
    Trace.pushTracerAndSetNextId(tracer, false)
    Trace.record(Annotation.ClientSend())
    Trace.recordRpcname(endServerName, method)
    println(Trace.id.traceId.toLong + "," + Trace.id.parentId.toLong + "," + Trace.id.spanId.toLong)
    block(request).map { rep =>
      Trace.record(Annotation.ClientRecv())
      rep
    }
  }
}




package cn.changhong

import java.net.InetSocketAddress

import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.Trace
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.{SimpleFilter, Service}
import com.twitter.finagle.builder.{ServerBuilder, ClientBuilder}
import com.twitter.finagle.http.{Response, Request, Http, RichHttp}
import com.twitter.util.Future

/**
 * Created by Administrator on 2014/12/27.
 */
object ZipkinTracerServer {
  lazy val tracer=ZipkinTracer.mk(host="10.9.52.31",port = 9410,DefaultStatsReceiver,1)
  def createDispatcherServer: Unit ={
    object DisPactherFilterService extends SimpleFilter[Request,Response]{
      override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
        println(Trace.pushTracerAndSetNextId(tracer,true))
        service(request)
      }
    }
  }
  def createProxyClient(hosts:InetSocketAddress,proxyName:String,connectionLimit:Int=1) ={
    ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .hosts(hosts)
      .name(proxyName)
      .hostConnectionLimit(connectionLimit)
      .build()
  }
  def createProxyService(hosts:InetSocketAddress,serverName:String,service:Service[Request,Response]) ={
    ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .bindTo(hosts)
      .name(serverName)
      .build(service)
  }
  def runService(fb: =>Unit): Unit ={
    new Thread(new Runnable {
      override def run(): Unit = fb
    }).start()
  }
}

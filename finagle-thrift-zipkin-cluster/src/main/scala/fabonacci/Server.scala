package fabonacci

import java.net.InetSocketAddress

import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.{Http, RichHttp, Request, Response}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers

/**
 * Created by Administrator on 2014/12/26.
 */

object Server {
  lazy val tracer = ZipkinTracer.mk(host = "10.9.52.31", port = 9410, DefaultStatsReceiver, 1)
  def createDispatcherServer(): Unit = {
    val client=ClientBuilder()
      .codec(RichHttp[Request](Http().enableTracing(true)))
      .tracer(tracer)
      .hosts(new InetSocketAddress("localhost",10002))
      .hostConnectionLimit(3)
      .reportTo(DefaultStatsReceiver)
      .name("client_01_win_yg_DispatcherClient")
      .build()

    val service=new Service[Request, Response] {
      override def apply(request: Request): Future[Response] = client(request)
    }
    runServer(new Runnable {
      override def run(): Unit = {
        ServerBuilder()
          .codec(RichHttp[Request](Http().enableTracing(true)))
//          .tracer(tracer)
          .bindTo(new InetSocketAddress("localhost", 10000))
//          .reportTo(DefaultStatsReceiver)
          .name("server_01_win_yg_DispatcherServer")
          .build(service)
      }
    })
  }
  def createDBServer(): Unit ={
    val service=new Service[Request, Response] {
      override def apply(request: Request): Future[Response] = {
        Future.value{
          val response=Response()
          response.setContent(ChannelBuffers.wrappedBuffer("request successed".getBytes()))
          response
        }
      }
    }
    runServer(new Runnable {
      override def run(): Unit = {
        ServerBuilder()
          .codec(RichHttp[Request](Http().enableTracing(true)))
          .tracer(tracer)
          .bindTo(new InetSocketAddress("localhost", 10001))
          .reportTo(DefaultStatsReceiver)
          .name("server_01_win_yg_DBServer")
          .build(service)
      }
    })
  }
  def createLogicServer(): Unit ={
    val client=ClientBuilder()
      .codec(RichHttp[Request](Http().enableTracing(true)))
      .tracer(tracer)
      .hosts(new InetSocketAddress("localhost",10001))
      .hostConnectionLimit(3)
      .reportTo(DefaultStatsReceiver)
      .name("server_01_win_yg_LogicClient")
      .build()
    val service=new Service[Request, Response] {
      override def apply(request: Request): Future[Response] = client(request)
    }
    runServer(new Runnable {
      override def run(): Unit = {
        ServerBuilder()
          .codec(RichHttp[Request](Http().enableTracing(true)))
          .tracer(tracer)
          .bindTo(new InetSocketAddress("localhost", 10002))
          .reportTo(DefaultStatsReceiver)
          .name("client_01_win_yg_LogicServer")
          .build(service)
      }
    })
  }
  def runServer(runnable:Runnable): Unit ={
    new Thread(runnable).start()
  }
  def LauncherServer: Unit ={
    createDispatcherServer()
    createLogicServer()
    createDBServer()
    Thread.sleep(2000)
    Client.main(null)
  }
  def main(args:Array[String]): Unit ={
    LauncherServer
  }
}

package cn.changhong.thrift

import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicInteger

import cn.changhong.core.{NewsModel, IndexNewsOperatorServices}
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.{Response, Http, Request, RichHttp}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.tracing.Trace
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.zookeeper.{ZkAnnouncer, ZkResolver}
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol
import org.jboss.netty.buffer.ChannelBuffers

/**
 * Created by yangguo on 15-1-5.
 */
class IndexNewsOperationImp(name:String) extends IndexNewsOperatorServices.FutureIface{
  val count=new AtomicInteger(0)
  override def indexNews(indexNews: NewsModel): Future[Boolean] = Future.value{
    if(count.addAndGet(1)%2==0) true
    else false
  }

  override def deleteArtificaillyNes(id: Int): Future[Int] = {
    Trace.recordBinary("id",id)
    if(count.getAndAdd(1)%2==0) HttpProxy.httpProxyClient1(Request()).map{rep=>
      println("Http proxy service="+new String(rep.getContent().array()))
      0
    }else HttpProxy.httpProxyClient2(Request()).map{rep=>
      println("Http proxy service="+new String(rep.getContent().array()))
      1
    }
  }
}
object HttpProxy {

    val path = "/http_proxy"
    val httpProxyClient1=client("zk!" + Start.zkHost + "!/http_proxy_1","test_http_proxy_client_1")
    val httpProxyClient2=client("zk!" + Start.zkHost + "!/http_proxy_2","test_http_proxy_client_2")

    def client(dest: String, name: String): Service[Request, Response] = {
      ClientBuilder()
        .dest(dest) //"zk!"+Start.zkHost+"!/http_proxy_2")
        .name(name) //("proxy_http_client_1")
        .codec(RichHttp[Request](Http()))
        .tracer(Start.tracer)
        .hostConnectionLimit(3)
        .build()
    }
    def startServer: Unit = {
      (1 to 10).foreach { index =>
        new Thread(new Runnable {
          override def run(): Unit = {
            val zkResovler = new ZkResolver()
            val zkAnn = new ZkAnnouncer()
            val bind = new InetSocketAddress(index + 20000)
            val zkAddr = Start.zkHost + "!" + path + (if (index % 2 == 0) "_2" else "_1") + "!0"
            service(bind, "end_http_proxy_" + bind.getPort)
            zkResovler.bind(zkAddr)
            zkAnn.announce(bind, zkAddr)
          }
        }).start()
      }
    }
    def service(bind: InetSocketAddress, name: String): Unit = {
      ServerBuilder()
        .codec(RichHttp[Request](Http()))
        .bindTo(bind)
        .tracer(ZipkinTracer.mk("10.9.52.31",9410,DefaultStatsReceiver,1))
        .name(name)
        .build(new Service[Request, Response] {
        override def apply(request: Request): Future[Response] = Future.value {
          println("HttpProxy Receive message..."+name)
          Trace.recordBinary("requestId", name)
          val response = Response()
          response.setContent(ChannelBuffers.wrappedBuffer((name).getBytes()))
          response
        }
      })

    }
  }

object ThriftServer {
  def main(args:Array[String]): Unit ={
    require(args!=null && args.length>2)
    apply(new InetSocketAddress(args(0),args(1).toInt),args(2))
  }
  def apply(addr: InetSocketAddress, name: String): Unit = {
    val service = new IndexNewsOperatorServices.FinagledService(new IndexNewsOperationImp(name), new TBinaryProtocol.Factory())
    ServerBuilder()
      .bindTo(addr)
//      .tracer(ZipkinTracer.mk("10.9.52.31",9410,DefaultStatsReceiver,1))
      .codec(ThriftServerFramedCodec())
      .name(name)
      .build(service)
  }

}

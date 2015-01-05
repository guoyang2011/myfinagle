package cn.changhong

import java.net.InetSocketAddress

import cn.changhong.core.{IndexNewsOperatorServices, NewsModel}
import com.twitter.finagle.Thrift
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.util.{Return, Await, Future}
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Created by yangguo on 14-12-18.
 */
class IndexNewsOperationImp extends IndexNewsOperatorServices.FutureIface {
  override def indexNews(indexNews: NewsModel): Future[Boolean] = Future.value(true)

  override def deleteArtificaillyNes(id: Int): Future[Int] = {
    println(id)
    Future.value(id)
  }
}
object Server {
  def main(args:Array[String]): Unit = {
    if (args == null || args.length < 1) throw new IllegalArgumentException("bind ip and port")
//    val server=Thrift.serveIface(args(0),new IndexNewsOperatorServices[Future] {
//
//    })
//    Await.ready(serwww.chphon.cn
    val service =new IndexNewsOperatorServices.FinagledService(new IndexNewsOperationImp,new TBinaryProtocol.Factory())
    ServerBuilder()
       .bindTo(new InetSocketAddress((10000)))
       .codec(ThriftServerFramedCodec())
       .name("sbt")
       .build(service)
  }
}

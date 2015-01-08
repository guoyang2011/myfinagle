package cn.changhong.zipkin

import java.net.InetSocketAddress
import java.util.Random

import cn.changhong.core.{NewsModel, IndexNewsOperatorServices}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Created by yangguo on 15-1-8.
 */
object StartEndPointThriftServer {

  object IndexNewsOperationImp extends IndexNewsOperatorServices.FutureIface{
    override def indexNews(indexNews: NewsModel): Future[Boolean] = Future.value{
      true
    }

    override def deleteArtificaillyNes(id: Int): Future[Int] = Future.value{
      new Random().nextInt()
    }
  }
  //serverhost serverport name zkhost zkport
  def main(args:Array[String]): Unit ={
    require(args!=null && args.length>4)
    val service=new IndexNewsOperatorServices.FinagledService(IndexNewsOperationImp,new TBinaryProtocol.Factory())
    val tracer=ZipkinTracer.mk(args(3),args(4).toInt,DefaultStatsReceiver,1)
    ServerBuilder()
      .codec(ThriftServerFramedCodec())
      .bindTo(new InetSocketAddress(args(0),args(1).toInt))
      .name(args(2))
      .tracer(tracer)
      .build(service)
  }
}

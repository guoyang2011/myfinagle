package cn.changhong.thrift

import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicInteger

import cn.changhong.core.{NewsModel, IndexNewsOperatorServices}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Created by yangguo on 15-1-5.
 */
class IndexNewsOperationImp(name:String) extends IndexNewsOperatorServices.FutureIface{
  val count=new AtomicInteger(0)
  override def indexNews(indexNews: NewsModel): Future[Boolean] = Future.value{
    if(count.addAndGet(1)%2==0) true
    else false
  }

  override def deleteArtificaillyNes(id: Int): Future[Int] = Future.value{
    println(name)
    if(count.getAndAdd(1)%3==0) 0
    else 1
  }
}
object ThriftServer {
  def apply(addr:InetSocketAddress,name:String): Unit ={
    val service =new IndexNewsOperatorServices.FinagledService(new IndexNewsOperationImp(name),new TBinaryProtocol.Factory())
    ServerBuilder()
      .bindTo(addr)
      .codec(ThriftServerFramedCodec())
      .name(name)
      .build(service)
  }
}

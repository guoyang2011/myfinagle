package cn.changhong

import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicInteger

import com.twitter.finagle.{Http, Thrift}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec
import com.twitter.finagle.tracing.Trace
import com.twitter.util.{ Duration,Future}
import core.{ IndexNewsOperatorServices}
import org.jboss.netty.handler.codec.http.{HttpVersion, HttpMethod, DefaultHttpRequest}

import scala.util.Random

/**
 * Created by yangguo on 14-12-17.
 */
object ClientTest {
  val s1:AtomicInteger=new AtomicInteger(0)
  val e1:AtomicInteger=new AtomicInteger(0)
  val noMatch:AtomicInteger=new AtomicInteger(0)
  val ee1:AtomicInteger=new AtomicInteger(0)
  var ex1: Throwable = null
  val client = Thrift.newIface[IndexNewsOperatorServices.FutureIface]("localhost:10000")
  def run1: Unit ={
    (1 to 100000) foreach { index =>
      doTask(index)
    }
    Thread.sleep(10000)
    println(s1.get() + "," + e1.get() + ",noMatch=" + noMatch.get()+",total try="+ee1.get())

  }
  def doTask(index: Int, timeout: Long = 0,try_count:Int=0): Unit = {
    client.deleteArtificaillyNes(index) onSuccess { b =>
      s1.addAndGet(1)
      if(b!=index) noMatch.addAndGet(1)
    } onFailure { ex =>
      ee1.addAndGet(1)
        Thread.sleep(try_count)
        if (ex1 == null) ex1 = ex
        if (try_count > 3) e1.addAndGet(1)
        else doTask(index, (new Random().nextInt()) % 1000, try_count + 1)
    }
  }

  def main(args: Array[String]): Unit = {
    val client=Http.newService("localhost:10000")
    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/")
    (1 to 100) foreach{index=>client(request) onSuccess(e=>println("successed!")) onFailure(e=>e.printStackTrace())}
    Thread.sleep(1000)
  }
  def run {
    val serviceCodec = ThriftClientFramedCodec()
    val service = ClientBuilder()
      .hosts(Seq(new InetSocketAddress("localhost",10000),new InetSocketAddress("www.chphone.cn", 10000)))
      .codec(serviceCodec)
      .hostConnectionLimit(3)
      .build()
    val clients=new IndexNewsOperatorServices.FinagledClient(service)
    (1 to 10000).foreach{index=>clients.deleteArtificaillyNes(1) onSuccess{e=>println(e)} onFailure{ex=>ex.printStackTrace()}}
  }

  val map=new scala.collection.mutable.HashMap[String,(String)=>Future[String]]()
  map("hee")=null
}

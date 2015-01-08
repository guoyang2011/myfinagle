package cn.changhong.thrift

import java.net.InetSocketAddress

import com.twitter.finagle.{ServerCodecConfig, Codec, ClientCodecConfig, CodecFactory}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.Trace
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.zookeeper.{ZkAnnouncer, ZkResolver}

/**
 * Created by yangguo on 15-1-5.
 */
object Start {
  lazy val  tracer=ZipkinTracer.mk("10.9.52.31",9410,DefaultStatsReceiver,1)
  val zkHost="localhost:2181"
  val zkPath="/thrift_1"
  def main(args:Array[String]): Unit ={
    (1 to 10).foreach{index=>runServer(new InetSocketAddress(index+10000),startZkService)}
    Thread.sleep(3000)
    runServer(HttpProxy.startServer)
    Thread.sleep(5000)
    runServer(startZkClient)
  }
  def startZkService(bind:InetSocketAddress): Unit ={
    val zkResolver=new ZkResolver()
    val zkAnn=new ZkAnnouncer()
    val zkAddr=zkHost+"!"+zkPath+"!0"
    println("zkAddr"+zkAddr)
    ThriftServer(bind,"front_thrift_proxy_http"+bind.getPort)
    zkResolver.bind(zkAddr)
    zkAnn.announce(bind,zkAddr)
  }
  def startZkClient: Unit ={
    val client=ThriftClient("zk!"+zkHost+"!"+zkPath)
    (1 to 10).foreach{index=>
      Trace.recordBinary("id1",index)
      client.deleteArtificaillyNes(1) onSuccess{rep=>
        println(rep)
      } onFailure{ex=>ex.fillInStackTrace()}
      Thread.sleep(100)
    }
  }
  def runServer(fn: =>Unit): Unit ={
    new Thread(new Runnable {
      override def run(): Unit = fn
    }).start()
  }
  def runServer(bind:InetSocketAddress,fn: (InetSocketAddress)=>Unit): Unit ={
    new Thread(new Runnable {
      override def run(): Unit = {
        fn(bind)
      }
    }).start()
  }
}
package cn.changhong.dynamicservice

import java.net.InetSocketAddress
import com.twitter.common.quantity.{Time, Amount}
import com.twitter.common.zookeeper.{ServerSets, ServerSetImpl, ZooKeeperClient}
import com.twitter.finagle.{ListeningServer, Service}
import com.twitter.finagle.builder.{ServerBuilder, ClientBuilder}
import com.twitter.finagle.http.{Response, Request, RichHttp, Http}
import com.twitter.finagle.zookeeper.{ZkAnnouncer, ZkClientFactory, ZkResolver, ZookeeperServerSetCluster}
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers



/**
 * Created by yangguo on 14-12-30.
 */
object Start {
  var zkHost:String="localhost:2181"
  var zkPath:String="/endpoint_4"
  def main(args:Array[String]): Unit ={
    require(args == null || args.length<4)
    //proxy,endpoint host port path zkHost
    zkPath=args(3)
    if(args.length==5) zkHost=args(4)
    args(0) match{
      case "proxy"=> startServer(new InetSocketAddress(args(1),args(2).toInt),"proxy")(ZkProxyLauncher.apply)
      case "endpoint"=> startServer(new InetSocketAddress(args(1),args(2).toInt),"endpoint"+args(2))(ZkEndpointLauncher.apply)
      case _=>throw new RuntimeException("proxy or endpoint")
    }
  }

  def startServer(bindHost:InetSocketAddress,name:String)(fb:(InetSocketAddress,String) =>Unit): Unit ={
    println("start server:"+name)
    new Thread(new Runnable {
      override def run(): Unit = fb(bindHost,name)
    }).start()
  }

}
object ZkProxyLauncher{
  def apply(bind:InetSocketAddress,name:String): Unit ={
    val clients=ZkClient()
    ZkServer(bind,name,false,clients.apply)
  }
}
object ZkEndpointLauncher{
  def apply(bind:InetSocketAddress,name:String): Unit ={
    ZkServer(bind,name)
  }
}
object ZkClient{
  def apply(path:String="/default") :Service[Request,Response]={
    val addr="zk!"+Start.zkHost+"!"+Start.zkPath//scheme
    println("Client\t\t"+addr)
    ClientBuilder.get()
      .dest(addr)
      .codec(RichHttp[Request](Http()))
      .hostConnectionLimit(3)
      .build()
  }
}
object ZkServer {
  def apply(bind: InetSocketAddress, name: String,isBind:Boolean=true, fb: (Request) => Future[Response]=defaultHandler): Unit = {
    val res=new ZkResolver()
    val ann=new ZkAnnouncer()
    val host = bind
    ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .bindTo(host)
      .name(name)
      .build(new Service[Request, Response] {
      override def apply(request: Request): Future[Response] = {
        println("request server name="+name)
        request.headers().set("name",name)
          fb(request) onFailure(ex=>ex.printStackTrace())
      }
    })
    if(isBind) {
      val addr = Start.zkHost+"!" + Start.zkPath + "!0"
      println("\n\n"+addr)
      res.bind(addr).foreach(a=>println(a.toString+">>>>>>>>")) //绑定到zk
      ann.announce(host,addr)
      println("zk listener start...")
   }
  }

  private[this] def defaultHandler(request: Request): Future[Response] = {
    Future.value {
      val response = Response()
      response.setContent(ChannelBuffers.wrappedBuffer(request.headers().get("name").getBytes()))
      response
    }
  }

}


package cn.changhong.cluster

import java.net.InetSocketAddress

import cn.changhong.core.thrift.{User, AccountService}
import cn.changhong.core.thrift.AccountService.FinagledClient
import cn.changhong.core.thrift.impl.AccountServiceImpl
import com.twitter.finagle.{Thrift, Service}
import com.twitter.finagle.builder.{ServerBuilder, ClientBuilder}
import com.twitter.finagle.http.{Response, Http, Request, RichHttp}
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftServerFramedCodec}
import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http.QueryStringDecoder

/**
 * Created by Administrator on 2014/12/29.
 */
trait Cluster{
  def service(port:Int,serverName:String):Unit
  def client(servers:String,name:String):Unit
}
object Clusters {
  def main(args:Array[String]): Unit ={
    val servers=(1 to 3).map{index=>
      val port=10000+index
      val serverName="server_pool_"+index
      runThread(createThriftServer(port,serverName))
      "localhost:"+port
    }
    Thread.sleep(2000)
    runThread(createThriftProxy(10004,"proxyServer",servers.mkString(",")))
  }
  def createThriftProxy(port: Int, serverName: String,proxys:String): Unit ={
   def createThriftClient(proxy:String,name:String = "Thrift Test Client"):FinagledClient={
      val clientCodec=ThriftClientFramedCodec()
      val clientService=ClientBuilder()
        .codec(clientCodec)
        .hosts(proxy)
        .name(name)
        .hostConnectionLimit(1)
        .build()
      new AccountService.FinagledClient(clientService)
    }
    val clients=createThriftClient(proxys)
    ServerBuilder()
      .bindTo(new InetSocketAddress("localhost", port))
      .name(serverName)
      .codec(RichHttp[Request](Http()))
      .build(new Service[Request, Response] {
      override def apply(request: Request): Future[Response] = {
        val decoder=new QueryStringDecoder(request.getUri())
        decoder.getPath match{
          case "/create"=>
            clients.create(new User.Immutable(1,"username","iphone","email","passwd")).map{b=>
            val response=Response()
            response.setContent(ChannelBuffers.wrappedBuffer(("res="+b).getBytes()))
            response
          }
          case "/get"=> clients.get(100).map{user=>
            val response=Response()
            response.setContent(ChannelBuffers.wrappedBuffer((user.id+","+user.username+","+user.iphone+","+user.email+","+user.passwd).getBytes()))
            response
          }
          case "/list"=> clients.list().map{users=>
            val response=Response()
            val content=users.map{user=>
              user.id+","+user.username+","+user.iphone+","+user.email+","+user.passwd
            }.mkString(";")
            response.setContent(ChannelBuffers.wrappedBuffer(content.getBytes()))
            response
          }
          case "/creates"=> clients.creates((1 to 10).map{index=>
            new User.Immutable(index,"username","iphone","email","passwd")
          }).map{res=>
            val response=Response()
            val content=res.map(_.toString).mkString(",")
            println(content+",,,,")
            response.setContent(ChannelBuffers.wrappedBuffer(content.getBytes()))
            response
          }
          case _=>Future.value{
            val response=Response()
            response.setContent(ChannelBuffers.wrappedBuffer("Not Find...".getBytes()))
            response
          }
        }
      }
    })
  }
  def createThriftServer(port:Int,name:String): Unit ={
    ServerBuilder()
      .codec(ThriftServerFramedCodec())
      .bindTo(new InetSocketAddress("localhost",port))
      .name(name)
      .build(new AccountService.FinagledService(new AccountServiceImpl,new TBinaryProtocol.Factory()))
  }
  def runThread(fb: =>Unit): Unit ={
    new Thread(new Runnable {
      override def run(): Unit = fb
    }).start()
  }
  object ProxyServer extends Cluster{
    def serviceProxy(port: Int, serverName: String,proxys:String): Unit = {
      val clients=ClientBuilder()
        .codec(RichHttp[Request](Http()))
        .name(serverName + "client")
        .hosts(proxys)
        .hostConnectionLimit(3)
        .build()
      ServerBuilder()
        .bindTo(new InetSocketAddress("localhost", port))
        .name(serverName)
        .codec(RichHttp[Request](Http()))
        .build(new Service[Request, Response] {
        override def apply(request: Request): Future[Response] = clients(request)
      })
    }

    override def service(port: Int, serverName: String): Unit = ???

    override def client(servers: String, name: String): Unit =  ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .name(name)
      .hosts(servers)
      .hostConnectionLimit(3)
      .build()
  }
  object Endpoint extends Cluster{
    override def service(port:Int,serverName:String): Unit = ServerBuilder()
      .bindTo(new InetSocketAddress("localhost",port))
      .name(serverName)
      .codec(RichHttp[Request](Http()))
      .build(new Service[Request,Response] {
      override def apply(request: Request): Future[Response] = {
        Future.value{
          val response=Response()

          response.setContent(ChannelBuffers.wrappedBuffer((serverName+"-"+port).getBytes()))
          response
        }
      }
    })

    override def client(servers: String,name:String): Unit = ???
  }
  object Client extends Cluster{
    override def service(port: Int, serverName: String): Unit = ???

    override def client(servers: String,name:String): Unit = ClientBuilder()
      .codec(RichHttp[Request](Http()))
      .name(name)
      .hosts(servers)
      .hostConnectionLimit(3)
      .build()
  }
}

package cn.changhong.docker

import java.net.InetSocketAddress

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Response, RichHttp, Http, Request}
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers

/**
 * Created by yangguo on 15-1-9.
 */
object SimpleServer {
  def main(args:Array[String]): Unit ={
//    require(args!=null && args.length>2)
//    ServerBuilder()
//      .codec(new RichHttp[Request](Http()))
//      .name("docker_test_simple_server")
//      .bindTo(new InetSocketAddress(args(1).toInt))
//      .build(new Service[Request,Response]{
//      override def apply(request: Request): Future[Response] = {
//        Future.value {
//          val response=Response()
//          response.setContent(ChannelBuffers.wrappedBuffer("hello docker...".getBytes()))
//          response
//        }
//      }
//    })
    Tets.run()
  }
}
object Tets{
  def task(s:String): Unit ={

  }
  def doTask(task: (String)=>Unit): Unit ={
    task("")
  }
  def run(): Unit ={
    doTask{client:String=>}
  }
}
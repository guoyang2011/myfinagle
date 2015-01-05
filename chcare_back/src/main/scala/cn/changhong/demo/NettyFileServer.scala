package cn.changhong.demo

import java.io.{FileInputStream, File}
import java.net.InetSocketAddress
import java.nio.channels.FileChannel

import com.twitter.finagle.http.{Response, Http}
import com.twitter.finagle.{Service, CodecFactory, Codec}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.util.Future
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.channel.{Channels, ChannelPipelineFactory}
import org.jboss.netty.handler.codec.http._

/**
 * Created by yangguo on 14-11-10.
// */

//case class SimpleCodec[req,rep] extends CodecFactory[req,rep]{
//  override def client: Client = {
//
//  }
//
//  override def server: Server = {config=>
//    new Codec[req,rep] {
//      override def pipelineFactory: ChannelPipelineFactory = {
//        val pipeline=Channels.pipeline()
//        pipeline.addLast("httpCompressor",new HttpContentCompressor(6))
//        pipeline
//      }
//    }
//  }
//}

object NettyFileServer extends App{
        val file = new File("/Users/yangguo/Downloads/mysql-cluster-gpl-7.3.7-linux-glibc2.5-x86_64.tar.gz")
  if(file.exists() && file.isFile){
    println("ok....")
  }
  val service=new Service[HttpRequest,HttpResponse] {
    override def apply(request: HttpRequest): Future[HttpResponse] = {
      println("go in...")
      (file.exists(), file.isFile) match {
        case (true, true) => {
          val instream = new FileInputStream(file).getChannel
          try {

            val buffer = instream.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
            println("recv message")
            response.setHeader("Content-Length", file.length())
            response.setHeader("Content-Type", "application/octet-stream")
            response.setHeader("Content-Transfer-Encoder","binary")
            response.setHeader("Content-Desposition", "attachment;filename=\"mysql.tar.gz\";name=\"mysql.tar.gz\"")
            response.setContent(ChannelBuffers.wrappedBuffer(buffer))

            Future.value(response)

          } catch {
            case e =>
              println(e.getLocalizedMessage + "," + e.getMessage)
              Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
          } finally {
            println("what's happened?")
            instream.close()
          }
        }
        case _ =>
          println("go out.."+file.exists()+","+file.isFile)
          Future.value(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK))
      }
    }
  }
  ServerBuilder()
    .codec(Http(6))
    .name("fileServer")
    .bindTo(new InetSocketAddress(10000))
    .build(service)

  println("server start....")
}

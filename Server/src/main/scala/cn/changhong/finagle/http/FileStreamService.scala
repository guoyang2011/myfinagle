package cn.changhong.finagle.http

import java.io.{ByteArrayInputStream, FileOutputStream, FileInputStream, File}
import java.net.{URLEncoder, InetSocketAddress}
import java.nio.channels.FileChannel

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._
import com.twitter.finagle.http.Http

/**
 * Created by yangguo on 14-10-24.
 */
object FileStreamService extends App{
  val service=new Service[HttpRequest,HttpResponse]{
    val filepath="/Users/yangguo/Downloads/spark源码分析-20140521.pdf"
    override def apply(request: HttpRequest): Future[HttpResponse] = {
      downloadFile(request)
    }
    private[this] def uploadFile(request:HttpRequest): Future[HttpResponse]={
      val cbuffer=request.getContent
      val fStream=new FileOutputStream("/Users/yangguo/Downloads/uploadFile.dao").getChannel
      println("service receive stream...")
      try{
        println(cbuffer.capacity()+","+cbuffer.readableBytes()+","+cbuffer.writableBytes())
       fStream.write(cbuffer.toByteBuffer)
      }finally{
        fStream.close()
      }
      Future.value(new DefaultHttpResponse(request.getProtocolVersion,HttpResponseStatus.OK))
    }
    private[this] def downloadFile(request:HttpRequest): Future[HttpResponse] ={
      val file=new File(filepath)
      val fileChannel=new FileInputStream(file).getChannel
      try{
        println(request.getHeader("Accept-Encoding"))
        val buffer=ChannelBuffers.copiedBuffer(fileChannel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY,0,file.length()))
        val response=new DefaultHttpResponse(request.getProtocolVersion,HttpResponseStatus.OK)
        response.setHeader("Content-Type", "application/octet-stream")
        response.setHeader("Content-Transfer-Encoding","binary")
        response.setHeader("Content-Disposition","attachment;filename=\""+URLEncoder.encode(file.getName,"utf-8")+"\"")
        response.setContent(buffer)
        Future.value(response)
      }finally{
        fileChannel.close()
      }
    }
  }
  new Thread(new Runnable {
    override def run(): Unit = ServerBuilder()
    .codec(com.twitter.finagle.http.Http(6))
    .bindTo(new InetSocketAddress(10000))
    .name("fileService")
    .build(service)
  }).start()
  Thread.sleep(2000)
  new Thread(new Runnable{
    override def run(): Unit = clientDownloadFile
  }).start()
  private[this] def clientDownloadFile: Unit ={
    val client=com.twitter.finagle.Http.newService("localhost:10000")
    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"")
    client(request) onSuccess{response=>
      val buffer=response.getContent
      println(response.getHeader("Content-Disposition"))
      val file=new File("/Users/yangguo/client.data");
      val channel=new FileOutputStream(file).getChannel
      channel.write(buffer.toByteBuffer)
      channel.close()
      println("download file successed")
    }
  }
  private[this] def clientuploadFile={
      val client=com.twitter.finagle.Http.newService("localhost:10000")
    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,"/")
    val file=new File("/Users/yangguo/Downloads/深入浅出Netty.pdf")
    if(file.exists() && file.isFile){
      println("file"+file.length())
    }
    val fileOutStreamChannel=new FileInputStream(file).getChannel
    try{
    val cbuffer=fileOutStreamChannel.map(FileChannel.MapMode.READ_ONLY,0,file.length())
    println(cbuffer.capacity()+"client")
    val httpRequest=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.PUT,"/")
    httpRequest.setHeader("Content-Type", "application/octet-stream")
    httpRequest.setHeader("Content-Transfer-Encoding","binary")
    httpRequest.setHeader("Chunk-Size",5)
    httpRequest.setHeader("Chunk-Id",1)
    httpRequest.setHeader("Chunk-Name","filehashcode")
    httpRequest.setHeader("Chunk-CheckSum","")
    httpRequest.setHeader("Chunk-Length","")
    httpRequest.setHeader("Content-Disposition","attachment;filename=\""+URLEncoder.encode(file.getName,"utf-8")+"\"")
    httpRequest.setContent(ChannelBuffers.wrappedBuffer(cbuffer))
    println("client request...")

    client(httpRequest) onSuccess(rosponse=>
      println("upload file successed!")
     )
    }finally{
      fileOutStreamChannel.close()
    }

  }
}
object FileClient {

}

package cn.changhong.finagle.http.client

import java.io.{File, FileOutputStream}
import java.net.URLEncoder
import java.nio.channels.FileChannel

import com.twitter.finagle.Http
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer, ChannelBufferOutputStream}
import org.jboss.netty.handler.codec.http.{DefaultHttpRequest, HttpMethod, HttpVersion, DefaultHttpResponse}


/**
 * Created by yangguo on 14-10-24.
 */
object FileClient {
//  val client=Http.newService("localhost:10000")
//  val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,"/")
//  val file=new File("/Users/yangguo/Downloads/spark-1.0.0-bin-hadoop2.tar")
//  val fileOutStreamChannel=new FileOutputStream(file).getChannel
//  val cbuffer=fileOutStreamChannel.map(FileChannel.MapMode.READ_ONLY,0,file.length())
//  val httpRequest=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.PUT,"/")
//  httpRequest.setHeader("Content-Type", "application/octet-stream")
//  httpRequest.setHeader("Content-Transfer-Encoding","binary")
//  httpRequest.setHeader("Content-Disposition","attachment;filename=\""+URLEncoder.encode(file.getName,"utf-8")+"\"")
//   httpRequest.setContent(ChannelBuffers.wrappedBuffer(cbuffer))
//  client(httpRequest) onSuccess(rosponse=>
//    println("upload file successed!")
//   )
  val file=new File("/Users/yangguo/Downloads/wkaliedit.dmg")

}

package cn.changhong.demo

import java.io.{FileInputStream, FileOutputStream}
import java.nio.ByteBuffer
import java.nio.charset.Charset

/**
 * Created by yangguo on 14-11-17.
 */
object NioChannelDemo extends App{
  val header=ByteBuffer.allocate(4*4)
  val body=ByteBuffer.allocate(1024)
  val content="channel buffer!中文"

  body.put(content.getBytes(Charset.forName("utf8")))
  body.flip()
  header.putInt(body.limit())
  header.putInt(body.limit())
  header.putInt(body.limit())
  header.putInt(body.limit())
  header.flip()
  println("instream body length="+body.limit())
  val fileOutChannel=new FileOutputStream("/Users/yangguo/channel.log").getChannel

  fileOutChannel.write(Array(header,body))
  fileOutChannel.close()
  val fileInChannel=new FileInputStream("/Users/yangguo/channel.log").getChannel
  header.clear()
  body.clear()

  (fileInChannel.read(Array(header,body)))

  header.flip()
  body.flip()
  val contentLength=body.limit()

  val inHeanders:Array[Long]=new Array[Long](2)
  header.asLongBuffer().get(inHeanders)

  inHeanders.foreach(println)
  if(contentLength.equals(inHeanders(0))) {
    println(new String(header.array(),Charset.forName("utf8")) + "," + new String(body.array(), Charset.forName("utf8")))
  }else{
    throw new Exception("error..."+contentLength+","+inHeanders(0)+","+new String(body.array(),Charset.forName("utf8")))
  }

//  4,4=type,content-length
}

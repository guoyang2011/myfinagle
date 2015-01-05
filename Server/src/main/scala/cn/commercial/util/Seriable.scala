package cn.commercial.util

import net.liftweb.json._
import net.liftweb.json.Extraction._
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.util.CharsetUtil

/**
 * Created by yangguo on 14-10-30.
 */
object Seriable {
  def transferStringToMap(params:String):Map[String,Any]={
    parse(params).values.asInstanceOf[Map[String,Any]]
  }
  def transferByteBufToMap(content:ChannelBuffer,charset:java.nio.charset.Charset=CharsetUtil.UTF_8):Map[String,Any]={
    transferStringToMap(transferByteBufToString(content,charset))
  }
  def transferByteBufToString(content:ChannelBuffer,charset:java.nio.charset.Charset=CharsetUtil.UTF_8):String={
    val buffer=
      if(content.hasArray) content.array()
      else {
        val tBuffer = new Array[Byte](content.readableBytes())
        content.readBytes(tBuffer)
        tBuffer
      }
    new String(buffer,charset)
  }
  private[this] implicit val format = net.liftweb.json.DefaultFormats
  def transferObjToNettyByteBuf[T](content:Option[T],charset:java.nio.charset.Charset=CharsetUtil.UTF_8):Option[ChannelBuffer]={
    content match {
      case Some(t)=>
        val jString=compact(render(decompose(t)))
        Some(ChannelBuffers.copiedBuffer(jString,charset))
    }
  }

  case class UserInfo(age:Int)
  case class MapBean(username:String,password:Int,userInfo:List[UserInfo])
  def main(args:Array[String]): Unit ={
    val user=MapBean("username",1232,List(UserInfo(32432),UserInfo(0)))
    val user_jstr=compact(render(decompose(user)))
    val map=parse(user_jstr).values.asInstanceOf[Map[String,Any]]
    map foreach{case (key,value)=>println(key+","+(value match {
      case l:List[Map[String,Any]]=>
        var p=""
        l.foreach(m=>m foreach{case (k,v)=>p+=(k+","+v+","+v.getClass.getName)})
        p
      case t=>t.getClass.getName
    }))}
  }
}

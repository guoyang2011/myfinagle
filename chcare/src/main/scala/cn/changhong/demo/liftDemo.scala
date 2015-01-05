package cn.changhong.demo

import java.io.Serializable

import cn.changhong.persistent.Tables.Tables.AccessTokenRow

import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.util.CharsetUtil

import net.liftweb.json._
import net.liftweb.json.Extraction._


import net.liftweb.json.Serialization._
/**
 * Created by yangguo on 14-11-6.
 */

object Seriable extends App {

  import net.liftweb.json._
  import net.liftweb.json.Extraction._

//  implicit val format = net.liftweb.json.DefaultFormats
//  Demo.liftDemo
  private[Seriable] object Demo {

    trait Handler {
      //  @throws(classOf[Exception])
      //  def executor(bean:Bean):Bean
    }

    object Tables {

      trait Bean {}
      case class Token(accessToken: String, refreshToken: String, userId: Long, clientId: String, createdAt: Long, expiresIn: Int, clientType: Int, tokenType: Int)

      case class ResponseContent[T](var status:Int,var content:T)

      case class Person(username: String, password: Option[String], age: Int, sex: Boolean)

      case class User[T](var bean: Person, var t: T)

      case class DefaultHandler(name: String) extends Handler

    }

    import Tables._
    implicit val formats=DefaultFormats

    val person = Person("p_name", Some("p_password"), 24, false)
    val user = User(person, person)

//    def decoder[T](str:String)(implicit t:Manifest[T]): ResponseContent[_ >: AccessTokenRow with String <: Serializable] = {
//      try {
//        if(t <:< Manifest[AccessTokenRow]) {
//          read[ResponseContent[AccessTokenRow]](str)
//        }else
//          throw new Exception("")
//      } catch {
//        case e => read[ResponseContent[String]](str)
//      }
//    }
//    def liftDemo: Unit = {
//      val token=AccessTokenRow("token","refreshToken",1,"sdasd",0,0,0,0)
//      val responseBean=ResponseContent(0,token)
//      println("toString=>"+write(responseBean))
//
//      val jsonStr=write(responseBean)
//      val bean=decoder[AccessTokenRow](jsonStr)
//      if(bean.status==0){
//        val content=bean.content.asInstanceOf[AccessTokenRow]
//        println(content.accessToken)
//      }
//
//      val str=read[ResponseContent[AccessTokenRow]](write(responseBean))
//      println(str.status)
//      val ss_token=compact(render(decompose(token)))
//      println(ss_token)
//      val token1=parse(ss_token).extract[String]
//
//
//
//
//
//
//
//
//
//      val s_user = compact(render(decompose(user))) //OBJECT -> JSON STRING
//      val tt = parse(s_user).extract[User[Person]] //JSON STRING -> OBJECT
//      println("LIFT JSON:JSON STRING->OBJECT:" + compact(render(decompose(tt))))
//
//      //LIFT JSON :LIST OBJECT ->JSON ARRAY
//      val ll = (1 to 2) map { index =>
//        val p = Person("p_name_" + index, None, index, index % 2 == 0)
//        User(p, p)
//      }
//      val lls = compact(render(decompose(ll))) //JSON ARRAY -> LIST OBJECT
//      println("LIFT：" + lls)
//      val llso = parse(lls).extract[Array[User[Person]]] //LIST OBJECT -> JSON ARRAY
//      llso foreach { item =>
//        println(item.bean.password)
//        println("LIFT V5=>" + compact(render(decompose(item))))
//      }
//    }

//    this.liftDemo
  }

//  def transferByteBufToString(content: ChannelBuffer, charset: java.nio.charset.Charset = CharsetUtil.UTF_8): String = {
//    val buffer =
//      if (content.hasArray) content.array()
//      else {
//        val tBuffer = new Array[Byte](content.readableBytes())
//        content.readBytes(tBuffer)
//        tBuffer
//      }
//    new String(buffer, charset)
//  }
//
//  def transferObjToByteBuf[T](content: Option[T], charset: java.nio.charset.Charset = CharsetUtil.UTF_8): Option[ChannelBuffer] = {
//    content match {
//      case Some(t) =>
//        val jString = compact(render(decompose(t)))
//        Some(ChannelBuffers.copiedBuffer(jString, charset))
//    }
//  }
}

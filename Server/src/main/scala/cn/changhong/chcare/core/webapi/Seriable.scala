package cn.changhong.chcare.core.webapi

import io.netty.util.CharsetUtil
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import net.liftweb.json._
import net.liftweb.json.Extraction._

/**
* Created by yangguo on 14-10-20.
*/
object Seriable extends App{
  private[Seriable] object Demo {
    trait Handler {
      //  @throws(classOf[Exception])
      //  def executor(bean:Bean):Bean
    }
    object Tables {

      trait Bean {}

      case class Person(username: String, password: Option[String], age: Int, sex: Boolean) extends Bean

      case class User[T](var bean: Person, var t: T) extends Bean

      case class DefaultHandler(name: String) extends Handler

    }
    import Tables._
    val person = Person("p_name", Some("p_password"), 24, false)
    val user = User(person, person)

    def gsonDemo: Unit = {
      /**
       * 使用gson作为scala json序列化库时注意：
       * 1.无法解析Option类型的数据字段，因此在设计bean时不能创建Option类型属性
       * 2.对于集合类型的序列化，不能使用scala原生集合类型，只能使用java集合类型，可以通过引入scala.scala.collection.JavaConverters._中相应方法在scala和java集合对象之间做相应转化
       */
      import scala.collection.JavaConverters._
      import com.google.gson.GsonBuilder
      val gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
      val s_user = gson.toJson(user) //OBJECT -> JSON STRING
      val r_user = gson.fromJson(s_user, classOf[User[Person]]) //JSON STRING -> OBJECT
      println("OBJECT -> JSON STRING:" + gson.toJson(r_user.bean) + "," + gson.toJson(r_user.t))
      val l_user = (1 to 10).map { index =>
        val p = Person("p_name_" + index, None, index, index % 2 == 0)
        User(p, p)
      }.toList.asJava
      val l_s_user = gson.toJson(l_user) //LIST OBJECT -> JSON ARRAY ,注意转化的list必须是java.util.list
      println("LIST OBJECT -> JSON STRING:" + l_s_user)

      val ll_s_user = gson.fromJson(l_s_user, classOf[Array[User[Person]]])
      ll_s_user foreach { item =>
        println(gson.toJson(item))
      }
    }

    def liftDemo: Unit = {
      import net.liftweb.json._
      import net.liftweb.json.Extraction._
      implicit val format = net.liftweb.json.DefaultFormats
      val s_user = compact(render(decompose(user))) //OBJECT -> JSON STRING
      val tt = parse(s_user).extract[User[Person]] //JSON STRING -> OBJECT
      println("LIFT JSON:JSON STRING->OBJECT:" + compact(render(decompose(tt))))

      //LIFT JSON :LIST OBJECT ->JSON ARRAY
      val ll = (1 to 2) map { index =>
        val p = Person("p_name_" + index, None, index, index % 2 == 0)
        User(p, p)
      }
      val lls = compact(render(decompose(ll))) //JSON ARRAY -> LIST OBJECT
      println("LIFT：" + lls)
      val llso = parse(lls).extract[Array[User[Person]]] //LIST OBJECT -> JSON ARRAY
      llso foreach { item =>
        println(item.bean.password)
        println("LIFT V5=>" + compact(render(decompose(item))))
      }
    }
    this.liftDemo
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
  implicit val format = net.liftweb.json.DefaultFormats
  def transferObjToByteBuf[T](content:Option[T],charset:java.nio.charset.Charset=CharsetUtil.UTF_8):Option[ChannelBuffer]={
    content match {
      case Some(t)=>
        val jString=compact(render(decompose(t)))
        Some(ChannelBuffers.copiedBuffer(jString,charset))
    }
  }
//  val tt=transferObjToByteBuf(Some(Demo.user))
//  println("tt read data:"+tt.get.readableBytes())
}

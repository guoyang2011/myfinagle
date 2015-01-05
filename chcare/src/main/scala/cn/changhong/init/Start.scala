package cn.changhong.init

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import cn.changhong.controllers.{ResponseCode, ResponseContent, DefaultRouterService}
import cn.changhong.persistent.Tables.Tables.AccessTokenRow
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.{Duration, Future}
import net.liftweb.json.Serialization._
import net.liftweb.json._
import org.jboss.netty.handler.codec.http._
import cn.changhong.controllers.util._

case class TT(status:Int,content:Option[AccessTokenRow])
/**
 * Created by yangguo on 14-11-5.
 */
object Start extends App{
  val service=ExceptionFilterService andThen DefaultRouterService
  ServerBuilder()
    .codec(Http())
    .bindTo(new InetSocketAddress(10000))
    .readTimeout(Duration(1,TimeUnit.SECONDS))
    .name("chcare")
    .build(service)
//
//  Thread.sleep(1000)
//  clientTest.register
  clientTest.login
//  clientTest.thirdAuth

}
object clientTest {
  val client = com.twitter.finagle.Http.newService("localhost:10000")

  def login={
    implicit val format=DefaultFormats
    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,"/oauth/token")
    val map=Map("account"->"yangguo","password"->"Some(123456)","tokenType"->0,"clientType"->0)
    request.setHeader("Client_Id","UUUUUUUUUID")
    request.setContent(transferObjToChBuffer(map))

    client(request) onSuccess { response =>
      if (response.getStatus == HttpResponseStatus.OK) {
        val str = (transferByteBufToString(response.getContent))
        val responseBean = try {
          read[ResponseContent[AccessTokenRow]](str)
        } catch {
          case e => read[ResponseContent[String]](str)
        }
        if (responseBean.status == 0) {
          val token=responseBean.content.asInstanceOf[AccessTokenRow]
          println(token.accessToken + "," + token.refreshToken)
        }
      }
    } onFailure(e=>println(e.getLocalizedMessage+"onFailure...."))
  }

  def register = {
    import net.liftweb.json.Serialization._
    implicit val format=DefaultFormats
    val request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/register/user")
    val map = Map("account" -> "chcare", "iphone" -> "1582815274", "email" -> "83885590@qq.com", "password" -> "123456")
    request.setHeader("Client_Id", "UUUUUUUUUUUID")
    request.setContent(transferObjToChBuffer(map))
    client(request) onSuccess { response =>
      if (response.getStatus.equals(HttpResponseStatus.OK)) {
        val str=(transferByteBufToString(response.getContent))
      } else {
        println(response.getStatus + ",status code")
      }
    } onFailure { e =>
      println(e.getLocalizedMessage + ",error!")
    }
  }
  def thirdAuth={
    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,"/oauth/platform/token")
    request.setHeader("Client_Id","UUUUUUUUUUID")
    request.setHeader("Access_Token","MGY1YTgyMWQtNWMzYS00MzViLTlmNjgtNzU5MGVmOWU2Yjkz")
    request.setHeader("User_Id","5")
    client(request) onSuccess { response =>
      if (response.getStatus.equals(HttpResponseStatus.OK)) {
        println(transferByteBufToString(response.getContent))
      } else {
        println(response.getStatus + ",status code")
      }
    } onFailure{e=>
      println(e.getLocalizedMessage)

    }
  }
}
object ExceptionFilterService extends SimpleFilter[HttpRequest,HttpResponse]{
  override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = {
    service(request) handle{
      case e=>
      val content=ResponseContent(ResponseCode.service_inline_cause,Some(e.getLocalizedMessage))
       createResponseEncoderContent(content)
    }
  }
}

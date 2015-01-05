package cn.changhong.controllers


import com.twitter.finagle.Service
import com.twitter.finagle.http.Response
import com.twitter.util.Future
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.handler.codec.http.{HttpResponseStatus, HttpMethod, HttpResponse}
import org.jboss.netty.util.CharsetUtil

/**
 * Created by yangguo on 14-11-4.
 */
object AccountService extends Service[RequestBean,HttpResponse]{
  import util._
  import cn.changhong.persistent.Dao.UserDao._
  import cn.changhong.persistent.Dao.AccessTokenDao._
  override def apply(request: RequestBean): Future[HttpResponse]={
    Future.value((request.request.getMethod,request.path) match {
      case (HttpMethod.POST, "/account/oauth/token") =>
        val map=transferByteBufToMap(request.request.getContent)
        login(map.get("account").get.toString,map.get("password").get.toString,map.get("clientId").map(_.toString),map.get("accessToken").map(_.toString))
      case (HttpMethod.POST, "/account/register") =>Response()
      case _ =>Response()
    })
  }
  def login(account:String,password:String,clientId:Option[String],accessToken:Option[String]): HttpResponse ={
    validate(account,password) match{
      case Some(user)=>
        findAccessToken(clientId,accessToken,user.id) match{
          case Some(token)=>
          case None=>
        }
      case None=>
        val content=ResponseContent(ResponseCode.User_not_exit,Some("User Not Exit!"))
        createResponse(200,None,ChannelBuffers.copiedBuffer(transferObjToJsonStr(content),CharsetUtil.UTF_8))
    }

  }
  def register1(account:Option[String],iphone:Option[String],email:Option[String],password:String):HttpResponse={
    Response()
  }
}
import net.liftweb.json._
object util {
  def createResponse(code:Int,header:Option[Map[String,String]],content:ChannelBuffer):Response={
    val response=Response()
    response.setStatusCode(code)
    if(header.isDefined) header.get foreach{kv=>response.setHeader(kv._1,kv._2)}
    response.setContent(content)
    response
  }
  private[this] implicit val format = DefaultFormats
  def transferObjToJsonStr[T](t:T):String={
    compat(render(decompose(t)))
  }
  def transferStringToMap(params: String): Map[String, Any] = {
    parse(params).values.asInstanceOf[Map[String, Any]]
  }
  def transferByteBufToMap(content: ChannelBuffer, charset: java.nio.charset.Charset = CharsetUtil.UTF_8): Map[String, Any] = {
    transferStringToMap(transferByteBufToString(content, charset))
  }

  def transferByteBufToString(content: ChannelBuffer, charset: java.nio.charset.Charset = CharsetUtil.UTF_8): String = {
    val buffer =
      if (content.hasArray) content.array()
      else {
        val tBuffer = new Array[Byte](content.readableBytes())
        content.readBytes(tBuffer)
        tBuffer
      }
    new String(buffer, charset)
  }
}

package cn.changhong.controllers


import java.nio.charset.Charset
import java.sql.Timestamp
import java.util.Date

import cn.changhong.persistent.Tables.Tables.{UserRow}
import com.twitter.finagle.Service
import com.twitter.finagle.http.Response
import com.twitter.util.Future
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.handler.codec.http.{QueryStringDecoder, HttpRequest, HttpMethod, HttpResponse}
import org.jboss.netty.util.CharsetUtil

import util._
import cn.changhong.persistent.Dao.AccessTokenDao._
import cn.changhong.persistent.Dao.UserDao._

/**
 * Created by yangguo on 14-11-4.
 */
object DefaultInterceptorService extends Service[CHRequest,HttpResponse] {
  override def apply(request: CHRequest): Future[Response] = {
    Future.value(Response())
  }
}
object DefaultRouterService extends Service[HttpRequest,HttpResponse]{
  override def apply(request: HttpRequest): Future[HttpResponse] = {
    val clientId=request.getHeader("Client_Id")
    if(clientId==null){
      Future.value(createInvalidRequestParamsResponse())
    }else {
      val chReq = RequestBean()
      val decoder = new QueryStringDecoder(request.getUri)
      chReq.accessToken = request.getHeader("Access_Token") match{
        case s:String=>Some(s)
        case _=>None
      }
      chReq.userId = request.getHeader("User_Id") match {
        case s: String => Some(s.trim.toLong)
        case _ => None
      }
      chReq.clientId = clientId
      chReq.request = request
      chReq.path = decoder.getPath
      if (decoder.getParameters != null) chReq.urlParams = Some(decoder.getParameters)
      DispatcherService(chReq)
    }
  }
}


object DispatcherService extends Service[CHRequest,HttpResponse]{
  override def apply(request: CHRequest): Future[HttpResponse]= {
    (request.request.getMethod, request.path) match {
      case (HttpMethod.POST,"/register/app")=> //企业第一步返回APPID,APPKEY,APPSecret
        val map=transferByteBufToString(request.request.getContent)
        Future.value(Response())
      case (HttpMethod.POST,"/oauth/authorize")=> //企业第二步获取Authorize Token
        Future.value(Response())
      case (HttpMethod.POST, "/oauth/token") => //获取AccessToken
        val map = transferByteBufToMap(request.request.getContent)
        val username = map.get("account").get.toString
        val password = map.get("password").get.toString
        request.tokenType = Some(map.get("tokenType").get.toString.toInt)
        request.clientType = Some(map.get("clientType").get.toString.toInt)
        Future.value(login(username, password, request))
      case (HttpMethod.POST, "/register/user") => //注册使用用户
        val map = transferByteBufToMap(request.request.getContent)
        val username = map.get("account").get.toString
        val iphone = map.get("iphone") match {
          case Some(ip) => ip.toString
          case _ => username
        }
        val email = map.get("email") match {
          case Some(mail) => mail.toString
          case _ => username
        }
        val password = map.get("password").get.toString

        request.tokenType = Some(0)
        request.clientType = Some(0)
        Future.value(register(username, iphone, email, password, request))
      case (HttpMethod.POST, "/oauth/platform/token") => createAuthRouter(request)(authModelRouter)
      case _ => createAuthRouter(request)(defaultRouter)
    }
  }
  private[this] def defaultRouter(request:CHRequest):Future[Response]={
    DefaultInterceptorService(request)
  }
  private[this] def authModelRouter(request:CHRequest):Future[Response]={
    println("authModelRouter.....")
    Future.value(createAuthModelSuccessResponse())
  }
  private[this] def createAuthRouter(request:CHRequest)(routerService:(CHRequest)=>Future[Response]): Future[Response] = {
    if(request.userId.isEmpty||request.accessToken.isEmpty){
      Future.value(createInvalidRequestParamsResponse())
    }else {
      findAccessToken(request.userId.get, request.clientId) match {
        case Some(token) =>
          println(request.accessToken.get+","+token.accessToken)
          if (token.accessToken.equals(request.accessToken.get)) {//
            if (compareTime(token.createdAt, token.expiresIn)) {//超时
              routerService(request)
            } else Future.value(createExpiredTimestampResponse())
          } else Future.value(createInvalidTokenResponse())
        case _ => Future.value(createNoAuthResponse())
      }
    }
  }
  private[this] def compareTime(startTime:Long,expiredIn:Long):Boolean={
    val now=new Timestamp(new Date().getTime).getTime
    val endTime=now+expiredIn
    if(now<endTime && now>startTime) true
    else false
  }
  private[this] def login(account:String,password:String,request:CHRequest): HttpResponse = {
    val content = validate(account, password) match {
      case Some(user) =>
        val token = findAccessToken(user.id, request.clientId) match {
          case Some(aToken) => updateToken(request.clientId, user.id)
          case _ =>
            createToken(user.id, request.clientId, request.clientType.get, request.tokenType.get)
        }
        ResponseContent(ResponseCode.succeed, token)
      case _ => ResponseContent(ResponseCode.User_not_exit, Some("User Not Exit!"))
    }
    createResponse(ChannelBuffers.copiedBuffer(transferObjToJsonStr(content), CharsetUtil.UTF_8))
  }
  private[this] def register(account:String,iphone:String,email:String,password:String,request:CHRequest):HttpResponse={
    val userId=createUser(UserRow(-100,account,Some(iphone),Some(email),password))
    if(userId>0) //login(account,password,request)
      createResponse(transferObjToChBuffer(userId))
    else {
      val content = ResponseContent(ResponseCode.service_inline_cause, Some("Register User Info Failed!"))
      createResponse(transferObjToChBuffer(content))
    }
  }
}

import net.liftweb.json._
import net.liftweb.json.Extraction._
object util {
  def createInvalidRequestParamsResponse():Response={
    val content=ResponseContent(ResponseCode.invalid_request_parameters,Some("Invalid Request Parameters!"))
    createResponseEncoderContent(content)
  }
  def createAuthModelSuccessResponse():Response={
    val content=ResponseContent(ResponseCode.succeed,Some("Ok"))
    createResponseEncoderContent(content)
  }
  def createResponseEncoderContent[T](t:T):Response={
    createResponse(transferObjToChBuffer(t))
  }
  def createInvalidTokenResponse():Response={
    val content=ResponseContent(ResponseCode.invalid_token,Some("Invalid Token!"))
    createResponse(transferObjToChBuffer(content))
  }
  def createExpiredTimestampResponse():Response={
    val content=ResponseContent(ResponseCode.expired_timestamp,Some("Expired AccessToken Timestamp!"))
    createResponse(transferObjToChBuffer(content))
  }
  def createNoAuthResponse():Response={
    val content=ResponseContent(ResponseCode.request_method_not_support,Some("Not Auth Use This Method!"))
    createResponse(transferObjToChBuffer(content))
  }
  def createResponse(content:ChannelBuffer,code:Int=200,header:Option[Map[String,String]]=None):Response={
    val response=Response()
    response.setStatusCode(code)
    if(header.isDefined) header.get foreach{kv=>response.setHeader(kv._1,kv._2)}
    response.setContent(content)
    response
  }
  def createNoResourceFindResponse():Response={
    val content=ResponseContent(ResponseCode.no_such_method,Some("Request Service Is Not Find!"))
    createResponse(transferObjToChBuffer(content))
  }
  private[this] implicit val format = DefaultFormats
  def transferObjToJsonStr[T](t:T):String={
    compact(render(decompose(t)))
  }
  def transferStringToMap(params: String): Map[String, Any] = {
    parse(params).values.asInstanceOf[Map[String, Any]]
  }
  def transferByteBufToMap(content: ChannelBuffer, charset: java.nio.charset.Charset = CharsetUtil.UTF_8): Map[String, Any] = {
    transferStringToMap(transferByteBufToString(content, charset))
  }
  def transferObjToChBuffer[T](t:T,charset:Charset=CharsetUtil.UTF_8):ChannelBuffer={
    ChannelBuffers.copiedBuffer(transferObjToJsonStr(t),charset)
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


package cn.changhong.finagle.http

import java.net.InetSocketAddress
import java.sql.Timestamp
import java.util.concurrent.TimeUnit
import java.util.{UUID, Date}

import com.google.gson.reflect.TypeToken
import com.google.gson.{GsonBuilder, Gson}
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Response, Request}
import com.twitter.finagle.{ SimpleFilter, Service}
import com.twitter.util.{Duration, Future}
import org.apache.commons.dbcp.BasicDataSource
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.util.CharsetUtil
import cn.changhong.orm.Tables.Tables._

import scala.slick.lifted


// case class AUser(id: Long, userName: String, hashPassword: String)
//
// case class AccessToken(user_id: Long,client_id:Long, expires_in: Int, created_time: Long, scope: Int, token_type: Int, access_token: String, refresh_token: String)

/**
* Created by yangguo on 14-10-28.
*/
import scala.slick.driver.MySQLDriver.simple._
object MyAuth2Server {
  val gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
  object Auth2EntityDao {
    object Users {
      def validate(username: String, password: String): Option[UserRow] = {
        DB.withTransaction{implicit session=>
          Usertt.filter(u=>u.username === username && u.password === password).firstOption
        }
      }
      def findById(id: Long): Option[UserRow] = {
        DB.withTransaction{implicit session=>
          Usertt.filter{u=>u.id === id.toInt}.firstOption
        }
      }
    }

    object AccessTokens {
      def create(accessToken: AccessTokenRow): Boolean = {
        DB.withTransaction { implicit session =>
//          val access=(AccessToken returning AccessToken.map(_.accessToken)) += (accessToken)
//          if(access.equals(accessToken.accessToken)) true
//          else false
            AccessToken.insert(accessToken)
          true

        }
      }

      def findAccessToken(client_id: String, accessToken: String): Option[AccessTokenRow] = {
        DB.withTransaction { implicit session =>
          AccessToken.filter(a => a.clientId === client_id && a.accessToken === accessToken).firstOption
        }
      }

      def findRefreshToken(client_id: String, refreshToken: String): Option[AccessTokenRow] = {
         DB.withTransaction{implicit  session=>
          AccessToken.filter(a=>a.clientId === client_id && a.refreshToken === refreshToken).firstOption
         }
      }

      def findToken( user_id: Long,client_id: String): Option[AccessTokenRow] = {
        DB.withTransaction{implicit  session=>
          AccessToken.filter(a=>a.clientId === client_id && a.userId === user_id.toInt).firstOption
        }
      }
      def updateToken(accessToken:String,created_at:Long):Boolean={
        DB.withTransaction{implicit session=>
          AccessToken.filter(_.accessToken === accessToken).map(_.createdAt).update(created_at.toInt)
          true
        }
      }
    }

  }

  /**
   * test
   * @return
   */
  def generateToken(): String = {
    val key = UUID.randomUUID().toString
    new sun.misc.BASE64Encoder().encode(key.getBytes())
  }
  object BussinessLayer {
    object AuthService {
      private[this] def createAccessToken(user_id: Long, client_id: String): Option[AccessTokenRow] = {
        Auth2EntityDao.AccessTokens.findToken(user_id,client_id) match {
          case Some(token) =>
            if (Auth2EntityDao.AccessTokens.updateToken(token.accessToken, new Timestamp(new Date().getTime).getTime) == true)
              Some(token)
            else None
          case None =>
            val expires_in = 60 * 60
            val now = new Date()
            val created_at = new Timestamp(now.getTime).getTime
            def generateToken(): String = {
              val key = UUID.randomUUID().toString
              new sun.misc.BASE64Encoder().encode(key.getBytes())
            }
            val accessToken = generateToken()
            val refreshToken = generateToken()
            val token=AccessTokenRow(accessToken,refreshToken,user_id.toInt,client_id, created_at.toInt, expires_in)
            if(Auth2EntityDao.AccessTokens.create(token) == true)
              Some(token)
            else None
        }
      }
      def login(userName: String, password: String, client_id: String): Option[AccessTokenRow] = {
        Auth2EntityDao.Users.validate(userName, password) match {
          case Some(user) => createAccessToken(user.id, client_id)
        }
      }

      def refreshToken(client_id: String, user_id: Long, refreshToken: String): Option[AccessTokenRow] = {
        Auth2EntityDao.AccessTokens.findRefreshToken(client_id, refreshToken) match {
          case Some(token) =>
            if(Auth2EntityDao.AccessTokens.updateToken(token.accessToken, new Timestamp(new Date().getTime).getTime) == true)
              Some(token)
            else None
        }
      }

      def findAuthInfoByAccessToken(client_id: String, accessToken: String): Option[AccessTokenRow] = {
        Auth2EntityDao.AccessTokens.findAccessToken(client_id, accessToken)
      }
    }

  }

  object Controller {
    val loginregx = """(\S+),(\S+),(\S+),(\S+)""".r//username,password,clientid
    val refreshregx = """(\S+),(\d+),(\S+)""".r //clientid,userid,refreshToken

    object AuthService extends Service[HttpRequest, HttpResponse] {
      private[this] def b_login(username: String, password: String, clientid: String, accessToken: Option[String]): Future[HttpResponse] = {
        println("b_login....")
        Future.value {
          val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
          BussinessLayer.AuthService.login(username, password, clientid) match {
            case Some(token) =>
              val content = new Gson().toJson(token)
              response.setContent(ChannelBuffers.copiedBuffer(content, CharsetUtil.UTF_8))
              response
            case None =>
              response.setContent(ChannelBuffers.copiedBuffer("username or password invalidate", CharsetUtil.UTF_8))
              response
          }
        }
      }

      private[this] def login(request: HttpRequest): Future[HttpResponse] = {

        loginregx findFirstMatchIn readContent(request.getContent) match {
          case Some(m) =>
            b_login(m.group(1), m.group(2), m.group(3), None)

          case None =>
            Future.value(Response())
        }
      }

      def readContent(content: ChannelBuffer): String = {
        val buffer = content.hasArray match {
          case false =>
            val tbuffer = new Array[Byte](content.readableBytes())
            content.readBytes(tbuffer, 0, tbuffer.length)
            tbuffer
          case true => content.array()
        }
        val result: String = new String(buffer, CharsetUtil.UTF_8)
        println("server rcv Data:" + result)
        result
      }

      private[this] def refreshToken(request: HttpRequest): Future[HttpResponse] = {
        Future.value {
          println("RefreshToken:>>>>>????")
          val newToken = refreshregx findFirstMatchIn readContent(request.getContent) match {
            case Some(m) =>
              BussinessLayer.AuthService.refreshToken(m.group(1), m.group(2).toLong, m.group(3))
          }
          val response = Response()
          response.setContent(ChannelBuffers.copiedBuffer(newToken match {
            case Some(token) => new Gson().toJson(token)
            case None => "refresh token"
          }, CharsetUtil.UTF_8))
          response
        }
      }

      override def apply(request: HttpRequest): Future[HttpResponse] = {
        (request.getMethod(), request.getUri()) match {
          case (HttpMethod.POST, "/oauth2/token") => login(request)
          case (HttpMethod.POST, "/oauth2/token/refresh") => refreshToken(request)
          case _ => Future.value {
            val response = Response()
            response.setContent(ChannelBuffers.copiedBuffer("Not Find Resource!", CharsetUtil.UTF_8))
            response
          }
        }
      }
    }
  }
  val DB= {
    val ds = new BasicDataSource
    ds.setDriverClassName("com.mysql.jdbc.Driver")
    ds.setUsername("yangguo")
    ds.setPassword("123456")
    ds.setMaxActive(20)
    ds.setMaxIdle(10)
    ds.setInitialSize(5)
    ds.setUrl("jdbc:mysql://localhost:3306/crazycat")
    println("create datasource...")
    Database.forDataSource(ds)
  }
  def main(args:Array[String]): Unit = {

    Thread.sleep(4000)
    new Thread(new Runnable(){
      override def run(): Unit = server
    }).start()
    Thread.sleep(1000)
    client

  }
  def server: Unit ={
    val service: Service[HttpRequest, HttpResponse] = Controller.AuthService
    ServerBuilder()
      .codec(Http())
      .name("tokenService")
      .readTimeout(Duration(1, TimeUnit.SECONDS))
      .bindTo(new InetSocketAddress(10002))
      .build(service)
  }
  def client: Unit ={
    var token:Option[AccessTokenRow]=None

    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,"/oauth2/token")



    val content="username,password,crazycat_android_id,null"
    request.setContent(ChannelBuffers.copiedBuffer(content,CharsetUtil.UTF_8))
    val clientServer=com.twitter.finagle.Http.newService("localhost:10002")
    clientServer(request) onSuccess { response =>
      token =
        //          println(Controller.AuthService.readContent(response.getContent))
        Some(gson.fromJson(Controller.AuthService.readContent(response.getContent), new TypeToken[AccessTokenRow]() {}.getType).asInstanceOf[AccessTokenRow])


      println("request token refresh...")
      request.setUri("/oauth2/token/refresh")
      val requestBody: String = token match {
        case Some(t) => t.clientId + "," + t.userId + "," + t.refreshToken
        case None => ""
      }
      println("request content:"+requestBody)
      request.setHeader("Content-Length",requestBody.length);//设置长度很重要
      request.setContent(ChannelBuffers.copiedBuffer(requestBody, CharsetUtil.UTF_8))
      clientServer(request) onSuccess { res =>
        println("success:"+Controller.AuthService.readContent(res.getContent))
      } onFailure{e=>
        println(e.getStackTrace+","+e.getMessage)
      }
    } onFailure{e=>
      println(e.getStackTraceString)
    }
  }
}

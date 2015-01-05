package cn.changhong.persistent.Dao

import java.sql.Timestamp
import java.util.{Date, UUID}

import cn.changhong.persistent.Tables.Tables.UserRow
import cn.changhong.persistent.DBManager._
import cn.changhong.persistent.Tables.Tables._
import org.slf4j.LoggerFactory

import scala.slick.driver.MySQLDriver.simple._
import scala.util.{Failure, Success, Try}


/**
 * Created by yangguo on 14-11-3.
 */
trait Dao
object UserDao extends Dao{
  def validate(loginname: String, password: String): Option[UserRow] = {
    DBPool.withTransaction { implicit session =>
      User.filter(u => u.password === password && u.username === loginname || u.email === loginname || u.iphone === loginname).firstOption
    }
  }
  def findById(id: Long): Option[UserRow] = {
    DBPool.withTransaction { implicit session =>
      User.filter(u => u.id === id).firstOption
    }
  }
  @throws(classOf[Exception])
  def bindIPhone(id: Long, iphone: Option[String]): Int = {
    DBPool.withTransaction { implicit session =>
        User.filter(a => a.id === id).map(u => u.iphone).update(iphone)
    }
  }
  def bindEmail(id:Long,email:Option[String]):Int={
    DBPool.withTransaction { implicit session =>
      User.filter(a => a.id === id).map(u => u.email).update(email)
    }
  }
  @throws(classOf[Exception])
  def createUser(user:UserRow): Long ={
    DBPool.withTransaction { implicit session =>
      (User returning User.map(_.id)).insert(user)
    }
  }
  def changePasswd(id:Long,passwd:String):Int={
    DBPool.withTransaction{implicit session=>
      User.filter(a=>a.id===id).map(u=>u.password).update(passwd)
    }
  }
}
object AccessTokenDao extends Dao{
  @throws(classOf[Exception])
  def createToken(userId:Long,clientId:String,clientType:Int,tokenType:Int):Option[AccessTokenRow]= {
    DBPool.withTransaction {implicit session =>
      val token = createTokenBean(userId, clientId, new Timestamp(new Date().getTime).getTime, 3600,clientType,tokenType)
      if (AccessToken.insert(token).equals(1)) Some(token)
      else None
    }
  }
  def findAccessToken(userId:Long,clientId:String):Option[AccessTokenRow]={
    DBPool.withTransaction{implicit session=>
      AccessToken.filter(a=>a.userId === userId && a.clientId === clientId).firstOption
    }
  }
  def findRefreshToken(clientId:String,refreshToken:String,userId:Long):Option[AccessTokenRow]={
    DBPool.withTransaction{implicit session=>
      AccessToken.filter(a=>a.userId === userId && a.refreshToken === refreshToken && a.userId === userId).firstOption
    }
  }
  @throws(classOf[Exception])
  def updateToken(clientId:String,userId:Long):Option[AccessTokenRow]={
    DBPool.withTransaction{implicit session=>
      val token=createTokenBean(userId,clientId)
      if(AccessToken.filter(a=>a.userId === userId  && a.clientId === clientId).map(a=>(a.accessToken,a.refreshToken)).update((token.accessToken,token.refreshToken))>0) Some(token)
      else None
    }
  }
  private[this] def createTokenBean(userId:Long,clientId:String,createdAt:Long=0,expiresIn:Int=0,clientType:Int=0,tokenType:Int=0):AccessTokenRow={
    val accessToken=generateToken()
    val refreshToken=generateToken()
    AccessTokenRow(accessToken,refreshToken,userId,clientId,createdAt,expiresIn,clientType,tokenType)
  }
  private[this] def generateToken(): String = {
    val key = UUID.randomUUID().toString
    new sun.misc.BASE64Encoder().encode(key.getBytes())
  }
}

object test{
  def main(args:Array[String]): Unit ={
    val log=LoggerFactory.getLogger("Dao")
    val user=UserRow(1000,"userme2",Some("pword2"),Some("ddsd3"),"password")
    val ins=Try(
      UserDao.createUser(user)
    )
    ins match{
      case Success(v)=>println(v)
      case Failure(e)=>println(e.getLocalizedMessage+","+e.getCause)
    }
    if(UserDao.bindEmail(1,Some("bindphone2"))==1){
      log.info("bind Email Ok")
    }
    if(UserDao.bindIPhone(1,Some("bindIphone2"))==1) log.info("bind iphone ok")
    val userInfo=Try(UserDao.bindEmail(1,Some("bindI")))
    userInfo match{
      case Success(v)=>log.info(v.toString)
      case Failure(e)=>log.info(e.getLocalizedMessage)
    }
  }
}


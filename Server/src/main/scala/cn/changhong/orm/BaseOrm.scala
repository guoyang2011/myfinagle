package cn.changhong.orm

/**
 * Created by yangguo on 14-10-28.
 */

import com.changhong.orm.Tables.Tables._
import org.apache.commons.dbcp.BasicDataSource
import scala.slick.driver.MySQLDriver.simple._
//import


object tt{
}
//case class User(userId: Int, userName: Option[String], password: Option[String], email: Option[String],sex:Option[String])
//class Users(tag: Tag) extends Table[User](tag, "user") {
//  def userId = column[Int]("userid", O.PrimaryKey)
//  def userName = column[Option[String]]("username")
//  def password = column[Option[String]]("password")
//  def email = column[Option[String]]("email")
//  def sex = column[Option[String]]("sex")
//  def * = (userId, userName, password, email,sex) <> (User.tupled, User.unapply _)
//}

//case class User(userId:Int,userName:String,password:String,email:String)
//class Users(tag:Tag) extends Table[User](tag,"USER"){
//  def userId:Column[Int]=column[Int]("USERID",O.PrimaryKey)
//  def userName:Column[String]=column[String]("USERNAME")
//  def password:Column[String]=column[String]("PASSWORD")
//  def email:Column[String]=column[String]("EMAIL")
//  def sex:Column[String]=column[String]("SEX")
//  def * = (userId,userName,password,email,sex) <> (User.tupled, User.unapply _)
//}

//case class ClientGrantType(clientId: String, grantTypeId: Int)
//class ClientGrantTypes(tag: Tag) extends Table[ClientGrantType](tag, "client_grant_type") {
//  def clientId = column[String]("client_id")
//  def grantTypeId = column[Int]("grant_type_id")
//  def * = (clientId, grantTypeId) <> (ClientGrantType.tupled, ClientGrantType.unapply _)
//  val pk = primaryKey("pk_client_grant_type", (clientId, grantTypeId))
//}
object TestDB {

}
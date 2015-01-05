package cn.commercial.model

import cn.changhong.orm.Tables.Tables.{AccessTokenRow, UserRow}
import cn.changhong.finagle.http.MyAuth2Server._
import cn.changhong.orm.Tables.Tables._
import scala.slick.
/**
 * Created by yangguo on 14-11-3.
 */
object AuthDao{
  def login(username:String,password:String,clientId:String,accessToken:Option[String]): Option[AccessTokenRow]={
    DB.withTransaction{ implicit session=>
     Usertt.filter(u=>u.username === username)
    }
  }
}

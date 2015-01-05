package cn.changhong.orm

import scala.slick.codegen.SourceCodeGenerator

/**
 * Created by yangguo on 14-10-29.
 */
object CodeGen extends App{
  SourceCodeGenerator.main(Array(
    "scala.slick.driver.MySQLDriver",
    "com.mysql.jdbc.Driver",
    "jdbc:mysql://localhost:3306/crazycat",
    "src/main/scala/",
    "cn.changhong.orm.Tables",
    "yangguo",
    "123456"
  ))
}

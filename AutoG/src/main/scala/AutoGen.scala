/**
 * Created by yangguo on 14-11-5.
 */
import scala.slick.codegen.SourceCodeGenerator

/**
 * Created by yangguo on 14-11-3.
 */
object AutoGen  {
  def main(args:Array[String]) :Unit={
    SourceCodeGenerator.main(Array(
      "scala.slick.driver.MySQLDriver",
      "com.mysql.jdbc.Driver",
      "jdbc:mysql://localhost:3306/crazycat",
      "src/main/scala/",
      "cn.changhong.persistent.Tables",
      "yangguo",
      "123456"
    ))
  }
}
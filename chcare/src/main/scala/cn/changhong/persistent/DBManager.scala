package cn.changhong.persistent

import org.apache.commons.dbcp.BasicDataSource
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yangguo on 14-11-3.
 */
object DBManager {
  val DBPool={
    val ds=new BasicDataSource
    ds.setDriverClassName("com.mysql.jdbc.Driver")
    ds.setUsername("yangguo")
    ds.setPassword("123456")
    ds.setMaxActive(20)
    ds.setMaxIdle(10)
    ds.setInitialSize(5)
    ds.setUrl("jdbc:mysql://localhost:3306/crazycat")
    ds.setMaxWait(1)
    Database.forDataSource(ds)
  }
}

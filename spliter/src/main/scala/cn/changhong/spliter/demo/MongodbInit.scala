package cn.changhong.spliter.demo

import com.mongodb.MongoClient

/**
 * Created by yangguo on 14-12-4.
 */
object MongodbInit {
  def main(args:Array[String]): Unit ={
    val mongo=new MongoClient("localhost",27017)

  }
}

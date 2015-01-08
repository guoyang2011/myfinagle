package cn.changhong.zipkin

import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future

/**
 * Created by yangguo on 15-1-7.
 */
object StartEndpointServer {
  def main(args:Array[String]): Unit ={

  }
  def traceRpc(serverName:String,method:String,request:Request)(block:(Request)=>Future[Response]):Future[Response]={
    ???
  }
}

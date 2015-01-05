package cn.commercial.controller

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

/**
 * Created by yangguo on 14-11-3.
 */
object AutoService extends SimpleFilter[Request,Response]{
//  override def apply(request: Request): Future[Response] = {
//    request.getUri() match{
//      case Router.RouterType.authService=>
//      case _=>
//    }
//  }
  val tokenR="""(\S+) (\S+)""".r

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    request.getUri() match {
      case Router.RouterType.authService=> //获取token
      case _=>
        tokenR findFirstMatchIn request.getParam("Authorization") match{
          case Some(m)=> service(request) //判断token
          case _=>
        }
    }
  }
}

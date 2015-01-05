package cn.commercial.controller

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

/**
 * Created by yangguo on 14-11-3.
 */
object AccountService extends Service[Request,Response]{
  override def apply(request: Request): Future[Response] = {

  }
}

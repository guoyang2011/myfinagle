package cn.commercial.controller

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future

import scala.util.matching.Regex

/**
 * Created by yangguo on 14-11-3.
 */
object Router extends Service[Request,Response]{
  val routerProvider=Map(RouterType.accountService->AccountService,RouterType.familyService->FamilyService)
  object RouterType{
    val accountService="""/accountservice/.*""".r
    val familyService="""/familyservice/.*""".r
    val authService="""/auth/.*""".r
  }
  override def apply(request: Request): Future[Response] = {
    request.getUri() match {
      case RouterType.accountService => selectRouterService(RouterType.accountService, request)
      case RouterType.familyService => selectRouterService(RouterType.familyService, request)
      case RouterType.authService=>selectRouterService(RouterType.authService,request)
      case _ =>noFindRouterService(request)
    }
  }
  def selectRouterService(reg:Regex,request:Request): Future[Response] ={
    routerProvider.get(reg) match{
      case service:Service[Request,Response]=>service(request)
      case _=>Future.value(Response())
    }
  }
  def noFindRouterService(request:Request):Future[Response]={
    val response=Response()
    response.setContentString("")
    Future.value(Response())
  }
}

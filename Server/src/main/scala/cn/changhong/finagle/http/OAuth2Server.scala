//package cn.changhong.finagle.http
//
//import java.sql.Timestamp
//import java.util
//import java.util.{Date, UUID}
//
//import com.twitter.finagle.http.{ Response}
////import com.twitter.finagle.oauth2.{ AuthInfo, AccessToken, DataHandler}
//import com.twitter.finagle._
//import com.twitter.util.Future
////import com.twitter.finagle.oauth2._
//import org.jboss.netty.handler.codec.http.{HttpMethod, HttpVersion, DefaultHttpRequest, HttpRequest}
//
//
///**
// * Created by yangguo on 14-10-27.
// */
//object OAuth2Server extends App{
//  def generateToken():String={
//    val key=UUID.randomUUID().toString
//    new sun.misc.BASE64Encoder().encode(key.getBytes())
//  }
//  object OAuth2Entity{
//    case class User(id:Long,//用户
//                    userName:String,
//                    hashPassword:String)
//    /*
//    第三方授权申请的表
//     */
//    case class ThirdPartsAuthorizationCode(aCode:String)
////    case class ThirdParts(id:Long,appName:String,hashSecret:String,scope:Int)
//    case class AccessTokens(user_id:Long,//用户ID
//                            access_token:String,//Access Token
//                            reflesh_token:String,//Access Token失效后用于重新刷新的Token
//                            token_type:Int,//Access Token的类型
//                            scope:Int,//AccessToken最终访问权限
//                            expires_in:Int,//Access Token有效时间，单位为秒
//                            created_at:Long,//Access Token创建时间
//                            client_id:Long)//终端的ID号或者第三方注册的App Key
//  }
//
//
//
//  case class User(id:Long,username:String,email:String,password:String)
//  case class Client(id:String,secret:String,redirect_uri:String,scope:String)
//  case class AccessTokens(accessToken:String,refreshToken:Option[String],userId:Long,scope:Option[String],expiresIn:Int,createdAt:Timestamp,clientId:String)
//  case class AuthCode(authorizationCode:String,userId:Long,redirectUri:Option[String],createdAt:Timestamp,scope:Option[String],clientId:String,expiresIn:Int)
//  object Dao{
//    object ClientDao {
//      def validate(id: String, secret: String, grantType: String): Boolean = {
//        true
//      }
//      def findById(id:String):Option[Client]= {
//        Some(Client("", "", "", ""))
//      }
//    }
//    object UserDao{
//      def findUser(username:String,password:String):Option[User]={
//        Some(User(1,"","",""))
//      }
//      def getById(id:Long):Option[User]={
//        Some(User(1,"","",""))
//      }
//    }
//    object AccessTokens{
//      def create(accessToken:AccessToken): Unit ={
//        //insert into  db
//      }
//      def deleteExistingAndCreate(accessToken:AccessTokens,userId:Long,clientId:String): Unit ={
//        //AccessTokens.where(a=>a.clientId==clientId && a.userId == userId).delete
//        //AccessTokens.insert(accessToken)
//      }
//      def findToken(userId:Long,clientId:String):Option[AccessTokens]={
//        None
//      }
//      def findAccessToken(token:String):Option[AccessTokens]={
//        //AccessTokens.where(a=>a.accessToken == token).fireshOption
//        None
//      }
//      def findRefreshToken(token:String):Option[AccessTokens]= {
//        //AccessTokens.where(a=>a.accessToken == token).fireshOption
//        None
//      }
//    }
//    object AuthCodes{
//      def find(code:String): Option[AuthCode] ={
//        None
//      }
//    }
//  }
////  val dataHandler=new DataHandler[User](){
////    override def validateClient(clientId: String, clientSecret: String, grantType: String): Future[Boolean] =
////      Future.value(Dao.ClientDao.validate(clientId,clientSecret,grantType))
////
////    override def findClientUser(clientId: String, clientSecret: String, scope: Option[String]): Future[Option[User]] =
////      Future.value(Dao.UserDao.findUser(clientId,clientSecret))
////
////    override def createAccessToken(authInfo: AuthInfo[User]): Future[AccessToken] = {
////      val accessTokenExpiresIn=60*60
////      val now = new Date()
////      val createdAt=new Timestamp(now.getTime)
////      val refreshToken = Some(generateToken())
////      val accessToken=generateToken()
////      val tokenObject=AccessTokens(accessToken,refreshToken,authInfo.user.id,authInfo.scope,accessTokenExpiresIn,createdAt,authInfo.clientId)
////      Dao.AccessTokens.deleteExistingAndCreate(tokenObject,authInfo.user.id,authInfo.clientId)
////      //new scalaoauth2.provider.AccessToken(accessToken,refreshToken,authInfo.scope,Some(accessTokenExpiresIn.toLong),createdAt)
////      Future.value(AccessToken(tokenObject.accessToken,tokenObject.refreshToken,tokenObject.scope,Some(tokenObject.expiresIn),tokenObject.createdAt))
////    }
////
////    override def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = {
////      //refreshToken is validate
////      createAccessToken(authInfo)
////    }
////
////    override def findAuthInfoByAccessToken(accessToken: oauth2.AccessToken): Future[Option[AuthInfo[User]]] = {
////      Future.value{
////         Dao.AccessTokens.findAccessToken(accessToken.token) map{a=>
////          val user=Dao.UserDao.getById(a.userId).get
////          AuthInfo(user,a.clientId,a.scope,Some(""))
////        }
////      }
////    }
////
////    override def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = {
////      Future.value {
////        Dao.AccessTokens.findRefreshToken(refreshToken) map{a =>
////          val user=Dao.UserDao.getById(a.userId).get
////          AuthInfo(user,a.clientId,a.scope,Some(""))
////        }
////      }
////    }
////
////    override def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = {
////      Future.value{
////        Dao.AccessTokens.findToken(authInfo.user.id,authInfo.clientId) map {tokenObject =>
////          (AccessToken(tokenObject.accessToken, tokenObject.refreshToken, tokenObject.scope, Some(tokenObject.expiresIn), tokenObject.createdAt))
////        }
////      }
////    }
////
////    override def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = {
////      Future.value {
////        Dao.AuthCodes.find(code) map { a =>
////          val user = Dao.UserDao.getById(a.userId).get
////          AuthInfo(user, a.clientId, a.scope, a.redirectUri)
////        }
////      }
////    }
////
////    override def findUser(username: String, password: String): Future[Option[User]] = {
////      Future.value{
////        Dao.UserDao.findUser(username,password)
////      }
////    }
////
////    override def findAccessToken(token: String): Future[Option[AccessToken]] = {
////      Future.value {
////        Dao.AccessTokens.findAccessToken(token) map { tokenObject =>
////          (AccessToken(tokenObject.accessToken, tokenObject.refreshToken, tokenObject.scope, Some(tokenObject.expiresIn), tokenObject.createdAt))
////        }
////      }
////    }
////  }
//
////  val auth=new OAuth2Filter[User](dataHandler) with OAuthErrorInJson
////
////  val hello=new Service[OAuth2Request[User],Response]{
////    override def apply(request: OAuth2Request[User]): Future[Response] = {
////      Future.value(Response())
////    }
////  }
////  val tokens =new OAuth2Endpoint(dataHandler) with OAuthErrorInJson with OAuthTokenInJson
////
//  val auth=new OAuth2Filter[User](null)//new OAuth2Filter[User](dataHandler)
//  val hello = new Service[OAuth2Request[User],Response] {
//    override def apply(request: OAuth2Request[User]): Future[Response] = {
//      Future.value(Response())
//    }
//  }
//
////  trait AccessTokenFetcher{
////    def matches(request:HttpRequest):Boolean
////    def fetch(request:HttpRequest):FetchResult
////  }
////  case class FetchResult(token:String,params:util.Map[String,String])
////  object AuthHeader extends AccessTokenFetcher{
////    override def matches(request: HttpRequest): Boolean = ???
////
////    override def fetch(request: HttpRequest): FetchResult = ???
////  }
////  object RequestParameter extends AccessTokenFetcher{
////    override def matches(request: HttpRequest): Boolean = ???
////
////    override def fetch(request: HttpRequest): FetchResult = ???
////  }
//
////  val fetcher=Seq(AuthHeader,RequestParameter)
//
//
//}
//

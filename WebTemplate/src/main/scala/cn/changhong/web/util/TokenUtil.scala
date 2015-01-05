package cn.changhong.web.util

import java.util.UUID

import cn.changhong.web.init.GlobalConfigFactory
import cn.changhong.web.persistent.RedisPoolManager
import com.twitter.finagle.http.Request
import org.slf4j.LoggerFactory

/**
 * Created by yangguo on 14-12-11.
 */
abstract class TokenManager{
  protected def generateToken:String
  def validateToken(clientId:String,uid:String,tType:String,token:String):Boolean
  def createToken(clientId:String,uid:String,tType:String,expired:Int):Map[String,String]
  def validateIsHackAction(requestClientKey:String):Boolean
}
object TokenUtil extends TokenManager{
  override def validateIsHackAction(requestClientKey:String): Boolean = {
    RedisPoolManager.redisCommand{implicit client=>
      val key=requestClientKey
      val count=client.incr(key)
      println(requestClientKey+":"+count)
      if(count>GlobalConfigFactory.max_valid_request_frequency) {
        if(count==GlobalConfigFactory.exceed_spider_threshold_frequency) client.expire(key,GlobalConfigFactory.exceed_spider_threshold_seconds)
        true
      }else {
        if(count == 1) client.expire(key,GlobalConfigFactory.max_valid_request_expire_seconds)
        false
      }
    }
  }
  override protected def generateToken: String = {
    val uuid=UUID.randomUUID().toString
    new sun.misc.BASE64Encoder().encode(uuid.getBytes())
  }
  override def validateToken(clientId: String, uid: String, tType: String, token: String): Boolean = {
    RedisPoolManager.redisCommand{implicit client=>
      val key="t_"+tType+"_"+uid+"_"+clientId
      println("token_key:"+key)
      val uToken=client.get(key)
      if(uToken==null) throw new RestException(RestResponseInlineCode.expired_token,"Token Timeout")
      if(token.equals(uToken)) true
      else throw new RestException(RestResponseInlineCode.invalid_token,"Invalid Token")
    }
  }

  /**
   *
   * @param cid
   * @param uid
   * @param tType
   * @param expired token过期时间 默认为一天 单位second
   * @return
   */
  override def createToken(cid: String, uid: String, tType: String, expired: Int=86400): Map[String,String] = {
    RedisPoolManager.redisCommand { implicit client =>
      val key = "t_" + tType + "_" + uid + "_" + cid
      println("created token_key:"+key)
      val token = generateToken
      val status = client.set(key, token)
      if (status.equals("OK")) {
        client.expire(key, expired)
        Map("access_token" -> token, "expired" -> expired.toString, "tk_type" -> tType)
      } else throw new RestException(RestResponseInlineCode.service_inline_cause, "Create Access Token Failed!")
    }
  }
}

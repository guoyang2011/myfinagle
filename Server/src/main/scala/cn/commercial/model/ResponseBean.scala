package cn.commercial.model

/**
 * Created by yangguo on 14-11-3.
 */
case class ResponseBean(status:Int,msg:Option[String],value:Option[Any],version:Int,service:String)
object ResponseBean{
  val succeed = 0 // 请求成功
  val User_not_exit = -1 // 不匹配任何账户
  val login_user_freeze= -2 //你的账号存在安全问题,已暂时被冻结
  val no_right_call_this_Method= -3 // 没有权限调用此接口
  val no_such_method = -4 //调用接口不存在
  val request_method_not_support= -5 //此方法不支持Http访问，请使用Https访问
  val permission_need = -6 //用户没有授予某权限
  val login_user_banned = -7 // 你的账号已经停止使用
  val Invalid_authorization_parameters = -8 // 验证请求参数失败
  val login_user_suicide = -9 //用户已经注销
  val method_was_closed = -10 //接口被禁用或者服务器故障被临时关闭
  val no_right_call_this_method_deeper = -11 //没有权限调用此接口
  val no_right= -12 // 对方设置了权限，你暂时无法查看此内容
  val upload_file_size_exceed_limit = -13 //上传文件不能大于8M
  val APp_over_invocation_limit = -14 //请不要频繁调用接口，你调用次数超过限制
  val invalid_request_parameters= -15 //解析请求参数失败
  val expired_timestamp= -16 //时间戳过期
  val already_user_nonce = -17 // 随机码不唯一
  val invalid_signature = -18 //无效的签名
  val invalid_token = -19 //access token 无效
  val expired_token= -20 //access token 过期
  val service_execution_timeout = -21 //服务器运行超时,请稍后重试
  val service_inline_cause = -22 //服务器内部错误
}

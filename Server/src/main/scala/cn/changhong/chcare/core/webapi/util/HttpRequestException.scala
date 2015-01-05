package cn.changhong.chcare.core.webapi.util

import cn.changhong.chcare.core.webapi.util.ChCareWebApiRequestErrorType.ChCareWebApiRequestErrorType

object ChCareWebApiRequestErrorType extends Enumeration{
  type ChCareWebApiRequestErrorType=Value
  val CHCAREWEBAPI_TRANSFORM_DATA_ERROR=Value(-100)
  val CHCAREWEBAPI_REQUEST_ERROR=Value(-200)
  val CHCAREWEBAPI_RESPONSE_ERROR=Value(-300)
  val CHCAREWEBAPI_OTHER_ERROR=Value(-500)


}
class HttpRequestException(msg:String,errorType:ChCareWebApiRequestErrorType) extends Exception(msg){

  def getErrorType()=errorType
  def this(msg:String)=this(msg,ChCareWebApiRequestErrorType.CHCAREWEBAPI_OTHER_ERROR)
}


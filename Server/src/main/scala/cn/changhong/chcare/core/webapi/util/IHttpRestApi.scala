package cn.changhong.chcare.core.webapi.util

import java.io.InputStream
import java.io.Closeable

/**
 * Created by yangguo on 14-10-20.
 */
trait IHttpRestApi {
  @throws(classOf[HttpRequestException])
  def get(url:String):String
  @throws(classOf[HttpRequestException])
  def post(url:String,responseBody:String):String
  @throws(classOf[HttpRequestException])
  def delete(url:String,responseBody:String):String
  @throws(classOf[HttpRequestException])
  def put(url:String,responseBody:String):String
  @throws(classOf[HttpRequestException])
  def postFile(url:String,fileStream:InputStream,params:String):String
  @throws(classOf[HttpRequestException])
  def closeStream(stream:Closeable):String
  @throws(classOf[HttpRequestException])
  def getPhotoFile(url:String):Unit


}

package cn.changhong.web.util

import java.nio.charset.Charset

import cn.changhong.web.persistent.Tables.Tables.{FamilyMemberRow, UserRow}
import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import net.liftweb.json.Extraction._
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.util.CharsetUtil

/**
 * Created by yangguo on 14-12-9.
 */
trait Parser[T]{
  def apply(x:String):T
  def unapply(x:String):T=apply(x)
}

object Parser {
  implicit val jsonFormat=DefaultFormats

  implicit object DoubleParser extends Parser[Double]{
    override def apply(x: String): Double = try{
      x.toDouble
    }catch{
      case ex:Throwable=>throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters,x,Double.getClass.getName,ex.getMessage)
    }
  }
  implicit object StringParser extends Parser[String]{
    override def apply(x: String): String = x
  }
  implicit object IntParser extends Parser[Int]{
    override def apply(x: String): Int = try{
      x.toInt
    }catch{
      case ex:Throwable=>throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters,x,Int.getClass.getName,ex.getMessage)
    }
  }
  implicit object LongParser extends Parser[Long]{
    override def apply(x: String): Long = try{
      x.toLong
    }catch{
      case ex:Throwable=>throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters,x,Long.getClass.getName,ex.getMessage)
    }
  }
  implicit object UserParser extends Parser[UserRow]{
    override def apply(json: String): UserRow = {
      try{
        (parse(json).extract[UserRow])
      }catch{
        case ex:Throwable=>throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters,json,UserRow.getClass.getName,ex.getMessage)
      }
    }
  }

  object FamilyMemberParser extends Parser[FamilyMemberRow] {
    override def apply(json: String): FamilyMemberRow = {
      try {
        (parse(json).extract[FamilyMemberRow])
      } catch {
        case ex: Throwable => throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters, json, UserRow.getClass.getName, ex.getMessage)
      }
    }
  }
  implicit object JsonDecoderToMapParser extends Parser[Map[String,String]]{
    override def apply(x: String): Map[String, String] = {
      try{
        parse(x).extract[Map[String,String]]
      }catch{
        case ex:Throwable=>throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters,x,"Map[String,String]",ex.getMessage)
      }
    }
  }
  object StringToChannelBuffer {
    def apply(json: String) :ChannelBuffer= {
      ChannelBuffers.wrappedBuffer(json.getBytes(Charset.forName("utf8")))
    }
  }
  object ObjectToJsonString{
    def apply(obj:Any):String= {
      try {
        compact(render(decompose(obj)))
      } catch {
        case ex:Throwable=> throw new JsonEncoderException(RestResponseInlineCode.service_encoder_response_cause, obj.getClass.getName, ex.getMessage)
      }
    }
  }
  object ChannelBufferToString{
    def apply(content:ChannelBuffer,charset:Charset=CharsetUtil.UTF_8) = {
      if (content == null || content.array().length == 0) throw new RestException(RestResponseInlineCode.invalid_request_parameters, "解析请求参数失败")
      val buffer =
        if (content.hasArray) content.array()
        else {
          val tBuffer = new Array[Byte](content.readableBytes())
          content.readBytes(tBuffer)
          tBuffer
        }
      new String(buffer, charset)
    }
  }
  object JsonStringToMap{
    def apply(json:String) ={
      try{
        parse(json).extract[Map[String,String]]
      }catch{
        case ex:Throwable=>throw new InvalidParameterFormatException(RestResponseInlineCode.invalid_request_parameters,json,"Map[String,String]",ex.getMessage)
      }
    }
  }
  object ObjectToJsonStringToChannelBuffer {
    def apply(obj: Any) = {
      Parser.StringToChannelBuffer(Parser.ObjectToJsonString(obj))
    }
  }
  object ChannelBufferToJsonStringToMap {
    def apply(buffer: ChannelBuffer) :Map[String,String]={
      Parser.JsonStringToMap(Parser.ChannelBufferToString(buffer))
    }
  }
}

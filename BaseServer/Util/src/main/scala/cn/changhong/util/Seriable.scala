package cn.changhong.util


/**
 * Created by yangguo on 14-11-4.
 */
object Seriable {
  def transferStringToMap(params:String):Map[String,Any]={
    parse(params).values.asInstanceOf[Map[String,Any]]
  }
  def transferByteBufToMap(content:ChannelBuffer,charset:java.nio.charset.Charset=CharsetUtil.UTF_8):Map[String,Any]={
    transferStringToMap(transferByteBufToString(content,charset))
  }
  def transferByteBufToString(content:ChannelBuffer,charset:java.nio.charset.Charset=CharsetUtil.UTF_8):String= {
    val buffer =
      if (content.hasArray) content.array()
      else {
        val tBuffer = new Array[Byte](content.readableBytes())
        content.readBytes(tBuffer)
        tBuffer
      }
    new String(buffer, charset)
  }
//  private[this] implicit val format = net.liftweb.json.DefaultFormats
//  def transferObjToNettyByteBuf[T](content:Option[T],charset:java.nio.charset.Charset=CharsetUtil.UTF_8):Option[ChannelBuffer]={
//    content match {
//      case Some(t)=>
//        val jString=compact(render(decompose(t)))
//        Some(ChannelBuffers.copiedBuffer(jString,charset))
//    }
//  }
}

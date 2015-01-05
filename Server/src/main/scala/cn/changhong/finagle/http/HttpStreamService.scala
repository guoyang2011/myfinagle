//package cn.changhong.finagle.http
//
//import java.io.{InputStream, File, FileInputStream}
//import java.net.URI
//import java.nio.channels.FileChannel
//
//import com.twitter.concurrent.{Offer, Broker}
//import com.twitter.finagle.Service
//import com.twitter.finagle.stream.StreamResponse
//import com.twitter.util.{Timer, Future}
//import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
//import org.jboss.netty.handler.codec.http._
//import com.twitter.conversions.time._
//import org.jboss.netty.util.CharsetUtil
//
//import java.nio.channels._
//import scala.collection._
//import scala.util.Random
//
///**
// * Created by yangguo on 14-10-24.
// */
//object HttpStreamService {
//  def main(args:Array[String]): Unit ={
//    println("go...")
//  }
//  class Contents(name:String){
//    private[this] val addBroker=new Broker[Broker[ChannelBuffer]]
//    private[this] val remBroker=new Broker[Broker[ChannelBuffer]]
//    private[this] val messages =new Broker[ChannelBuffer]
//    def sendMessage(msg:String)= messages.send(ChannelBuffers.copiedBuffer("data: "+msg+"\n\n",CharsetUtil.UTF_8))
//    def subscribe(subscriber:Broker[ChannelBuffer]):Future[Unit]=addBroker ! subscriber
//    def exit(subscriber:Broker[ChannelBuffer]):Future[Unit]=remBroker ! subscriber
//    private[this] def tee(receivers:Set[Broker[ChannelBuffer]]): Unit ={
//      Offer.select(
//        addBroker.recv{b=>tee(receivers+b)},
//        remBroker.recv{b=>
//          val rcv=receivers-b
//          if(rcv.size == 0) Contents.remove(name)
//          tee(rcv)
//        },
//        if(receivers.isEmpty) Offer.never else{
//          messages.recv{m=>Future.join(receivers map {_ ! m} toSeq) ensure tee(receivers)}
//        }
//      )
//      println("number of receivers:"+receivers.size)
//    }
//    tee(immutable.Set())
//    def test(r:Random,t:Timer): Unit ={
//      t.schedule(1.second.fromNow){
//        sendMessage(r.nextInt.toString) andThen test(r,t)
//      }
//    }
//  }
//  object Contents{
//    private[this] val contentsMap= new mutable.HashMap[Symbol,Contents] with mutable.SynchronizedMap[Symbol,Contents]
//    def get(contentName:String):Option[Contents]=contentsMap.get(Symbol(contentName))
//    def remove(contentName:String): Unit ={
//      contentsMap-=Symbol(contentName)
//    }
//    def add(contentsName:String) : Contents={
//      val nc=new Contents(contentsName)
//      contentsMap += Symbol(contentsName) -> nc
//      nc
//    }
//  }
//  private def uriToContentsName(uri: URI) = uri.getPath.substring(1)
//  val subscribe = new Service[HttpRequest, StreamResponse] {
//
//    def apply(request: HttpRequest) = Future {
//
//      val subscriber = new Broker[ChannelBuffer]
//      val uri = URI.create(request.getUri)
//      val contentsName = uriToContentsName(uri)
//      val contents = Contents.get(contentsName).getOrElse(Contents.add(contentsName))
//      contents.subscribe(subscriber)
//      new StreamResponse {
//
//        val httpResponse = new DefaultHttpResponse(
//          request.getProtocolVersion, HttpResponseStatus.OK)
//        httpResponse.setHeader("Content-type", "text/event-stream")
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*")
//        httpResponse.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
//        httpResponse.setHeader("Expires", "Fri, 01 Jan 1990 00:00:00 GMT")
//        httpResponse.setHeader("Pragma", "no-cache")
//
//        def messages = subscriber.recv
//        def error = new Broker[Throwable].recv
//        def release() = {
//          contents.exit(subscriber)
//          // sink any existing messages, so they
//          // don't hold up the upstream.
//          subscriber.recv foreach { _ => () }
//        }
//      }
//    }
//  }
//  val pages=new Service[HttpRequest,HttpResponse] {
//    override def apply(request: HttpRequest): Future[HttpResponse] = {
//      try{
//        val uri=URI.create(request.getUri)
//        val f= new File("resources")
//        if(f.isFile && !f.getAbsolutePath.contains("..")){
//          val response=new DefaultHttpResponse(request.getProtocolVersion,HttpResponseStatus.OK)
//          response.setHeader("Content-Length",f.length())
//          response.setHeader("Content-type","text/plain")
//
//          Future.value(response)
//        }else{
//          val response = new DefaultHttpResponse(request.getProtocolVersion, HttpResponseStatus.NOT_FOUND)
//          response.setContent(ChannelBuffers.copiedBuffer("contents " + f.getAbsolutePath + " not found.", CharsetUtil.UTF_8))
//          Future.value(response)
//        }
//      }catch{
//        case e=>{
//          val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR)
//          e.printStackTrace()
//          response.setContent(ChannelBuffers.copiedBuffer(e.getMessage(), CharsetUtil.UTF_8))
//          Future.value(response)
//        }
//      }
//    }
//  }
//
//  val outBuffer={
//    val f=new File("")
//    val fileInChannel=new FileInputStream(f).getChannel
//    ChannelBuffers.wrappedBuffer(fileInChannel.map(FileChannel.MapMode.READ_ONLY,0,f.length()))
//  }
//
//}

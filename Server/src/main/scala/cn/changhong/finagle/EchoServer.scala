//package cn.changhong.finagle
//
//
//import java.net.{ InetSocketAddress}
//
//import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder, Server}
//import com.twitter.finagle._
//import com.twitter.util.{ Await, Future}
//
//
//import io.netty.util.CharsetUtil
//import org.jboss.netty.buffer.ChannelBuffers
//import org.jboss.netty.channel.{ChannelPipeline, Channels, ChannelPipelineFactory}
//import org.jboss.netty.handler.codec.frame.{Delimiters, DelimiterBasedFrameDecoder}
//import org.jboss.netty.handler.codec.http._
//import org.jboss.netty.handler.codec.string.{StringDecoder,StringEncoder}
//
//import akka.actor._
///**
// * Created by yangguo on 14-10-21.
// */
//object EchoServer extends App{
//  def serverStringInit={
//    val service=new Service[String,String] {
//      override def apply(request: String): Future[String] = {
//        println(request)
//        Future.value(request)
//      }
//    }
//    val server:Server=ServerBuilder()
//      .codec(StringCodec)
//      .bindTo(new InetSocketAddress(8888))
//      .name("echoserver")
//      .build(service)
//  }
////  serverStringInit
////  filterChannelService
//  serverStringInit
//  println("hhhh")
//  clientStringInit
//  def clientStringInit={
//    println("clientStringInit...")
//    val client:Service[String,String]=ClientBuilder().codec(StringCodec).hosts(new InetSocketAddress(8888)).hostConnectionLimit(1).build()
//    client("hi momn") onSuccess{result=>
//      println("Received Result asynchronously:"+result)
//    } onFailure { error =>
//      error.printStackTrace()
//    } ensure{
//      client.close()
//    }
//  }
//  def serverHttpInit= {
//    val service: Service[HttpRequest, HttpResponse] = new Service[HttpRequest, HttpResponse] {
//      override def apply(request: HttpRequest): Future[HttpResponse] = {
//        println(request.getUri + "," + request.getMethod + "," + request.toString)
//        Future.value(new DefaultHttpResponse(request.getProtocolVersion, HttpResponseStatus.OK))
//        val response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)
//        val params = new QueryStringDecoder(request.getUri).getParameters
//        val it = params.entrySet().iterator()
//        while (it.hasNext) {
//          val entry = it.next()
//          println(entry.getKey + "," + entry.getValue)
//        }
//        val content = "hello finagle"
//        response.setContent(ChannelBuffers.copiedBuffer(content + "\n" + params.toString, CharsetUtil.UTF_8))
//        Future.value(response)
//      }
//    }
//    val server: ListeningServer = Http.serve(":8080", service)
//    Await.ready(server)
//  }
//  def filterChannelService={
//    val myService:Service[HttpRequest,HttpResponse]=HttpServer.HandlerExceptions andThen HttpServer.Authorize andThen HttpServer.Respond
//    val server:Server=ServerBuilder()
//      .codec(http.Http())
//      .bindTo(new InetSocketAddress(19999))
//      .name("filterChannelServer")
//      .build(myService)
//
//  }
//
//}
//case class MakeRequest(id:Int,message:String)
//case class Finished(id:Int)
//case object Start
//case object Stop
//
//
//class TestMediator(var testBot:ActorRef=null,val server:Server) extends Actor{
//  override def receive: Receive = {
//    case Start=> testBot ! MakeRequest(0,"hello")
//    case Finished(0)=>testBot ! MakeRequest(1,"This is request #1")
//    case Finished(1)=> {
//      testBot ! Stop
//      server.close()
//    }
//
//  }
//}
//class TestBot(client:Service[HttpRequest,HttpResponse]) extends Actor{
//  override def receive: Actor.Receive = {
//    case MakeRequest(id,message)=>
//      val request=createConnect("http://localhost:8888/login?id="+id+"&message="+message,"")
//      val response=client(request)
//      response onSuccess{resp=>
//        println("GET Success:" + resp)
//      } onFailure{ error =>
//        println("GET Failure:"+error.getStackTrace)
//      }
//  }
//  def createConnect(url:String,context:String):DefaultHttpRequest={
//    val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,url)
//    request
//  }
//}
//object StringCodec extends StringCodec
//class StringCodec extends CodecFactory[String,String]{
//
//  override def client: Client = Function.const(new Codec[String,String](){
//    override def pipelineFactory: ChannelPipelineFactory = new ChannelPipelineFactory {
//      override def getPipeline: ChannelPipeline = {
//        val pipeline=Channels.pipeline()
//        pipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8))
//        pipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
//        pipeline
//      }
//    }
//  })
//
//
//  override def server: Server = Function.const{
//    new Codec[String,String] {
//      override def pipelineFactory: ChannelPipelineFactory = new ChannelPipelineFactory {
//        override def getPipeline: ChannelPipeline = {
//          val pipeline = Channels.pipeline()
//          pipeline.addLast("line", new DelimiterBasedFrameDecoder(100, Delimiters.lineDelimiter(): _*))
//          pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
//          pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
//          pipeline
//        }
//      }
//    }
//  }
//}
//
//
//object HttpServer{
//  class HandlerExceptions extends SimpleFilter[HttpRequest,HttpResponse]{
//    override def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]): Future[HttpResponse] = {
//      println("HandlerException...")
//      service(request) handle{
//        case error=>
//          val statusCode=error match{
//            case _:IllegalArgumentException=>HttpResponseStatus.FORBIDDEN
//            case _=>HttpResponseStatus.INTERNAL_SERVER_ERROR
//          }
//          val errorResponse=new DefaultHttpResponse(HttpVersion.HTTP_1_1,statusCode)
//         errorResponse.setContent(ChannelBuffers.copiedBuffer(error.getStackTraceString,CharsetUtil.UTF_8))
//          errorResponse
//      }
//    }
//  }
//  class Authorize extends SimpleFilter[HttpRequest, HttpResponse] {
//    def apply(request: HttpRequest, continue: Service[HttpRequest, HttpResponse]) = {
//      println("Authorize...")
//      if ("open sesame" != request.headers().get(HttpHeaders.Names.AUTHORIZATION)) {
//        continue(request)
//      } else {
//        Future.exception(new IllegalArgumentException("You don't know the secret"))
//      }
//    }
//  }
//  class Respond extends Service[HttpRequest,HttpResponse]{
//    override def apply(request: HttpRequest): Future[HttpResponse] = {
//      println("Respond...")
//      val response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
//      response.setContent(ChannelBuffers.copiedBuffer("Hello world",CharsetUtil.UTF_8))
//      Future.value(response)
//    }
//  }
//  object HandlerExceptions extends HandlerExceptions
//  object Authorize extends Authorize
//  object Respond extends Respond
//
//}
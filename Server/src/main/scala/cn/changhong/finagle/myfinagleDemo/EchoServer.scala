package cn.changhong.finagle.myfinagleDemo

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import com.twitter.finagle.builder.{ServerBuilder, Server}
import com.twitter.finagle.service.TimeoutFilter
import com.twitter.finagle._
import com.twitter.finagle.stats.{InMemoryStatsReceiver, StatsReceiver}
import com.twitter.finagle.util.DefaultTimer
import com.twitter.util.{Duration, Future}
import io.netty.util.CharsetUtil
import org.jboss.netty.channel._
import org.jboss.netty.handler.codec.frame.{Delimiters, DelimiterBasedFrameDecoder}
import org.jboss.netty.handler.codec.string.{StringEncoder, StringDecoder}

/**
 * Created by yangguo on 14-10-22.
 */
object EchoServer {
  private[this] object SimpleDefaultPipelineCodec extends CodecFactory[String,String]{
    override def client: Client = Function.const(new Codec[String,String](){
      override def pipelineFactory: ChannelPipelineFactory = new ChannelPipelineFactory {
        override def getPipeline: ChannelPipeline = {
          val pipeline=Channels.pipeline()
          pipeline.addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8))
          pipeline.addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8))
          pipeline.addLast("simplePipeline",new SimpleStringPipeLine)
          pipeline
        }
      }
    })

    override def server: Server = Function.const {
      new Codec[String, String] {
        override def pipelineFactory: ChannelPipelineFactory = new ChannelPipelineFactory {
          override def getPipeline: ChannelPipeline = {
            val pipeline = Channels.pipeline()
            pipeline.addLast("line", new DelimiterBasedFrameDecoder(128, Delimiters.lineDelimiter(): _*))
            pipeline.addLast("StringEncoder",new StringEncoder(CharsetUtil.UTF_8))
            pipeline.addLast("StringDecoder",new StringDecoder(CharsetUtil.UTF_8))
            pipeline.addLast("simplePipeline",new SimpleStringPipeLine)
            pipeline
          }
        }
      }
    }
    private[this] class SimpleStringPipeLine extends SimpleChannelHandler{
      override def handleUpstream(ctx: ChannelHandlerContext, e: ChannelEvent): Unit = {
        println("SimpleStringPipeLine Upstream Pipeline run..."+e.getChannel.getRemoteAddress.toString)
        super.handleUpstream(ctx,e)
      }
      override def handleDownstream(ctx: ChannelHandlerContext, e: ChannelEvent): Unit = {
        println("SimpleStringPipeLine Downstream Pipeline run..."+e.getChannel.getRemoteAddress.toString)

        super.handleDownstream(ctx, e)
      }

      override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
        val channel=ctx.getChannel
        if(channel.isWritable){
          channel.write("God bless U,cause:"+e.getCause.getStackTraceString)
        }
      }
    }
  }
  private[this] object SimpleService {
    private[this] object SimpleStringServiceFilter extends SimpleFilter[String, String] {
      override def apply(request: String, service: Service[String, String]): Future[String] = {
        println("SimpleStringServiceFilter Running...Request:" + request)
        service(request).within(DefaultTimer.twitter,Duration(2,TimeUnit.SECONDS))

      }
    }
    private[this] object SimpleStringDefaultExceptionHandlerService extends SimpleFilter[String,String]{
      override def apply(request: String, service: Service[String, String]): Future[String] = {
        service(request).handle(defaultHandler)
      }
      private[this] val defaultHandler:PartialFunction[Throwable,String]={
        case e:Exception=>e.getStackTraceString+"!BingGoo...."
      }
    }

    private[this] object SimpleStringService extends Service[String, String] {
      override def apply(request: String): Future[String] = {
        println("SimpleStringService Running...Request:" + request)
        try {
          Thread.sleep(20000)
        }catch{
          case e:InterruptedException=>println(e.getLocalizedMessage)
        }
        Future.exception(new Exception("ohh..."))
      }
    }
   def apply(): Service[String, String] = SimpleStringDefaultExceptionHandlerService andThen SimpleStringServiceFilter andThen SimpleStringService
  }
  def main (args: Array[String]) {
    println("Start Service...")
    val server:Server=ServerBuilder()
      .codec(SimpleDefaultPipelineCodec)
      .readTimeout(Duration(3,TimeUnit.SECONDS))
      .requestTimeout(Duration(4,TimeUnit.SECONDS))
      .writeCompletionTimeout(Duration(4,TimeUnit.SECONDS))
      .reportTo(new InMemoryStatsReceiver)
      .bindTo(new InetSocketAddress(11002))
      .name("SimpleService").build(SimpleService())



  }
}

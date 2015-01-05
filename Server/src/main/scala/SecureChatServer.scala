

import java.net.{HttpURLConnection, InetAddress}

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.group.DefaultChannelGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ChannelHandlerContext, Channel , SimpleChannelInboundHandler, ChannelInitializer}
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.string.{StringEncoder, StringDecoder}
import io.netty.handler.codec.{Delimiters, DelimiterBasedFrameDecoder}
import io.netty.handler.ssl.util.SelfSignedCertificate
import io.netty.handler.ssl.{SslHandler, SslContext}
import io.netty.util.CharsetUtil
import io.netty.util.concurrent.{Future, GenericFutureListener, GlobalEventExecutor}

//import io.netty.util.concurrent.DefaultEventExecutorGroup

class SecureChatServerInitializer(val sslCtx:SslContext) extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch:SocketChannel):Unit={
    val pl=ch.pipeline()
//    pl.addLast(sslCtx.newHandler(ch.alloc()))
//    pl.addLast(new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter():_*))
    pl.addLast(new StringDecoder(CharsetUtil.UTF_8))
    pl.addLast(new StringEncoder(CharsetUtil.UTF_8))
    pl.addLast(new SecureChatServerHandler())
  }

}
/**
* Created by yangguo on 14-10-20.
*/
object SecureChatServer extends App{
  val ssc=new SelfSignedCertificate()
  val sslCtx=SslContext.newServerContext(ssc.certificate(),ssc.privateKey())

  val serverGroup=new NioEventLoopGroup()
  val workerGroup=new NioEventLoopGroup()
  try{
    val boot=new ServerBootstrap()
    boot.group(serverGroup,workerGroup)
      .channel(classOf[NioServerSocketChannel])
      .childHandler(new SecureChatServerHandler())
    boot.bind(8888).sync().channel().closeFuture().sync()

  }finally {
    serverGroup.shutdownGracefully()
    workerGroup.shutdownGracefully()
  }
}
@Sharable
class SecureChatServerHandler extends SimpleChannelInboundHandler[String]{
  val channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)
  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    broadcastMsgToAllOnlineChannel(msg,ctx.channel())
  }
  private def broadcastMsgToAllOnlineChannel(msg:String,sendChannel:Channel): Unit ={
    val it=channels.iterator()
    var channel:Channel=null
    while(it.hasNext){
      channel=it.next()

      if(channel.isOpen&&channel.isActive&&channel.isWritable&&channel!=sendChannel) channel.writeAndFlush(msg)
    }
  }
  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    ctx.close()


  }

  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    ctx.pipeline().get(classOf[SslHandler]).handshakeFuture().addListener(new GenericFutureListener[Future[Channel]] {
      override def operationComplete(future: Future[Channel]): Unit = {
        ctx.writeAndFlush("Welcome to "+InetAddress.getLocalHost.getHostName+" Secure Chat Server\n")
        ctx.writeAndFlush("You Session Is Protected by "+ctx.pipeline().get(classOf[SslHandler]).engine().getSession.getCipherSuite+" Cipher Suite\n")
        val msg="Welcome ["+ctx.channel().remoteAddress().toString+"] Enter Secure Chat Server"
        broadcastMsgToAllOnlineChannel(msg,ctx.channel())
      }

    })
  }
}
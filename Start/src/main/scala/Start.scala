import java.io.{InputStreamReader, BufferedReader}
import java.net.InetSocketAddress

import io.netty.buffer.{Unpooled, ByteBuf}
import io.netty.channel.socket.SocketChannel
import io.netty.channel.{ChannelInitializer, SimpleChannelInboundHandler, ChannelHandlerContext, ChannelHandlerAdapter}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.bootstrap.Bootstrap
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.{StringEncoder, StringDecoder}
import io.netty.handler.codec.{Delimiters, DelimiterBasedFrameDecoder}
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import io.netty.util.CharsetUtil

class DefaultChannelHandler extends SimpleChannelInboundHandler[ByteBuf]{
//  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = super.exceptionCaught(ctx, cause)

  override def channelRead0(ctx: ChannelHandlerContext, msg: ByteBuf): Unit = {
    val buffer=msg.copy()
    while(buffer.isReadable) print(buffer.readableBytes().asInstanceOf[Char])
    println()
  }

  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Echo Server!"+ctx.channel().remoteAddress().toString,CharsetUtil.UTF_8))
    super.channelActive(ctx)
  }

  override def channelInactive(ctx: ChannelHandlerContext): Unit = {
    super.channelInactive(ctx)
  }
}

class SecureChatInitializer(val sslCtx:SslContext) extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch: SocketChannel): Unit = {
    val pl=ch.pipeline()
    pl.addLast(sslCtx.newHandler(ch.alloc(),"localhost",8888))
    pl.addLast(new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter():_*))
    pl.addLast(new StringDecoder(CharsetUtil.UTF_8))
    pl.addLast(new StringEncoder(CharsetUtil.UTF_8))
    pl.addLast(new DefaultChannelHandler)

  }
}
case class Person(username:String,password:String,age:Int)
/**
 * Created by yangguo on 14-10-20.
 */
object Start extends App {
  def calculate(n:BigInt):BigInt={
    if(n.equals(0) || n.equals(1)) n
    else (calculate(n-1)+calculate(n-2))


  }
  println(calculate(50))

//  val sslCtx=SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE)
//  val group = new NioEventLoopGroup()
//  try{
//    val bootStrap = new Bootstrap()
//    bootStrap.remoteAddress(new InetSocketAddress("localhost", 8888))
//    bootStrap.handler(new DefaultChannelHandler())
//    bootStrap.group(group)
//    bootStrap.channel(classOf[NioSocketChannel])
//    val channel = bootStrap.connect().sync().channel()
//    val bufferReader = new BufferedReader(new InputStreamReader(System.in))
//    var br: String = null
//    while (true) {
//      br = bufferReader.readLine()
//      println(br)
//      if ("bye".equals(br.trim.toLowerCase)) channel.closeFuture()
//      else {
//        channel.writeAndFlush(br)
//      }
//    }
//  }
//  finally {
////    group.shutdownGracefully()
//  }

}



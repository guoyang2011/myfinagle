package cn.changhong.finagle.fibonacci


import com.twitter.finagle.Service
import com.twitter.finagle.client.{Bridge, DefaultClient}
import com.twitter.finagle.dispatch.{SerialServerDispatcher, SerialClientDispatcher}
import com.twitter.finagle.netty3.{Netty3Transporter, Netty3Listener}
import com.twitter.finagle.server.DefaultServer
import com.twitter.util.{Future, Await}
import io.netty.util.CharsetUtil
import org.jboss.netty.channel._
import org.jboss.netty.handler.codec.frame.{Delimiters, DelimiterBasedFrameDecoder}
import org.jboss.netty.handler.codec.string.{StringDecoder, StringEncoder}

/**
 * Created by yangguo on 14-10-22.
 */

object FibonacciLauncher{
  def main(args:Array[String]):Unit={
    println("what happen??")
    new Thread(new Runnable {
      override def run(): Unit = FibonacciServerLauncher.main(null)
    }).start()

    Thread.sleep(3000)
    FibonacciClientLauncher.main(null)
    println("end program...")
  }
}
object StringServerPipeline extends ChannelPipelineFactory{
  override def getPipeline: ChannelPipeline = {
    val pipeline=Channels.pipeline()
    pipeline.addLast("line",new DelimiterBasedFrameDecoder(100,Delimiters.lineDelimiter():_*))
    pipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
    pipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8))
    pipeline
  }
}
object StringClientPipeline extends ChannelPipelineFactory{
  override def getPipeline: ChannelPipeline = {
    val pipeline=Channels.pipeline()
    pipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8))
    pipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8))
    pipeline
  }
}
object FibonacciClientLauncher{
  def main(args:Array[String]): Unit = {
    val arg = Array(Seq(10005, 20))
    val it = arg.iterator
    while (it.hasNext) {
      val v=it.next()
      println("cleint start..."+v)
      v match {
        case Seq(port, req) =>
          val client = FibonacciClient.newService("localhost:" + port)
          val rep = Await.result(client(req.toString))
          println("Fibonacci(%s) is %s", req, rep)
        case _ => println("Bad arguments!")
      }
    }
  }
}

object FibonacciServerLauncher{
  def main (args: Array[String]):Unit= {
    val arg = Array(Seq("left", 8999), Seq("left", 10000), Seq("node", 10001, 8999, 10000), Seq("left", 10002), Seq("left", 10003), Seq("node", 10004, 10003, 10002), Seq("node", 10005, 10004, 10001))
    val it=arg.iterator
    println("server /.")
    while(it.hasNext) {
      val v=it.next()
      v match {
        case Seq("left", port) =>
          println(port)
          new Thread(new Runnable {
            override def run(): Unit = {

              val service = new FibonaccisService(LocalFibonacciCalculator)
              Await.ready(FibonacciServer.serve(":" + port.toString, service))
            }
          }).start()

        case Seq("node", port, left, right) =>
          println(port+""+left+""+right)
          new Thread(new Runnable {
            override def run(): Unit = {
              val lt = new RemoteFibonacciCalculator(FibonacciClient.newService("localhost:" + left))
              val rt = new RemoteFibonacciCalculator(FibonacciClient.newService("localhost:" + right))
              val service = new FibonaccisService(new FanoutFibonacciCalculator(lt, rt))
              Await.ready(FibonacciServer.serve(":" + port.toString, service))
            }
          }).start()
      }
    }
  }
}

object FibonacciServer extends DefaultServer[String,String,String,String]("fibonacci-server",FibonacciListener,new SerialServerDispatcher(_,_))


object FibonacciListener extends Netty3Listener[String,String]("fibonacci-listener",StringServerPipeline)
object StringClientTransporter extends Netty3Transporter[String,String]("string-client-transporter",StringClientPipeline)

class RemoteFibonacciCalculator(remote:Service[String,String]) extends FibonacciCalculator{
  override def calculator(n: BigInt): Future[BigInt] = remote.apply(n.toString()) map{BigInt(_)}
}
class FanoutFibonacciCalculator(left:FibonacciCalculator,right:FibonacciCalculator) extends FibonacciCalculator{
  override def calculator(n: BigInt): Future[BigInt] = {
    println("start calculator..."+n)
    if(n == 0 || n == 1) Future.value(n)
    else {
      val seq = Seq(left.calculator(n - 1), right.calculator(n - 2))
      Future.collect(seq) map {_.sum}
    }
  }
}
object FibonacciClient extends DefaultClient[String,String]("fibonacci-client",{
  val bridge=Bridge[String,String,String,String](StringClientTransporter,new SerialClientDispatcher(_))
  (addr,stats)=>bridge(addr,stats)
})
object LocalFibonacciCalculator extends FibonacciCalculator{
  override def calculator(n: BigInt): Future[BigInt] = {
    println("local calculator start[%s]",n)
    if(n.equals(0) || n.equals(1)) Future.value(n)
    else for{
      a <- calculator(n-1)
      b <- calculator(n-2)
    } yield (a+b)
  }
}
trait FibonacciCalculator{
  def calculator(n:BigInt):Future[BigInt]

}
class FibonaccisService(calculator:FibonacciCalculator) extends Service[String,String]{
  override def apply(request: String): Future[String] = {
    println("service for request:"+request)
    calculator.calculator(BigInt(request)) map (_.toString())
  }
}
class DelimEncoder(delim:Char) extends SimpleChannelHandler{
  override def writeRequested(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    val newMessage=e.getMessage match{
      case m:String=>m+delim
      case m=>m
    }
    Channels.write(ctx,e.getFuture,newMessage,e.getRemoteAddress)
  }
}
class Test { outer=>
  private[this] def print=println("ha")
}
object Test{

}


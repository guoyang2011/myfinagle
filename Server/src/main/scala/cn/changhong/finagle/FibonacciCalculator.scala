//package cn.changhong.finagle
//
//import com.twitter.finagle.client.{Bridge, DefaultClient}
//import com.twitter.finagle.dispatch.{SerialClientDispatcher, SerialServerDispatcher}
//import com.twitter.finagle.netty3.{Netty3Transporter, Netty3Listener}
//import com.twitter.finagle.server.DefaultServer
//import com.twitter.finagle.{ Service}
//import com.twitter.util.{ Await, Future}
//import org.jboss.netty.channel._
//import org.jboss.netty.handler.codec.frame.{DelimiterBasedFrameDecoder, Delimiters}
//import org.jboss.netty.handler.codec.string.{StringEncoder, StringDecoder}
//import org.jboss.netty.util.CharsetUtil
//
///**
// * Created by yangguo on 14-10-21.
// */
//object FibonacciCalculatorLauncher {
//  def main(args:Array[String])= args match{
//    case Seq("left",port)=>
//      val service:Service[String,String]=new FibonacciService(LocalFibonacciCalculactor)
//      val server=FibonacciServer.serve(":"+port,service)
//      Await.ready(server)
//
//  }
//
//}
//object FibonacciClientLauncher{
//  def main(args:Array[String])=args match{
//    case Seq(port,req)=>
//      val client = FibonacciClient.newService("localhost:" + port)
//      val rep = Await.result(client(req.toString))
//      printf("Fibonacci(%s) is %s\n", req, rep)
//    case _=>println("Bad arguments!")
//  }
//}
////trait FibonacciCalculatorJob{
////  val Zero=BigInt(0)
////  val One=BigInt(1)
////  val Two=BigInt(2)
////  def calculateTask(n:BigInt):Future[BigInt]
////}
////object LocalFibonacciCalculactor extends FibonacciCalculatorJob{
////  override def calculateTask(n: BigInt): Future[BigInt] = {
////    if(n.equals(Zero) || n.equals(One)) Future.value(n)
////    else for {
////      a <- calculateTask(n - One)
////      b <- calculateTask(n-Two) } yield (a+b)
////  }
////}
//
//object StringServerPipeline extends ChannelPipelineFactory{
//  override def getPipeline = {
//    val pipeline=Channels.pipeline()
//    pipeline.addLast("line", new DelimiterBasedFrameDecoder(100, Delimiters.lineDelimiter: _*))
//    pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
//    pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
//    pipeline
//  }
//}
//object StringClientTransporter extends Netty3Transporter[String,String]("string-client-transporter",StringClientPipeline)
//object FibonacciClient extends DefaultClient[String,String](
//  name="fibonacci-client",
//  endpointer = {
//    val bridge=Bridge[String,String,String,String](StringClientTransporter,new SerialClientDispatcher(_))
//    (addr,stats)=>bridge(addr,stats)
//  }
//
//)
//object StringClientPipeline extends ChannelPipelineFactory{
//  override def getPipeline: ChannelPipeline = {
//    val pipeline=Channels.pipeline()
//    pipeline.addLast("stringDecoder",new StringEncoder(CharsetUtil.UTF_8))
//    pipeline.addLast("stringEncoder",new StringDecoder(CharsetUtil.UTF_8))
//    pipeline
//  }
//}
//class DelimEncoder(delim:Char) extends SimpleChannelHandler{
//  override def writeRequested(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
//    val newMessage=e.getMessage match{
//      case m:String=>m+delim
//      case m=>m
//    }
//    Channels.write(ctx,e.getFuture,newMessage,e.getRemoteAddress)
//  }
//}
//
//object FibonacciListener extends Netty3Listener[String,String]("fibonacci-listener",StringServerPipeline)
//object FibonacciServer extends DefaultServer[String,String,String,String]("fibonacci-server",FibonacciListener,new SerialServerDispatcher(_,_))
//
//
//class FanoutFibonacciCalculator(left:FibonacciCalculatorJob,right:FibonacciCalculatorJob) extends FibonacciCalculatorJob{
//  override def calculateTask(n: BigInt): Future[BigInt] = {
//    if(n.equals(One) || n.equals(Zero)) Future.value(n)
//    else{
//      val seq: Seq[Future[BigInt]] =Seq(left.calculateTask(n-One),right.calculateTask(n-Two))
//      Future.collect(seq) map {_.sum}
//    }
//  }
//}
//class RemoteFibonacciCalculator(remote:Service[String,String]) extends  FibonacciCalculatorJob{
//  override def calculateTask(n: BigInt): Future[BigInt] =
//  remote(n.toString) map {BigInt(_)}
//}
//class FibonacciService(calculator:FibonacciCalculatorJob) extends Service[String,String]{
//  override def apply(request: String): Future[String] = calculator.calculateTask(BigInt(request)) map {_.toString()}
//}
//

package fabonacci

import java.net.InetSocketAddress

import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.http.Http
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._

import scala.collection.mutable.Map
/**
 * Created by Administrator on 2014/12/27.
 */
object FibonacciCalculator {
  val map:Map[Int,BigDecimal]=Map()
   def run(index:Int): BigDecimal ={
     index match{
       case 0 => 0
       case  1 | 2 => 1
       case in if in > 0 =>
         map.get(in) match{
           case Some(v)=> v
           case None=>
             val v=run(index-1)+run(index-2)
             map+=(index->v)
             v
         }
       case _=>throw new RuntimeException("输入参数不合法")
     }
   }
  def main(args:Array[String]): Unit = {
    //    doLauncher(serverStart)
    //    Thread.sleep(2000)
    //    doLauncher(lazyClient)
//    val step = 300
//    (1 to 70000) foreach { in =>
//      val index = in * step
//      println(index + "->" + run(index))
//      Thread.sleep(300)
//    }


  }
  def doLauncher(fb: =>Unit): Unit ={
    new Thread(new Runnable {
      override def run(): Unit = fb
    }).start()
  }
  def lazyClient: Unit ={
    val step=100
    val client=ClientBuilder()
      .codec(Http())
      .hostConnectionLimit(1)
      .hosts(new InetSocketAddress("localhost",10000))
      .build()
    (1 to 70000)foreach{in=>
      val index=in*step
      val request=new DefaultHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.GET,"/")
      request.setUri("/?count="+index)
      client(request) onSuccess{response=>
        println(index+"->"+new String(response.getContent.array()))
      } onFailure{ex=>
        ex.printStackTrace()
      }
      Thread.sleep(200)
    }
  }
  def serverStart: Unit ={




    ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress("localhost",10000))
      .name("fibonacciCalculatorServer")
      .build(new Service[HttpRequest,HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = {
        Future.value{
          val decoder=new QueryStringDecoder(request.getUri)
          val index=decoder.getParameters.get("count")
          val v=if(index != null&&index.size()>0) {
            try {
              run(index.get(0).toInt)
            } catch {
              case ex: Throwable =>
                ex.printStackTrace()
                -2
            }
          }else -1
          val response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
          response.setContent(ChannelBuffers.wrappedBuffer(v.toString.getBytes()))
          response
        }
      }
    })
  }
}

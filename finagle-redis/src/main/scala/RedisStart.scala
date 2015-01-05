import com.twitter.finagle.redis.Client
import com.twitter.finagle.redis.util.{RedisCluster, StringToChannelBuffer}
import com.twitter.finagle.{RedisClient, Redis}
import com.twitter.util.Await

/**
 * Created by yangguo on 14-12-14.
 */
object RedisStart {
  def main(args:Array[String]): Unit ={
    command()
  }
  def printR[T](f: =>T){

  }
  def command(): Unit ={
    val clients=RedisCluster.start(1)
    clients
    val client=Client(RedisCluster.hostAddresses())
    val rc=RedisClient.newRichClient("localhost:6379")
    rc.set(StringToChannelBuffer("hello_redis_key"),StringToChannelBuffer("hello_redis_value"))
    rc.get(StringToChannelBuffer("hello_redis_key")) onSuccess{v=>
      v match{
        case Some(vl)=>println(new String(vl.array()))
        case None=>println("noFind")
      }
    }
//    Thread.sleep(1000)
  }
}

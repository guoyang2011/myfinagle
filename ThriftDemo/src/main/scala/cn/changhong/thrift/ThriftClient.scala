package cn.changhong.thrift

import cn.changhong.core.{IndexNewsOperatorServices, IndexNewsOperatorServices$FinagleClient}
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.thrift.ThriftClientFramedCodec

/**
 * Created by yangguo on 15-1-5.
 */
object ThriftClient {
  def apply(dest:String): IndexNewsOperatorServices.FinagledClient ={
    val serviceCodec=ThriftClientFramedCodec()
    val service=ClientBuilder()
      .dest(dest)
      .codec(serviceCodec)
      .hostConnectionLimit(3)
      .build()
    val client=new IndexNewsOperatorServices.FinagledClient(service)
    client
  }
}

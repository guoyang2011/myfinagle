package cn.changhong

import java.net.InetSocketAddress

import cn.changhong.core.thrift.AccountService
import cn.changhong.core.thrift.impl.AccountServiceImpl
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.thrift.ThriftServerFramedCodec
import org.apache.thrift.protocol.TBinaryProtocol

/**
 * Created by Administrator on 2014/12/29.
 */
object ThriftServer {
  def main(args:Array[String]): Unit ={
    val service=new AccountService.FinagledService(new AccountServiceImpl,new TBinaryProtocol.Factory())
    ServerBuilder()
      .bindTo(new InetSocketAddress(10000))
      .codec(ThriftServerFramedCodec())
      .name("sbt")
      .build(service)
  }
}

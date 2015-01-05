/**
 * Generated by Scrooge
 *   version: 3.14.1
 *   rev: a996c1128a032845c508102d62e65fc0aa7a5f41
 *   built at: 20140501-114733
 */
package cn.changhong.core.thrift

import com.twitter.finagle.{SourcedException, Service => FinagleService}
import com.twitter.finagle.stats.{NullStatsReceiver, StatsReceiver}
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.scrooge.{ThriftStruct, ThriftStructCodec}
import com.twitter.util.{Future, Return, Throw}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.{TMemoryBuffer, TMemoryInputTransport}
import scala.collection.{Map, Set}


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
class AccountService$FinagleClient(
  val service: FinagleService[ThriftClientRequest, Array[Byte]],
  val protocolFactory: TProtocolFactory = new TBinaryProtocol.Factory,
  val serviceName: String = "",
  stats: StatsReceiver = NullStatsReceiver
) extends AccountService[Future] {
  import AccountService._

  protected def encodeRequest(name: String, args: ThriftStruct) = {
    val buf = new TMemoryBuffer(512)
    val oprot = protocolFactory.getProtocol(buf)

    oprot.writeMessageBegin(new TMessage(name, TMessageType.CALL, 0))
    args.write(oprot)
    oprot.writeMessageEnd()

    val bytes = Arrays.copyOfRange(buf.getArray, 0, buf.length)
    new ThriftClientRequest(bytes, false)
  }

  protected def decodeResponse[T <: ThriftStruct](resBytes: Array[Byte], codec: ThriftStructCodec[T]) = {
    val iprot = protocolFactory.getProtocol(new TMemoryInputTransport(resBytes))
    val msg = iprot.readMessageBegin()
    try {
      if (msg.`type` == TMessageType.EXCEPTION) {
        val exception = TApplicationException.read(iprot) match {
          case sourced: SourcedException =>
            if (serviceName != "") sourced.serviceName = serviceName
            sourced
          case e => e
        }
        throw exception
      } else {
        codec.decode(iprot)
      }
    } finally {
      iprot.readMessageEnd()
    }
  }

  protected def missingResult(name: String) = {
    new TApplicationException(
      TApplicationException.MISSING_RESULT,
      name + " failed: unknown result"
    )
  }

  protected def setServiceName(ex: Exception): Exception =
    if (this.serviceName == "") ex
    else {
      ex match {
        case se: SourcedException =>
          se.serviceName = this.serviceName
          se
        case _ => ex
      }
    }

  // ----- end boilerplate.

  private[this] val scopedStats = if (serviceName != "") stats.scope(serviceName) else stats
  private[this] object __stats_create {
    val RequestsCounter = scopedStats.scope("create").counter("requests")
    val SuccessCounter = scopedStats.scope("create").counter("success")
    val FailuresCounter = scopedStats.scope("create").counter("failures")
    val FailuresScope = scopedStats.scope("create").scope("failures")
  }
  
  def create(user: User): Future[Boolean] = {
    __stats_create.RequestsCounter.incr()
    this.service(encodeRequest("create", create$args(user))) flatMap { response =>
      val result = decodeResponse(response, create$result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("create"))
    } respond {
      case Return(_) =>
        __stats_create.SuccessCounter.incr()
      case Throw(ex) =>
        __stats_create.FailuresCounter.incr()
        __stats_create.FailuresScope.counter(ex.getClass.getName).incr()
    }
  }
  private[this] object __stats_get {
    val RequestsCounter = scopedStats.scope("get").counter("requests")
    val SuccessCounter = scopedStats.scope("get").counter("success")
    val FailuresCounter = scopedStats.scope("get").counter("failures")
    val FailuresScope = scopedStats.scope("get").scope("failures")
  }
  
  def get(id: Long): Future[User] = {
    __stats_get.RequestsCounter.incr()
    this.service(encodeRequest("get", get$args(id))) flatMap { response =>
      val result = decodeResponse(response, get$result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("get"))
    } respond {
      case Return(_) =>
        __stats_get.SuccessCounter.incr()
      case Throw(ex) =>
        __stats_get.FailuresCounter.incr()
        __stats_get.FailuresScope.counter(ex.getClass.getName).incr()
    }
  }
  private[this] object __stats_list {
    val RequestsCounter = scopedStats.scope("list").counter("requests")
    val SuccessCounter = scopedStats.scope("list").counter("success")
    val FailuresCounter = scopedStats.scope("list").counter("failures")
    val FailuresScope = scopedStats.scope("list").scope("failures")
  }
  
  def list(): Future[Seq[User]] = {
    __stats_list.RequestsCounter.incr()
    this.service(encodeRequest("list", list$args())) flatMap { response =>
      val result = decodeResponse(response, list$result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("list"))
    } respond {
      case Return(_) =>
        __stats_list.SuccessCounter.incr()
      case Throw(ex) =>
        __stats_list.FailuresCounter.incr()
        __stats_list.FailuresScope.counter(ex.getClass.getName).incr()
    }
  }
  private[this] object __stats_map {
    val RequestsCounter = scopedStats.scope("map").counter("requests")
    val SuccessCounter = scopedStats.scope("map").counter("success")
    val FailuresCounter = scopedStats.scope("map").counter("failures")
    val FailuresScope = scopedStats.scope("map").scope("failures")
  }
  
  def map(): Future[Map[String, User]] = {
    __stats_map.RequestsCounter.incr()
    this.service(encodeRequest("map", map$args())) flatMap { response =>
      val result = decodeResponse(response, map$result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("map"))
    } respond {
      case Return(_) =>
        __stats_map.SuccessCounter.incr()
      case Throw(ex) =>
        __stats_map.FailuresCounter.incr()
        __stats_map.FailuresScope.counter(ex.getClass.getName).incr()
    }
  }
  private[this] object __stats_creates {
    val RequestsCounter = scopedStats.scope("creates").counter("requests")
    val SuccessCounter = scopedStats.scope("creates").counter("success")
    val FailuresCounter = scopedStats.scope("creates").counter("failures")
    val FailuresScope = scopedStats.scope("creates").scope("failures")
  }
  
  def creates(users: Seq[User] = Seq[User]()): Future[Seq[Boolean]] = {
    __stats_creates.RequestsCounter.incr()
    this.service(encodeRequest("creates", creates$args(users))) flatMap { response =>
      val result = decodeResponse(response, creates$result)
      val exception: Future[Nothing] =
        null
  
      if (result.success.isDefined)
        Future.value(result.success.get)
      else if (exception != null)
        exception
      else
        Future.exception(missingResult("creates"))
    } respond {
      case Return(_) =>
        __stats_creates.SuccessCounter.incr()
      case Throw(ex) =>
        __stats_creates.FailuresCounter.incr()
        __stats_creates.FailuresScope.counter(ex.getClass.getName).incr()
    }
  }
}
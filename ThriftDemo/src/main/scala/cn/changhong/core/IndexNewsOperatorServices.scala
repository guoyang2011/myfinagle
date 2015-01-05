/**
 * Generated by Scrooge
 *   version: 3.14.1
 *   rev: a996c1128a032845c508102d62e65fc0aa7a5f41
 *   built at: 20140501-114733
 */
package cn.changhong.core

import com.twitter.scrooge.{
  TFieldBlob, ThriftService, ThriftStruct, ThriftStructCodec, ThriftStructCodec3, ThriftStructFieldInfo, ThriftUtil}
import java.nio.ByteBuffer
import java.util.Arrays
import org.apache.thrift.protocol._
import org.apache.thrift.transport.TTransport
import org.apache.thrift.TApplicationException
import org.apache.thrift.transport.TMemoryBuffer
import scala.collection.immutable.{Map => immutable$Map}
import scala.collection.mutable.{
  Builder,
  ArrayBuffer => mutable$ArrayBuffer, Buffer => mutable$Buffer,
  HashMap => mutable$HashMap, HashSet => mutable$HashSet}
import scala.collection.{Map, Set}


@javax.annotation.Generated(value = Array("com.twitter.scrooge.Compiler"))
trait IndexNewsOperatorServices[+MM[_]] extends ThriftService {
  
  def indexNews(indexNews: NewsModel): MM[Boolean]
  
  def deleteArtificaillyNes(id: Int): MM[Int]
}


object IndexNewsOperatorServices {
  
  object indexNews$args extends ThriftStructCodec3[indexNews$args] {
    private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
    val Struct = new TStruct("indexNews_args")
    val IndexNewsField = new TField("indexNews", TType.STRUCT, 1)
    val IndexNewsFieldManifest = implicitly[Manifest[NewsModel]]
  
    /**
     * Field information in declaration order.
     */
    lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
      new ThriftStructFieldInfo(
        IndexNewsField,
        false,
        IndexNewsFieldManifest,
        None,
        None,
        immutable$Map(
        ),
        immutable$Map(
        )
      )
    )
  
    lazy val structAnnotations: immutable$Map[String, String] =
      immutable$Map[String, String](
      )
  
    /**
     * Checks that all required fields are non-null.
     */
    def validate(_item: indexNews$args) {
    }
  
    override def encode(_item: indexNews$args, _oproto: TProtocol) {
      _item.write(_oproto)
    }
  
    override def decode(_iprot: TProtocol): indexNews$args = {
      var indexNews: NewsModel = null
      var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
      var _done = false
  
      _iprot.readStructBegin()
      while (!_done) {
        val _field = _iprot.readFieldBegin()
        if (_field.`type` == TType.STOP) {
          _done = true
        } else {
          _field.id match {
            case 1 =>
              _field.`type` match {
                case TType.STRUCT => {
                  indexNews = readIndexNewsValue(_iprot)
                }
                case _actualType =>
                  val _expectedType = TType.STRUCT
            
                  throw new TProtocolException(
                    "Received wrong type for field 'indexNews' (expected=%s, actual=%s).".format(
                      ttypeToHuman(_expectedType),
                      ttypeToHuman(_actualType)
                    )
                  )
              }
            case _ =>
              if (_passthroughFields == null)
                _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
              _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
          }
          _iprot.readFieldEnd()
        }
      }
      _iprot.readStructEnd()
  
      new indexNews$args(
        indexNews,
        if (_passthroughFields == null)
          NoPassthroughFields
        else
          _passthroughFields.result()
      )
    }
  
    def apply(
      indexNews: NewsModel
    ): indexNews$args =
      new indexNews$args(
        indexNews
      )
  
    def unapply(_item: indexNews$args): Option[NewsModel] = Some(_item.indexNews)
  
  
    private def readIndexNewsValue(_iprot: TProtocol): NewsModel = {
      NewsModel.decode(_iprot)
    }
  
    private def writeIndexNewsField(indexNews_item: NewsModel, _oprot: TProtocol) {
      _oprot.writeFieldBegin(IndexNewsField)
      writeIndexNewsValue(indexNews_item, _oprot)
      _oprot.writeFieldEnd()
    }
  
    private def writeIndexNewsValue(indexNews_item: NewsModel, _oprot: TProtocol) {
      indexNews_item.write(_oprot)
    }
  
  
  
    private def ttypeToHuman(byte: Byte) = {
      // from https://github.com/apache/thrift/blob/master/lib/java/src/org/apache/thrift/protocol/TType.java
      byte match {
        case TType.STOP   => "STOP"
        case TType.VOID   => "VOID"
        case TType.BOOL   => "BOOL"
        case TType.BYTE   => "BYTE"
        case TType.DOUBLE => "DOUBLE"
        case TType.I16    => "I16"
        case TType.I32    => "I32"
        case TType.I64    => "I64"
        case TType.STRING => "STRING"
        case TType.STRUCT => "STRUCT"
        case TType.MAP    => "MAP"
        case TType.SET    => "SET"
        case TType.LIST   => "LIST"
        case TType.ENUM   => "ENUM"
        case _            => "UNKNOWN"
      }
    }
  
  }
  
  class indexNews$args(
      val indexNews: NewsModel,
      val _passthroughFields: immutable$Map[Short, TFieldBlob])
    extends ThriftStruct
    with scala.Product1[NewsModel]
    with java.io.Serializable
  {
    import indexNews$args._
      def this(
        indexNews: NewsModel
      ) = this(
        indexNews,
        Map.empty
      )
  
    def _1 = indexNews
  
  
    override def write(_oprot: TProtocol) {
      indexNews$args.validate(this)
      _oprot.writeStructBegin(Struct)
      if (indexNews ne null) writeIndexNewsField(indexNews, _oprot)
      _passthroughFields.values foreach { _.write(_oprot) }
      _oprot.writeFieldStop()
      _oprot.writeStructEnd()
    }
  
    def copy(
      indexNews: NewsModel = this.indexNews,
      _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
    ): indexNews$args =
      new indexNews$args(
        indexNews,
        _passthroughFields
      )
  
    override def canEqual(other: Any): Boolean = other.isInstanceOf[indexNews$args]
  
    override def equals(other: Any): Boolean =
      _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
        _passthroughFields == other.asInstanceOf[indexNews$args]._passthroughFields
  
    override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
  
    override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
  
  
    override def productArity: Int = 1
  
    override def productElement(n: Int): Any = n match {
      case 0 => this.indexNews
      case _ => throw new IndexOutOfBoundsException(n.toString)
    }
  
    override def productPrefix: String = "indexNews$args"
  }
  
  object indexNews$result extends ThriftStructCodec3[indexNews$result] {
    private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
    val Struct = new TStruct("indexNews_result")
    val SuccessField = new TField("success", TType.BOOL, 0)
    val SuccessFieldManifest = implicitly[Manifest[Boolean]]
  
    /**
     * Field information in declaration order.
     */
    lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
      new ThriftStructFieldInfo(
        SuccessField,
        true,
        SuccessFieldManifest,
        None,
        None,
        immutable$Map(
        ),
        immutable$Map(
        )
      )
    )
  
    lazy val structAnnotations: immutable$Map[String, String] =
      immutable$Map[String, String](
      )
  
    /**
     * Checks that all required fields are non-null.
     */
    def validate(_item: indexNews$result) {
    }
  
    override def encode(_item: indexNews$result, _oproto: TProtocol) {
      _item.write(_oproto)
    }
  
    override def decode(_iprot: TProtocol): indexNews$result = {
      var success: Option[Boolean] = None
      var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
      var _done = false
  
      _iprot.readStructBegin()
      while (!_done) {
        val _field = _iprot.readFieldBegin()
        if (_field.`type` == TType.STOP) {
          _done = true
        } else {
          _field.id match {
            case 0 =>
              _field.`type` match {
                case TType.BOOL => {
                  success = Some(readSuccessValue(_iprot))
                }
                case _actualType =>
                  val _expectedType = TType.BOOL
            
                  throw new TProtocolException(
                    "Received wrong type for field 'success' (expected=%s, actual=%s).".format(
                      ttypeToHuman(_expectedType),
                      ttypeToHuman(_actualType)
                    )
                  )
              }
            case _ =>
              if (_passthroughFields == null)
                _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
              _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
          }
          _iprot.readFieldEnd()
        }
      }
      _iprot.readStructEnd()
  
      new indexNews$result(
        success,
        if (_passthroughFields == null)
          NoPassthroughFields
        else
          _passthroughFields.result()
      )
    }
  
    def apply(
      success: Option[Boolean] = None
    ): indexNews$result =
      new indexNews$result(
        success
      )
  
    def unapply(_item: indexNews$result): Option[Option[Boolean]] = Some(_item.success)
  
  
    private def readSuccessValue(_iprot: TProtocol): Boolean = {
      _iprot.readBool()
    }
  
    private def writeSuccessField(success_item: Boolean, _oprot: TProtocol) {
      _oprot.writeFieldBegin(SuccessField)
      writeSuccessValue(success_item, _oprot)
      _oprot.writeFieldEnd()
    }
  
    private def writeSuccessValue(success_item: Boolean, _oprot: TProtocol) {
      _oprot.writeBool(success_item)
    }
  
  
  
    private def ttypeToHuman(byte: Byte) = {
      // from https://github.com/apache/thrift/blob/master/lib/java/src/org/apache/thrift/protocol/TType.java
      byte match {
        case TType.STOP   => "STOP"
        case TType.VOID   => "VOID"
        case TType.BOOL   => "BOOL"
        case TType.BYTE   => "BYTE"
        case TType.DOUBLE => "DOUBLE"
        case TType.I16    => "I16"
        case TType.I32    => "I32"
        case TType.I64    => "I64"
        case TType.STRING => "STRING"
        case TType.STRUCT => "STRUCT"
        case TType.MAP    => "MAP"
        case TType.SET    => "SET"
        case TType.LIST   => "LIST"
        case TType.ENUM   => "ENUM"
        case _            => "UNKNOWN"
      }
    }
  
  }
  
  class indexNews$result(
      val success: Option[Boolean],
      val _passthroughFields: immutable$Map[Short, TFieldBlob])
    extends ThriftStruct
    with scala.Product1[Option[Boolean]]
    with java.io.Serializable
  {
    import indexNews$result._
      def this(
        success: Option[Boolean] = None
      ) = this(
        success,
        Map.empty
      )
  
    def _1 = success
  
  
    override def write(_oprot: TProtocol) {
      indexNews$result.validate(this)
      _oprot.writeStructBegin(Struct)
      if (success.isDefined) writeSuccessField(success.get, _oprot)
      _passthroughFields.values foreach { _.write(_oprot) }
      _oprot.writeFieldStop()
      _oprot.writeStructEnd()
    }
  
    def copy(
      success: Option[Boolean] = this.success,
      _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
    ): indexNews$result =
      new indexNews$result(
        success,
        _passthroughFields
      )
  
    override def canEqual(other: Any): Boolean = other.isInstanceOf[indexNews$result]
  
    override def equals(other: Any): Boolean =
      _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
        _passthroughFields == other.asInstanceOf[indexNews$result]._passthroughFields
  
    override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
  
    override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
  
  
    override def productArity: Int = 1
  
    override def productElement(n: Int): Any = n match {
      case 0 => this.success
      case _ => throw new IndexOutOfBoundsException(n.toString)
    }
  
    override def productPrefix: String = "indexNews$result"
  }
  
  object deleteArtificaillyNes$args extends ThriftStructCodec3[deleteArtificaillyNes$args] {
    private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
    val Struct = new TStruct("deleteArtificaillyNes_args")
    val IdField = new TField("id", TType.I32, 1)
    val IdFieldManifest = implicitly[Manifest[Int]]
  
    /**
     * Field information in declaration order.
     */
    lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
      new ThriftStructFieldInfo(
        IdField,
        false,
        IdFieldManifest,
        None,
        None,
        immutable$Map(
        ),
        immutable$Map(
        )
      )
    )
  
    lazy val structAnnotations: immutable$Map[String, String] =
      immutable$Map[String, String](
      )
  
    /**
     * Checks that all required fields are non-null.
     */
    def validate(_item: deleteArtificaillyNes$args) {
    }
  
    override def encode(_item: deleteArtificaillyNes$args, _oproto: TProtocol) {
      _item.write(_oproto)
    }
  
    override def decode(_iprot: TProtocol): deleteArtificaillyNes$args = {
      var id: Int = 0
      var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
      var _done = false
  
      _iprot.readStructBegin()
      while (!_done) {
        val _field = _iprot.readFieldBegin()
        if (_field.`type` == TType.STOP) {
          _done = true
        } else {
          _field.id match {
            case 1 =>
              _field.`type` match {
                case TType.I32 => {
                  id = readIdValue(_iprot)
                }
                case _actualType =>
                  val _expectedType = TType.I32
            
                  throw new TProtocolException(
                    "Received wrong type for field 'id' (expected=%s, actual=%s).".format(
                      ttypeToHuman(_expectedType),
                      ttypeToHuman(_actualType)
                    )
                  )
              }
            case _ =>
              if (_passthroughFields == null)
                _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
              _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
          }
          _iprot.readFieldEnd()
        }
      }
      _iprot.readStructEnd()
  
      new deleteArtificaillyNes$args(
        id,
        if (_passthroughFields == null)
          NoPassthroughFields
        else
          _passthroughFields.result()
      )
    }
  
    def apply(
      id: Int
    ): deleteArtificaillyNes$args =
      new deleteArtificaillyNes$args(
        id
      )
  
    def unapply(_item: deleteArtificaillyNes$args): Option[Int] = Some(_item.id)
  
  
    private def readIdValue(_iprot: TProtocol): Int = {
      _iprot.readI32()
    }
  
    private def writeIdField(id_item: Int, _oprot: TProtocol) {
      _oprot.writeFieldBegin(IdField)
      writeIdValue(id_item, _oprot)
      _oprot.writeFieldEnd()
    }
  
    private def writeIdValue(id_item: Int, _oprot: TProtocol) {
      _oprot.writeI32(id_item)
    }
  
  
  
    private def ttypeToHuman(byte: Byte) = {
      // from https://github.com/apache/thrift/blob/master/lib/java/src/org/apache/thrift/protocol/TType.java
      byte match {
        case TType.STOP   => "STOP"
        case TType.VOID   => "VOID"
        case TType.BOOL   => "BOOL"
        case TType.BYTE   => "BYTE"
        case TType.DOUBLE => "DOUBLE"
        case TType.I16    => "I16"
        case TType.I32    => "I32"
        case TType.I64    => "I64"
        case TType.STRING => "STRING"
        case TType.STRUCT => "STRUCT"
        case TType.MAP    => "MAP"
        case TType.SET    => "SET"
        case TType.LIST   => "LIST"
        case TType.ENUM   => "ENUM"
        case _            => "UNKNOWN"
      }
    }
  
  }
  
  class deleteArtificaillyNes$args(
      val id: Int,
      val _passthroughFields: immutable$Map[Short, TFieldBlob])
    extends ThriftStruct
    with scala.Product1[Int]
    with java.io.Serializable
  {
    import deleteArtificaillyNes$args._
      def this(
        id: Int
      ) = this(
        id,
        Map.empty
      )
  
    def _1 = id
  
  
    override def write(_oprot: TProtocol) {
      deleteArtificaillyNes$args.validate(this)
      _oprot.writeStructBegin(Struct)
      writeIdField(id, _oprot)
      _passthroughFields.values foreach { _.write(_oprot) }
      _oprot.writeFieldStop()
      _oprot.writeStructEnd()
    }
  
    def copy(
      id: Int = this.id,
      _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
    ): deleteArtificaillyNes$args =
      new deleteArtificaillyNes$args(
        id,
        _passthroughFields
      )
  
    override def canEqual(other: Any): Boolean = other.isInstanceOf[deleteArtificaillyNes$args]
  
    override def equals(other: Any): Boolean =
      _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
        _passthroughFields == other.asInstanceOf[deleteArtificaillyNes$args]._passthroughFields
  
    override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
  
    override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
  
  
    override def productArity: Int = 1
  
    override def productElement(n: Int): Any = n match {
      case 0 => this.id
      case _ => throw new IndexOutOfBoundsException(n.toString)
    }
  
    override def productPrefix: String = "deleteArtificaillyNes$args"
  }
  
  object deleteArtificaillyNes$result extends ThriftStructCodec3[deleteArtificaillyNes$result] {
    private val NoPassthroughFields = immutable$Map.empty[Short, TFieldBlob]
    val Struct = new TStruct("deleteArtificaillyNes_result")
    val SuccessField = new TField("success", TType.I32, 0)
    val SuccessFieldManifest = implicitly[Manifest[Int]]
  
    /**
     * Field information in declaration order.
     */
    lazy val fieldInfos: scala.List[ThriftStructFieldInfo] = scala.List[ThriftStructFieldInfo](
      new ThriftStructFieldInfo(
        SuccessField,
        true,
        SuccessFieldManifest,
        None,
        None,
        immutable$Map(
        ),
        immutable$Map(
        )
      )
    )
  
    lazy val structAnnotations: immutable$Map[String, String] =
      immutable$Map[String, String](
      )
  
    /**
     * Checks that all required fields are non-null.
     */
    def validate(_item: deleteArtificaillyNes$result) {
    }
  
    override def encode(_item: deleteArtificaillyNes$result, _oproto: TProtocol) {
      _item.write(_oproto)
    }
  
    override def decode(_iprot: TProtocol): deleteArtificaillyNes$result = {
      var success: Option[Int] = None
      var _passthroughFields: Builder[(Short, TFieldBlob), immutable$Map[Short, TFieldBlob]] = null
      var _done = false
  
      _iprot.readStructBegin()
      while (!_done) {
        val _field = _iprot.readFieldBegin()
        if (_field.`type` == TType.STOP) {
          _done = true
        } else {
          _field.id match {
            case 0 =>
              _field.`type` match {
                case TType.I32 => {
                  success = Some(readSuccessValue(_iprot))
                }
                case _actualType =>
                  val _expectedType = TType.I32
            
                  throw new TProtocolException(
                    "Received wrong type for field 'success' (expected=%s, actual=%s).".format(
                      ttypeToHuman(_expectedType),
                      ttypeToHuman(_actualType)
                    )
                  )
              }
            case _ =>
              if (_passthroughFields == null)
                _passthroughFields = immutable$Map.newBuilder[Short, TFieldBlob]
              _passthroughFields += (_field.id -> TFieldBlob.read(_field, _iprot))
          }
          _iprot.readFieldEnd()
        }
      }
      _iprot.readStructEnd()
  
      new deleteArtificaillyNes$result(
        success,
        if (_passthroughFields == null)
          NoPassthroughFields
        else
          _passthroughFields.result()
      )
    }
  
    def apply(
      success: Option[Int] = None
    ): deleteArtificaillyNes$result =
      new deleteArtificaillyNes$result(
        success
      )
  
    def unapply(_item: deleteArtificaillyNes$result): Option[Option[Int]] = Some(_item.success)
  
  
    private def readSuccessValue(_iprot: TProtocol): Int = {
      _iprot.readI32()
    }
  
    private def writeSuccessField(success_item: Int, _oprot: TProtocol) {
      _oprot.writeFieldBegin(SuccessField)
      writeSuccessValue(success_item, _oprot)
      _oprot.writeFieldEnd()
    }
  
    private def writeSuccessValue(success_item: Int, _oprot: TProtocol) {
      _oprot.writeI32(success_item)
    }
  
  
  
    private def ttypeToHuman(byte: Byte) = {
      // from https://github.com/apache/thrift/blob/master/lib/java/src/org/apache/thrift/protocol/TType.java
      byte match {
        case TType.STOP   => "STOP"
        case TType.VOID   => "VOID"
        case TType.BOOL   => "BOOL"
        case TType.BYTE   => "BYTE"
        case TType.DOUBLE => "DOUBLE"
        case TType.I16    => "I16"
        case TType.I32    => "I32"
        case TType.I64    => "I64"
        case TType.STRING => "STRING"
        case TType.STRUCT => "STRUCT"
        case TType.MAP    => "MAP"
        case TType.SET    => "SET"
        case TType.LIST   => "LIST"
        case TType.ENUM   => "ENUM"
        case _            => "UNKNOWN"
      }
    }
  
  }
  
  class deleteArtificaillyNes$result(
      val success: Option[Int],
      val _passthroughFields: immutable$Map[Short, TFieldBlob])
    extends ThriftStruct
    with scala.Product1[Option[Int]]
    with java.io.Serializable
  {
    import deleteArtificaillyNes$result._
      def this(
        success: Option[Int] = None
      ) = this(
        success,
        Map.empty
      )
  
    def _1 = success
  
  
    override def write(_oprot: TProtocol) {
      deleteArtificaillyNes$result.validate(this)
      _oprot.writeStructBegin(Struct)
      if (success.isDefined) writeSuccessField(success.get, _oprot)
      _passthroughFields.values foreach { _.write(_oprot) }
      _oprot.writeFieldStop()
      _oprot.writeStructEnd()
    }
  
    def copy(
      success: Option[Int] = this.success,
      _passthroughFields: immutable$Map[Short, TFieldBlob] = this._passthroughFields
    ): deleteArtificaillyNes$result =
      new deleteArtificaillyNes$result(
        success,
        _passthroughFields
      )
  
    override def canEqual(other: Any): Boolean = other.isInstanceOf[deleteArtificaillyNes$result]
  
    override def equals(other: Any): Boolean =
      _root_.scala.runtime.ScalaRunTime._equals(this, other) &&
        _passthroughFields == other.asInstanceOf[deleteArtificaillyNes$result]._passthroughFields
  
    override def hashCode: Int = _root_.scala.runtime.ScalaRunTime._hashCode(this)
  
    override def toString: String = _root_.scala.runtime.ScalaRunTime._toString(this)
  
  
    override def productArity: Int = 1
  
    override def productElement(n: Int): Any = n match {
      case 0 => this.success
      case _ => throw new IndexOutOfBoundsException(n.toString)
    }
  
    override def productPrefix: String = "deleteArtificaillyNes$result"
  }

  import com.twitter.util.Future

  trait FutureIface extends  IndexNewsOperatorServices[Future] {
    
    def indexNews(indexNews: NewsModel): Future[Boolean]
    
    def deleteArtificaillyNes(id: Int): Future[Int]
  }

  class FinagledClient(
      service: com.twitter.finagle.Service[com.twitter.finagle.thrift.ThriftClientRequest, Array[Byte]],
      protocolFactory: TProtocolFactory = new TBinaryProtocol.Factory,
      serviceName: String = "",
      stats: com.twitter.finagle.stats.StatsReceiver = com.twitter.finagle.stats.NullStatsReceiver)
    extends IndexNewsOperatorServices$FinagleClient(
      service,
      protocolFactory,
      serviceName,
      stats)
    with FutureIface

  class FinagledService(
      iface: FutureIface,
      protocolFactory: TProtocolFactory)
    extends IndexNewsOperatorServices$FinagleService(
      iface,
      protocolFactory)
}
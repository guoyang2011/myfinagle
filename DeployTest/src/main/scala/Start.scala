import java.net.InetSocketAddress
import java.nio.charset.Charset

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import com.twitter.util.Future
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization._
import org.csource.common._
import org.csource.fastdfs._
import org.im4java.core.{GMOperation, ConvertCmd}
import org.jboss.netty.buffer.{ChannelBuffers}
import org.jboss.netty.handler.codec.http._

/**
 * Created by yangguo on 14-11-25.
 */
case class Person(id:Int,name:String,password:String,age:Int)
object ImageType{
  val pix50="50x50"
  val pix78="78x78"
  val pix100="100x100"
  val pix150="150x150"
  val pixType:Array[String]=Array("50x50","78x78","100x100","150x150")
}
object Start{
  def main(args:Array[String]): Unit = {
    if(args.length<2) throw new IllegalArgumentException("server port,server name")
    val service=new Service[HttpRequest,HttpResponse] {
      override def apply(request: HttpRequest): Future[HttpResponse] = {
        Future.value{
          val response=new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK)
          val content="serverName:"+args(1)
          response.setContent(ChannelBuffers.copiedBuffer(content.getBytes(Charset.forName("utf8"))))
          response
        }
      }
    }
    ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(args(0).toInt))
      .name(args(1))
      .build(service)
  }
  def defaultImageHandler(args:Array[String]): Unit ={
    implicit val format = DefaultFormats
    val xp = Person(1, "xp", "xp", 10)
    println(write(xp))
    val fid:Option[String]=if (args.length < 4) {
      println(
        "Error: Must have 2 parameters, one is config filename, "
          + "the other is the local filename to upload")
      None
    }else{
        val conf_filename=args(0)
        val local_filename=args(1)
        var trackerServer:TrackerServer=null
      try {
        ClientGlobal.init(conf_filename)
        println("network_timeout="+ClientGlobal.g_network_timeout+"ms")
        println("charset="+ClientGlobal.g_charset)
        val tracker=new TrackerClient()
        trackerServer=tracker.getConnection
        val storageServer=null
        val client=new StorageClient1(trackerServer,storageServer)
        val metaList=new Array[NameValuePair](3)
        metaList(0)=new NameValuePair("fileName",local_filename)
        val fileId=client.upload_file1(local_filename,null,null)
        println("upload success.file id is:"+fileId)
        Some(fileId)
      }catch {
        case e =>None
      }finally {
        if(trackerServer!=null)trackerServer.close()
      }
    }
    fid match{
      case Some(id)=>{
        thumbnailOp(args(1)) match {
          case Some(files)=>
            files.foreach { tmpPath=>
              println(tmpPath(1)+","+tmpPath(0))
              uploadSlaveFile(id, tmpPath(1), tmpPath(0)) match {
                case Some(slaverFid) => println("Slaver Id=" + slaverFid)
              }
            }
          case None=>
        }
      }
      case None=>
    }
  }
  /**
   *
   * @param masterFileId master文件的Fid
   * @param prefixName slave文件后缀名
   * @param slavaFilePath 需要上传文件的路径
   * @return
   */
  def uploadSlaveFile(masterFileId:String,prefixName:String,slavaFilePath:String): Option[String] ={
    val slaverFileExtName= if (slavaFilePath.contains(".")) Some(slavaFilePath.substring(slavaFilePath.lastIndexOf('.') + 1))

    slaverFileExtName match{
      case Some(ext)=>{
        val tracker=new TrackerClient()
        val trackerServer=tracker.getConnection
        val client=new StorageClient1(trackerServer,null)
        val slaveFid:Option[String]=try{
          println("masterId="+masterFileId+",prefixName="+prefixName+",slaverFilePath="+slavaFilePath+",ext="+ext.toString)
          Some(client.upload_file1(masterFileId,prefixName,slavaFilePath,ext.toString,null))
        }catch {
          case e=>None
        }finally {
          trackerServer.close()
        }
        slaveFid
      }
      case None=> None
    }
  }
  def thumbnailOp(imagePath:String): Option[Array[Array[String]]] ={
    try{
      val imgType=imagePath.substring(imagePath.lastIndexOf('.'))
      val newDefaultPath="/tmp"
      val paths:Array[Array[String]]=ImageType.pixType map { pix =>
        val tempPath=newDefaultPath+"/"+pix+imgType
        val op = new GMOperation
        op.addImage(imagePath)
        op.addRawArgs("-thumbnail", pix+"!")
        op.addRawArgs("-gravity", "center")
        op.addImage(tempPath)
        val convert = new ConvertCmd(true)
        convert.run(op)
        Array(tempPath,pix)
      }
      Some(paths)
    }catch{
      case e=> {
        println(e.getLocalizedMessage)
        None
      }
    }
  }
}

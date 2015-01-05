package cn.changhong.finagle.http

import java.io.{RandomAccessFile, FileOutputStream, FileInputStream, File}
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.util.concurrent.{Callable, Executors, Executor, ThreadPoolExecutor}
import java.util.zip.CRC32

import com.google.common.hash.Hashing
import com.google.common.io.Files
import io.netty.util.CharsetUtil

import scala.collection.JavaConversions.asJavaCollection
/**
 * Created by yangguo on 14-10-26.
 */
object FileChunk extends  App {
  case class FileChunk(length: Long, id: Int, start: Long, end: Long)
  val defaultChunkSize:Int=1024*1024
  def getFileChunks(file: File, tchunkSize: Int): Seq[FileChunk] = {
    if (file.length() > 1024 * 1024) {
      val cChunkSize :Int = {
        if (defaultChunkSize > tchunkSize) defaultChunkSize
        else tchunkSize
      }
      val chunkSize = (file.length() / defaultChunkSize).toInt
      val remainSize = file.length() % defaultChunkSize
      val chunks = (0 to chunkSize).map { index =>
        FileChunk(defaultChunkSize, index, index * defaultChunkSize, (index + 1) * defaultChunkSize - 1)
      }
      if (remainSize == 0) chunks
      else FileChunk(remainSize.toInt, chunkSize + 1, (chunkSize + 1) * defaultChunkSize, file.length()) :: chunks.toList
    } else {
      Seq(FileChunk(file.length(), 0, 0, file.length()))
    }
  }
  def byte2Hex(bytes:Array[Byte]): String = {
    var str:String=""
    bytes foreach{b=>
      val s=Integer.toHexString(b & 0xFF)
      val t=if(s.length == 1) "0"+s
        else s
      str+=t
    }
    str
  }
  val file = new File("/Users/yangguo/Downloads/分布式算法导论.pdf")
  val in = new RandomAccessFile(file, "rw")
  val inchannel = in.getChannel

  println(file.getCanonicalFile + "," + file.getName)

  (file.exists(), file.isFile) match {
    case (true, true) =>
      val callbacks = getFileChunks(file, 1024) map { chunk =>
        new Callable[Any] {
          override def call(): AnyRef = {
            val filename = "/Users/yangguo/" + file.getName + ".ChunkId." + chunk.id
            val tempfile = new File(filename)
            val out = new FileOutputStream(tempfile)
            val buffers = inchannel.map(FileChannel.MapMode.READ_WRITE, chunk.start, chunk.length)
            val outchannel = out.getChannel
            val size = outchannel.write(buffers)
//            println("writeSize:" + size + ",read size:" + chunk.length)

//            val md = MessageDigest.getInstance("MD5")
//            md.update(buffers)
//            println(byte2Hex(md.digest()).toUpperCase+","+chunk.id)
//            if (buffers.hasArray) {
//              val crc32 = new CRC32()
//              val buffer = buffers.array()
//              crc32.update(buffer)
//              println(crc32.getValue + "crc32")
//            } else {
//              println("no array")
//            }
            outchannel.close()
            val md5=Files.hash(tempfile,Hashing.md5())
            println(byte2Hex(md5.asBytes()).toString+","+chunk.id)
            chunk
          }
        }//d45baae0ce82e9678f243eafab4a7c4
      }
      //           new Runnable {
      //             override def run(): Unit = {
      //               val filename="/Users/yangguo/"+file.getName+".ChunkId."+chunk.id
      //                val tempfile=new File(filename)
      //                val out=new FileOutputStream(tempfile)
      //                val buffers=inchannel.map(FileChannel.MapMode.READ_WRITE,chunk.start,chunk.length)
      //                val outchannel=out.getChannel
      //                val size=outchannel.write(buffers)
      //                println(size)
      //                outchannel.close()
      //                println(chunk.length+",id="+chunk.id+",start="+chunk.start+",end="+chunk.end)
      //             }
      //           }
      //      }

      val executors = Executors.newFixedThreadPool(4)
      executors.invokeAll(callbacks)
  }
}

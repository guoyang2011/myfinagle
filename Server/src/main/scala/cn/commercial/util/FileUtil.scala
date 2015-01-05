package cn.commercial.util

import java.io._
import java.nio.ByteBuffer
import java.util.zip.{GZIPOutputStream, GZIPInputStream, CRC32}

import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.util.CharsetUtil

/**
 * Created by yangguo on 14-10-30.
 */
object FileUtil {
  /*
  分段上传文件头信息
  Chunk-Size:4//文件分段大小
  Chunk-Id:1//当前分段的ID号码
  Chunk-StartWith:13232//当前Chunk在文件中起始位置
  Chunk-EndWith:23434//当前Chunk在文件中结束位置
  Chunk-UUID:123232-3243243//上传文件的唯一标示号
  Chunk-Length:1024//上传文件的大小
  Chunk-CheckSum: CheckSumType Value//当前Chunk的checksumType,checksum,
  Chunk-CompressType:gzip // 当前Chunk使用的压缩算法
   */
  /*
  三个任务
  1.获取文件分片
  2.对文件分片压缩流
  3.获取每个文件压缩流的checksum
  对文件分区进行压缩流和获取checksum的流程：
    压缩流输出ByteBuf=>获取压缩流的checksum
   */
  /*
  ByteArrayInputStream和ByteArrayOutStream中reset方法用法不一样
  ByteArrayInputStream 中调用reset()后标志从索引位置为开始重新读
  ByteArrayOutStream中调用reset()后清空容器中中所有数据
  ByteArrayOutStream中调用toByteArray后会出现数组复制
   */
  object FileChunk{
    case class FileChunkInfo(id:Int,startWith:Long,endWith:Long,length:Long)
    val defaultChunkSize:Int=1048576//1024*1424
    val BUFFER_SIZE=1024
    val buffer=new Array[Byte](BUFFER_SIZE)

    @throws(classOf[IllegalArgumentException])
    def createFileChunks(file:File,tChunkSize:Int=defaultChunkSize):Option[Seq[FileChunkInfo]]= {
      (file.exists() , file.exists()) match {
        case (true, true) =>
          val chunkSize = (file.length() / tChunkSize).toInt
          val remainSize = file.length() % tChunkSize
          val chunks = (0 to chunkSize) map { index =>
            FileChunkInfo(index, index * tChunkSize, (index + 1) * tChunkSize, chunkSize)
          }
          Some {
            if (remainSize == 0) chunks
            else FileChunkInfo(chunkSize + 1, (chunkSize + 1) * tChunkSize, file.length(), remainSize) :: chunks.toList
          }
        case _ => None
      }
    }

    /**
     * 压缩文件
     * @param is
     * @param out
     */
    def gzipCompressStream(is:InputStream,out:OutputStream): Unit ={
      val gos=new GZIPOutputStream(out)
      try{
        Iterator continually(is.read(buffer,0,BUFFER_SIZE)) takeWhile( _ != -1) foreach(gos.write(buffer,0,_))
      }finally{
        gos.close()
      }
    }
    def gzipDecompressStream(is:InputStream,out:OutputStream): Unit ={
      val gis=new GZIPInputStream(is)
      try{
        Iterator continually (gis.read(buffer,0,BUFFER_SIZE)) takeWhile(_ != -1) foreach(out.write(buffer,0,_))
      }finally{
        gis.close()
      }
    }

    /**
     * 生成文件checksum
     * @param input
     * @return
     */
    def streamChecksumGenerator(input:Option[InputStream]):Option[Long]={
      input map {in=>
          val crc32=new CRC32
          Iterator continually(in.read(buffer,0,BUFFER_SIZE)) takeWhile(_ != -1) foreach(crc32.update(buffer,0,_))
          crc32.getValue
      }
    }
    def fileChecksumGenerator(file:File):Option[Long]= {
      (file.exists, file.isFile) match{
        case (true, true) =>
          var inputStream: Option[FileInputStream] = None
          try {
            inputStream = Some(new FileInputStream(file))
            streamChecksumGenerator(inputStream)
          } finally {
            inputStream match {
              case Some(in) => in.close()
            }
          }
      }
    }
    def checksumGenerator[T](in:T,checksumF:(T)=>Array[Byte],byte2HexF:(Array[Byte])=>String=byte2Hex):String={
      byte2HexF(checksumF(in))
    }

    def byte2Hex(bytes: Array[Byte]): String = {
      var str: String = ""
      bytes foreach { b =>
        str += Integer.toHexString(b & 0xFF)
      }
      str
    }


    /*
    对于大文件可能出现OutOfMemoryException问题
     */
    def crc32Checksum(buffer:ByteBuffer):Long={
      val b=buffer.hasArray match {
        case true=>buffer.array()
        case _=>
          val tBuffer=new Array[Byte](buffer.remaining())
          buffer.get(tBuffer,0,buffer.remaining())
          tBuffer
      }
      crc32Checksum(b)
    }
    def crc32Checksum(arr:Array[Byte]):Long={
      val crc32=new CRC32()
      crc32.update(arr)
      crc32.getValue
    }
    def crc32Checksum(file:File):Option[Long]= {
      (file.exists(), file.isFile) match {
        case (true, true) => Some {
          val inStream = new FileInputStream(file)
          try {
            val crc32 = new CRC32
            val buffer = new Array[Byte](1024)
            Iterator continually (inStream.read(buffer)) takeWhile (_ != -1) foreach (read => crc32.update(buffer, 0, read))
            crc32.getValue
          } finally {
            inStream.close()
          }
        }
      }
    }
  }

  def main(args:Array[String]): Unit = {
    import FileChunk._
    start
    def start={
      val b_in1=new ByteArrayInputStream(ChannelBuffers.copiedBuffer("sdadsad           sdadsad           sdadsad           sdadsad           sdadsad           sdadsad           sdadsad           sdadsad           ",CharsetUtil.UTF_8).array())
      val b_compress_out1=new ByteArrayOutputStream()
      gzipCompressStream(b_in1,b_compress_out1)
      val b_compress_in2=new ByteArrayInputStream(b_compress_out1.toByteArray)
      b_compress_in2.reset()
      println(b_compress_out1.size())

      println(crc32Checksum(b_compress_out1.toByteArray))

      val b_decompress_out2=b_compress_out1
      gzipDecompressStream(b_compress_in2,b_decompress_out2)
      println(b_decompress_out2.size())

      println(crc32Checksum(b_decompress_out2.toByteArray))



    }
    def preTest {
      val byteBuffer = ChannelBuffers.copiedBuffer("                      dsasa                      dsasa                      dsasa                      dsasa                      dsasa                      dsasa                      dsasa                      dsasa                      dsa", CharsetUtil.UTF_8)
      val instreamBuffer = new ByteArrayInputStream(byteBuffer.array())
      val outStreamBuffer = new ByteArrayOutputStream()
      import FileChunk._
      gzipCompressStream(instreamBuffer, outStreamBuffer)
      outStreamBuffer.toByteArray foreach print
      instreamBuffer.reset()
      val read = (instreamBuffer.read(buffer))

      println("\n" + read + ",11=" + outStreamBuffer.toByteArray.size)
      val decomStreamBuffer = new ByteArrayOutputStream()
      val comStreamBuffer = new ByteArrayInputStream(outStreamBuffer.toByteArray)
      gzipDecompressStream(comStreamBuffer, decomStreamBuffer)
      byteBuffer.resetReaderIndex()
      decomStreamBuffer.toByteArray foreach print


      println(byteBuffer.readableBytes() + "," + decomStreamBuffer.toByteArray.size)
    }
  }
}

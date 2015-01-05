package cn.changhong.finagle.http

import java.io._
import java.util.zip.{GZIPOutputStream, GZIPInputStream}

/**
 * Created by yangguo on 14-10-26.
 */
object CompressFile extends  App{
  val BUFFER_SIZE=1024
  val EXT=".gz"
  def gzipDecompress(is:InputStream,out:OutputStream): Unit ={
    val gis=new GZIPInputStream(is)
    val buffer=new Array[Byte](BUFFER_SIZE)
    Iterator.continually(gis.read(buffer,0,BUFFER_SIZE)).takeWhile(_ != -1).foreach(out.write(buffer,0,_))
    gis.close()
  }
  def gzipCompress(is:InputStream,out:OutputStream): Unit ={
    val gos=new GZIPOutputStream(out)
    val buffer=new Array[Byte](BUFFER_SIZE)
    Iterator.continually(is.read(buffer,0,BUFFER_SIZE)).takeWhile(_ != -1).foreach(gos.write(buffer,0,_))
    gos.close()
  }
  def compressTest: Unit ={
    val srcFile=new File("/Users/yangguo/speedtest_cli.py")
    val dstFile=new File("/Users/yangguo/speedtest_cli.py.gz")
    val is=new FileInputStream(srcFile)
    val os=new FileOutputStream(dstFile)
    gzipCompress(is,os)
    is.close()
    os.close()
    println(srcFile.length()+","+dstFile.length())
  }
  def decompressTest: Unit ={
    val srcFile=new File("/Users/yangguo/speedtest_cli.py.gz")
    val dstFile=new File("/Users/yangguo/speedtest_cli1.py")
    val is=new FileInputStream(srcFile)
    val os=new FileOutputStream(dstFile)
    gzipDecompress(is,os)
    is.close()
    os.close()
    println(srcFile.length()+","+dstFile.length())
  }
  compressTest
  decompressTest
}

package cn.changhong.finagle.http

import java.io.File
import java.security.MessageDigest

import com.google.common.hash.{HashCode, Hashing}
import com.google.common.io.Files

/**
 * Created by yangguo on 14-10-26.
 */
object FileCheckSum extends App {

  import GoogleIo._
  import JavaIo._
  val file = new File("/Users/yangguo/speedtest_cli.py")
  md5(file)
  md5_j
  sha256(file)
  crc32(file)
  sha1(file)
  object JavaIo{
    def md5_j ={
      val str = "TEST STRING"
      val md5 = MessageDigest.getInstance("MD5")
      md5.update(str.getBytes, 0, str.length)
      val checksum = md5.digest()
      println("Java IO MD5:" + byte2Hex(checksum))
    }
  }
  object GoogleIo {
    def md5(file: File) = {
      val hc = Files.hash(file, Hashing.md5())
      hc.asBytes() foreach (print)
      println("MD5:" + byte2Hex(hc.asBytes()).toString + "," + new String(hc.asBytes()).toString)
    }

    def sha256(file: File): Unit = {
      val checksum = Files.hash(file, Hashing.sha256())
      println("SHA256:" + byte2Hex(checksum.asBytes()))
    }

    //MD5:778acda9192f6ec6d5f0fe196da45e
    //SHA256:f05d262f86c2f3b9bceebd3eb4c5d35e4712f1144e3754cda364cf98e4a1af
    //CRC32:d87039ca
    //SHA-1:c3bd83ebcb6df1b3468a72595cfe776525e6f
    def crc32(file: File): Unit = {
      val checksum = Files.hash(file, Hashing.crc32())
      println("CRC32:" + byte2Hex(checksum.asBytes()))
    }

    def sha1(file: File): Unit = {
      val checksum = Files.hash(file, Hashing.sha1())
      println("SHA-1:" + byte2Hex(checksum.asBytes()))
    }
  }

  def byte2Hex(bytes: Array[Byte]): String = {
    var str: String = ""
    bytes foreach { b =>
      str += Integer.toHexString(b & 0xFF)
    }
    str
  }
}

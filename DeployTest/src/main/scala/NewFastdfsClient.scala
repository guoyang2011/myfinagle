import java.net.InetSocketAddress

import org.csource.common.NameValuePair
import org.csource.fastdfs._

/**
 * Created by yangguo on 15-1-30.
 */
object NewFastdfsClient {
  def main(args:Array[String]): Unit ={
    val client_config="/Users/yangguo/Downloads/src/fdfs_client.conf"
    val uploadFileName=client_config

//    val trackerServer:TrackerServer=
    ClientGlobal.setG_charset("UTF-8")
    ClientGlobal.setG_connect_timeout(2000)
    ClientGlobal.setG_network_timeout(30000)
    ClientGlobal.setG_tracker_group(new TrackerGroup(Array(new InetSocketAddress("10.9.52.31",22122))))
    val tracker=new TrackerClient()
    val trackerServer=tracker.getConnection
    val storageServer=null
    val client=new StorageClient1(trackerServer,storageServer)
    val metaList=new Array[NameValuePair](3)
    metaList(0)=new NameValuePair("fileName",uploadFileName)
    val fileId=client.upload_file1(uploadFileName,null,null)
    println(s"upload success file id=${fileId}")

  }
}

import java.util
import java.util.concurrent.CountDownLatch

import org.apache.zookeeper.AsyncCallback.{Children2Callback, StatCallback}
import org.apache.zookeeper.Watcher.Event.{KeeperState, EventType}
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.data.Stat
import org.apache.zookeeper._


/**
 * Created by yangguo on 14-12-2.
 */
//class Executor extends Watcher with Runnable{
//  override def process(event: WatchedEvent): Unit = {
//    (event.getPath,event.getType) match{
//      case ("/testRootPath",EventType.NodeDataChanged) =>
//        println("node Data Channged")
//      case _=>
//        println("Node Event:"+event.getState+","+event.getPath+",")
//    }
//  }
//
//  override def run(): Unit = ???
//}
//class LitterTask extends Runnable{
//  override def run(): Unit = {
//
//  }
//}
//
//object ZookeeperStart {
//  def main(args:Array[String]): Unit ={
////    start()
//    val sample=new ZooKeeper_GetChildren_API_Sync_Usage(null);
//    val path="/get_children_test"
//    try{
//      sample.createPath_sync(path,"",CreateMode.PERSISTENT)
//      sample.createPath_sync(path+"/c1","",CreateMode.PERSISTENT)
//      sample.getChildren(path)
//      sample.createPath_sync(path+"/c2","",CreateMode.PERSISTENT)
//      sample.getSemaphore().await()
//    }catch{case e=>
//      println(e.getMessage)
//    }
//
//  }
//  def start(): Unit ={
//
//    val zk=new ZooKeeper("localhost:21810",5000,new Watcher {
//      override def process(event: WatchedEvent): Unit = {
//        println(event.getPath+event.getType)
//        (event.getPath,event.getType) match {
//          case ("/testRootPath", EventType.NodeDataChanged) =>
//            println("node Data Channged")
//          case _ =>
//            println("Node Event:" + event.getState + "," + event.getPath + ",")
//        }
//      }
//    })
////    zk.create("/testRootPath","testRootData".getBytes,Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT)
//    zk.setData("/testRootPath","newTestRootData".getBytes,-1)
//    println(new String(zk.getData("/testRootPath",null,null)))
//    Thread.sleep(500000)
//
//  }
//}
//trait DataMonitorListener {
//  def exists(data: Array[Byte])
//
//  def closing(rc: Int)
//}
//class DataMonitor(zk:ZooKeeper,znode:String,chainedWatcher:Watcher,dead:Boolean,listener:DataMonitorListener,prevData:Array[Byte]) extends Watcher with StatCallback{
//  override def process(event: WatchedEvent): Unit = ???
//
//  override def processResult(rc: Int, path: String, ctx: scala.Any, stat: Stat): Unit = ???
//
//}
//
//
//class ZooKeeper_GetChildren_API_Sync_Usage(var zk:ZooKeeper,_semaphore:CountDownLatch=new CountDownLatch(1)) extends Watcher{
//  val connectedSemaphore=new CountDownLatch(1)
//  def getSemaphore()=this._semaphore
//  def createSession(connectString:String,sessionTimeout:Int,watcher:Watcher):ZooKeeper={
//    val zk=new ZooKeeper(connectString,sessionTimeout,watcher)
//    try{
//      connectedSemaphore.await()
//    }catch{case e=>
//    }
//    zk
//  }
//  def createPath_sync(path:String,data:String,createMode:CreateMode): Unit ={
//    if(zk == null) zk=this.createSession("localhost:21810",5000,this);
//    zk.create(path,data.getBytes,Ids.OPEN_ACL_UNSAFE,createMode)
//  }
//  def getChildren(path:String): Unit ={
//    if(zk == null) zk=this.createSession("localhost:21810",5000,this)
//    val _samphore_get_children=new CountDownLatch(1)
//    zk.getChildren(path,true,new Children2Callback {
//      override def processResult(rc: Int, path: String, ctx: scala.Any, children: util.List[String], stat: Stat): Unit = {
//        println("Get Children znode result:[Response code:"+rc+",param path:"+path+"ctx:"+ctx+",children:"+children+",stat:"+stat+"]")
//        _samphore_get_children.countDown()
//      }
//    },null)
//    _samphore_get_children.await()
//  }
//  override def process(event: WatchedEvent): Unit = {
//    println("Receive watched event:"+event)
//    if(KeeperState.SyncConnected==event.getState )
//      if(EventType.None==event.getType&& null==event.getPath) {
//        connectedSemaphore.countDown()
//      }else if(event.getType == EventType.NodeChildrenChanged){
//        try{
//          this.getChildren(event.getPath)
//          _semaphore.countDown()
//        }catch{case e=>
//          println(e.getMessage)
//        }
//      }
//  }
//}
object ZookeeperStart extends App{
  new Thread(new Runnable {
    override def run(): Unit = {
      val zkTest = new ZkTask(None)
      zkTest.deletePath("/t1")
      zkTest.createPath("/t1", "t1", CreateMode.PERSISTENT)

      zkTest.changePathValue("/t1", "t1_1")
      val it=zkTest.zk.get.getChildren("/", true).iterator()

      while(it.hasNext) println(it.next().toString)
    }
  }).start()
  while(true) Thread.sleep(10000)
}
class ZkTask(var zk:Option[ZooKeeper]) extends Watcher{

  private[this] def createZooKeeper(connectString:String): Option[ZooKeeper] ={
    if(zk.isEmpty) zk=Some(new ZooKeeper(connectString,500000,this))
    zk
  }
  def changePathValue(path:String,value:String): Unit ={
    if(zk.isEmpty) zk=createZooKeeper("localhost:21810")
    zk.get.setData(path,value.getBytes,-1)
  }
  def createPath(path:String,data:String,createMode:CreateMode): Unit ={
    if(zk.isEmpty) zk=createZooKeeper("localhost:21810")
    zk.get.create(path,data.getBytes,Ids.OPEN_ACL_UNSAFE,createMode)
  }
  def deletePath(path:String): Unit ={
    if(zk.isEmpty) zk=createZooKeeper("localhost:21810")
    zk.get.delete(path,-1)
  }
  override def process(event: WatchedEvent): Unit = {
    println("Receive Watched Event:"+event)
    if(event.getPath.equals("/t1")){
      event.getType match{
        case EventType.NodeDataChanged=>println(new String(zk.get.getData("/t1",this,null)))
        case EventType.NodeDeleted=>println("delete Event:"+event)
        case EventType.NodeCreated=>println("Children Node Created:"+event)
        case EventType.None=>println("None Node Event:"+event)
      }
    }

  }

}
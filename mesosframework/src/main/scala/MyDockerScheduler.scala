import java.util
import java.util.concurrent.atomic.AtomicInteger

import com.google.protobuf.ByteString
import org.apache.mesos.Protos._
import org.apache.mesos.{MesosSchedulerDriver, Protos, SchedulerDriver, Scheduler}
import scala.collection.JavaConverters._

/**
 * Created by Administrator on 2015/1/14.
 */

class MyDockerScheduler(imageName:String,desireInstances:Int) extends Scheduler{
  private[this] var isFirst=true;
  private[this] val pendingInstances=new util.LinkedList[String]()
  private[this] val runningInstances=new util.LinkedList[String]()
  private[this] val taskIDgenerator=new AtomicInteger()

  override def registered(driver: SchedulerDriver, frameworkId: FrameworkID, masterInfo: MasterInfo): Unit = {
    println("registered() master="+masterInfo.getIp()+":"+masterInfo.getPort+", framework="+ frameworkId)
  }

  override def offerRescinded(driver: SchedulerDriver, offerId: OfferID): Unit = ???

  override def disconnected(driver: SchedulerDriver): Unit = ???

  override def reregistered(driver: SchedulerDriver, masterInfo: MasterInfo): Unit = {
    println("reregistered() master="+masterInfo.getIp()+":"+masterInfo.getPort)
  }

  override def slaveLost(driver: SchedulerDriver, slaveId: SlaveID): Unit = {
    println("slaveLost() slaveId="+slaveId)
  }

  override def error(driver: SchedulerDriver, message: String): Unit = {
    println("error() error="+message)
  }

  override def statusUpdate(driver: SchedulerDriver, status: TaskStatus): Unit = {
    val taskId=status.getTaskId.getValue
    println("status="+status)
    status.getState match{
      case TaskState.TASK_RUNNING=>{
        pendingInstances.remove(taskId)
        runningInstances.add(taskId)
      }
      case TaskState.TASK_FAILED=>{
        driver.stop()
      }
      case TaskState.TASK_FINISHED=>{
        pendingInstances.remove(taskId)
        runningInstances.remove(taskId)
        driver.stop()
      }
      case s=>
    }
  }

  override def frameworkMessage(driver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, data: Array[Byte]): Unit = {
    println("frameworkMessage() executorId="+executorId.getValue+",slaveId="+slaveId.getValue+",data="+new String(data))
  }

  override def resourceOffers(driver: SchedulerDriver, offers: util.List[Offer]): Unit = {
   println("\n\nresourceOffers{"+offers+"}\n\n")
    if(isFirst) {
     val soffers = offers.asScala
     soffers.foreach { offer =>
       var tasks = if (runningInstances.size + pendingInstances.size < desireInstances) {
         val taskId = Protos.TaskID.newBuilder().setValue(Integer.toString(taskIDgenerator.incrementAndGet())).build()
         val dockerInfoBuilder = Protos.ContainerInfo.DockerInfo.newBuilder()
         dockerInfoBuilder.setImage(imageName)
         dockerInfoBuilder.setNetwork(Protos.ContainerInfo.DockerInfo.Network.BRIDGE)
         val containerInfoBuilder = Protos.ContainerInfo.newBuilder()
         containerInfoBuilder.setType(Protos.ContainerInfo.Type.DOCKER)
         containerInfoBuilder.setDocker(dockerInfoBuilder.build())
         val task = Protos.TaskInfo.newBuilder()
           .setName("task" + taskId.getValue)
           .setTaskId(taskId)
           .setSlaveId(offer.getSlaveId)
           .addResources(Protos.Resource
            .newBuilder()
            .setName("cpus")
            .setType(Protos.Value.Type.SCALAR)
            .setScalar(Protos.Value.Scalar.newBuilder().setValue(1)))
           .addResources(Protos.Resource
            .newBuilder()
            .setName("mem")
            .setType(Protos.Value.Type.SCALAR)
            .setScalar(Protos.Value.Scalar.newBuilder().setValue(128)))
           .setContainer(containerInfoBuilder)
           .setCommand(Protos.CommandInfo.newBuilder().setShell(true).setValue("while true;do echo 'hello world';sleep 3;done"))
           .build()
         val filters = Protos.Filters.newBuilder().setRefuseSeconds(20).build()
         val tasks = new util.ArrayList[TaskInfo]()
         tasks.add(task)
         driver.launchTasks(offer.getId, tasks, filters)
       }
       isFirst=false
     }
   }
  }

  override def executorLost(driver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, status: Int): Unit = {

  }
}
object OtherMesosExecutor{
  def main(args:Array[String]): Unit ={
    val args:Array[String]=Array("zk://10.9.52.31:2181/mesos")
    require(args!=null&&args.length>0)
    val frameworkBuilder=Protos.FrameworkInfo.newBuilder().setFailoverTimeout(120000).setUser("").setName("commandDockerLauncherFramework")
    val scheduler=new OtherMesosScheduler
    val driver=new MesosSchedulerDriver(scheduler,frameworkBuilder.build(),args(0))
    val status=if(driver.run() ==Protos.Status.DRIVER_STOPPED )0 else 1
    driver.stop()
    System.exit(status)
  }
}
class OtherMesosScheduler extends Scheduler{
  private[this] var isFirst=true;
  private[this] val taskIdCounter:AtomicInteger=new AtomicInteger()
  private[this] val excutorHello:Protos.ExecutorInfo={
    val commandHello="echo hello"
    val commandInfo=Protos.CommandInfo.newBuilder().setValue(commandHello).build()
    Protos.ExecutorInfo
      .newBuilder()
      .setExecutorId(Protos.ExecutorID.newBuilder().setValue("OtherExecutor"))
      .setCommand(commandInfo)
      .setSource("hello")
      .build()
  }
  override def registered(driver: SchedulerDriver, frameworkId: FrameworkID, masterInfo: MasterInfo): Unit = {

    println("registered<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
    println(masterInfo)
    println(frameworkId)
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
  }

  override def offerRescinded(driver: SchedulerDriver, offerId: OfferID): Unit = {

    println("offerRescinded<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
    println(offerId)
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
  }

  override def disconnected(driver: SchedulerDriver): Unit = {

  }

  override def reregistered(driver: SchedulerDriver, masterInfo: MasterInfo): Unit = {
    println("reregistered<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
      println(masterInfo)
        println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
  }

  override def slaveLost(driver: SchedulerDriver, slaveId: SlaveID): Unit = {
        println("slaveLost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
      println(slaveId)
        println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")

  }

  override def error(driver: SchedulerDriver, message: String): Unit = {
    println("error<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
    println(message)
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")


  }

  override def statusUpdate(driver: SchedulerDriver, status: TaskStatus): Unit = {
    println("status Update:"+status)
    println("getHealthy:"+status.getHealthy+",")
  }

  override def frameworkMessage(driver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, data: Array[Byte]): Unit = {
    println("frameworkMessage<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")

    println(executorId)
    println(slaveId)
    println(new String(data))
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
  }

  override def resourceOffers(driver: SchedulerDriver, offers: util.List[Offer]): Unit = {
    if(isFirst) {
      val offerIds = new util.ArrayList[Protos.OfferID]()
      val tasks = new util.ArrayList[Protos.TaskInfo]()
      offers.asScala.foreach { offer =>
        val taskId = Protos.TaskID.newBuilder().setValue(Integer.toString(taskIdCounter.incrementAndGet())).build()
        val task = Protos.TaskInfo
          .newBuilder()
          .setName("task" + taskId)
          .setTaskId(taskId)
          .setSlaveId(offer.getSlaveId)
          .addResources(
            Protos.Resource
              .newBuilder()
              .setName("cpus")
              .setType(Protos.Value.Type.SCALAR)
              .setScalar(Protos.Value.Scalar.newBuilder().setValue(1)))
          .addResources(
            Protos.Resource
              .newBuilder().setName("mem")
              .setType(Protos.Value.Type.SCALAR)
              .setScalar(Protos.Value.Scalar.newBuilder().setValue(128)))
          .setData(ByteString.copyFromUtf8("batman"))
          .setCommand(Protos.CommandInfo.newBuilder().setShell(true).setValue("docker run -d java:7 /bin/bash -c 'while true;do echo hello world;sleep 1;done'"))
          //          .setExecutor(Protos.ExecutorInfo.newBuilder(excutorHello))
          .build()
        tasks.add(task)
        offerIds.add(offer.getId)
      }

      driver.launchTasks(offerIds, tasks)
      println(taskIdCounter.get())
      isFirst = false
    }
  }

  override def executorLost(driver: SchedulerDriver, executorId: ExecutorID, slaveId: SlaveID, status: Int): Unit = {
    println("executorLost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
    println(executorId)
    println(slaveId)
    println(status)
    println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
  }
}
import org.apache.mesos._
import org.apache.mesos.Protos._

/**
 * Created by Administrator on 2015/1/14.
 */
object ExecutorDocker {
  def main(args:Array[String]): Unit ={
    require(args!=null && args.length>=3,"mesosmaster,dockerImage,instance")
    val frameworkBuilder=FrameworkInfo.newBuilder()
      .setName("SimpleFW")
      .setUser("")
      .setFailoverTimeout(100)
    val (mesosMaster,dockerImage,taskInstance)=(args(0),args(1),args(2).toInt)
    val dockerScheduler=new MyDockerScheduler(dockerImage,taskInstance)

    frameworkBuilder.setPrincipal("test-framework-java")
    val driver=new MesosSchedulerDriver(dockerScheduler,frameworkBuilder.build(),mesosMaster)
    val status= driver.run() match {
      case Status.DRIVER_STOPPED => 0
      case _ => 1
    }
    driver.stop()
    println("status======"+status)
    Thread.sleep(100000)
  }
}

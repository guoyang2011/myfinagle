import org.im4java.core.{GMOperation, ConvertCmd, IdentifyCmd, IMOperation}
import org.im4java.process.{ArrayListOutputConsumer, ArrayListConsumer}

/**
 * Created by yangguo on 14-11-10.
 */
object Start extends App{
  val cvtCmd=new ConvertCmd(true)
  def  getWidth(imgPath:String):Int={
    try{
      val op=new IMOperation
      op.format("%w")
      op.addImage(1)
      val identifyCmd=new IdentifyCmd(true)
      val output=new ArrayListOutputConsumer()
      identifyCmd.setOutputConsumer(output)
      identifyCmd.run(op,imgPath)
      val cmdOutput=output.getOutput
      assert(cmdOutput.size()==1)
      Integer.parseInt(cmdOutput.get(0))
    }catch{
      case e=>0
    }
  }
  def resizeImage(src:String,dst:String,size:Array[Int]): Unit ={
    val op=new GMOperation
    op.addImage(src)
    op.affine(0.5)
    op.appendHorizontally()
    op.depth(10)
    println(size(0)+""+size(1))
    op.resize(size(0),size(1))
    op.addImage(dst)
//    val cCmd=new ConvertCmd(true)
    cvtCmd.run(op)
  }

  val src="/Users/yangguo/gmdoc/example/test.png"
  val dst="/Users/yangguo/gmdoc/example/test.java.png"
  println("size="+getWidth(src))
  resizeImage(src,dst,Array(800,600))

}

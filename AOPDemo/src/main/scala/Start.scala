/**
 * Created by yangguo on 14-11-5.
 */
object Start extends App{
  def func[T](f:String=>T):T={
   val str="hello world"
   f(str)
  }
  println(func{ss=>ss+""+","+ss})
  println("???")
}

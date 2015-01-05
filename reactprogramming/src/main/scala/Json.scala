
/**
 * Created by yangguo on 14-12-15.
 */

object JsonTest {
  def main(args:Array[String]): Unit ={
    val g: PartialFunction[List[Int], String] = {
      case Nil =>"one"
      case x :: rest =>
        rest match {
          case Nil => " two "
          case y::z if z.isInstanceOf[List[Int]]=> {
            if (z.isInstanceOf[List[Int]] ){println("sdasdas")
            }
            println(z.getClass.getName);
            "t"
          }
        }
    }
    println(g.isDefinedAt(List(1,2,3,4)))
    println(g(List(1,2,3)))
  }
  def createJsonObj={
    JObj(Map(
      "firstName"->JString("John"),
      "lastName"->JString("smith"),
      "address"->JObj(Map(
        "streetAddress"->JString("长虹"),
         "state"->JString("SC"),
        "postalCode"->JDouble(10021)
      )),
      "phoneNumber"->JSeq(List(
        JObj(Map("type"->JString("home"),"number"->JString("212 555-1234"))),
        JObj(Map("type"->JString("fax"),"number"->JString("646 555-4567")))
        ))
    ))
  }
  def decoder(json:JSON):String=json match{
    case JSeq(elems)=>
      "["+(elems map decoder mkString ",")+"]"
    case JObj(binds)=>
      val assocs=binds map{
        case (key,value)=>"\""+key+"\": "+ decoder(value)
      }
      "{"+(assocs mkString ",")+"}"
    case JDouble(num)=>num.toString
    case JString(str)=>"\""+str+"\""
    case JBool(b)=>b.toString
    case JNull=>"null"
  }
}
abstract class JSON
case class JSeq(elems:List[JSON]) extends JSON
case class JString(str:String) extends JSON
case class JDouble(num:Double) extends JSON
case class JBool(b:Boolean) extends JSON
case class JObj(bindings:Map[String,JSON]) extends JSON
case object JNull extends JSON
trait dd {
  val value:String
  val length:Int
  def getValue=value
  val f:PartialFunction[String,String]
}

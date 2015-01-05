package cn.changhong.finagle.http

/**
 * Created by yangguo on 14-10-27.
 */
object RegexTest {
  val dateP1="""(\d\d\d\d)-(\d\d)-(\d\d)""".r
//  val some=Some("") getOrElse(new InterruptedException("")) match{
//    case ""=>""
//  }
  val copyright:String=dateP1 findFirstIn "Date of this document:2011-07-15" match {
    case Some(dateP1(year, month, day)) => "Cpyright " + year
    case None => "No copyright"
  }
  val copyright1:Option[String]=for{
    dateP1(year,month,day)<-dateP1 findFirstIn "modified time:2011-09-14"
  }yield year
  def getYear(text:String):Iterator[String]={
    for{
      dateP1(year,_,_)<-dateP1 findAllIn  "Last modified time:2011-07-11"
    } yield year
  }
  def getFirstDay(text:String):Option[String]={
    for{
      m <- dateP1 findFirstMatchIn text
    } yield m group "day"
  }
  for{
    words <- """\w+""".r findAllMatchIn "A simple example."
  } yield words start
  val regx="""((\d\d\d\d)-(\d\d)-(\d\d))""".r
//  regx findFirstMatchIn "2011-30-34" match{
//    case Some(m)=>m.
//  }


  object Grant_Type {
    val read_user_blog = 0x01
    val read_user_checkin = 0x01 << 1
    val read_user_feed = 0x01 << 2
    val read_user_guestbook = 0x01 << 3
    val read_user_invitation = 0x01 << 4
  }
  val listReg=List("""/accountservice.*""".r,"""/familyservice.*""".r)
  def main(args:Array[String]): Unit ={
    listReg foreach{reg=>
      "/accountservice/" match{
        case reg(_*)=>println("match /accountservice")
        case _=>
      }
    }
  }
}

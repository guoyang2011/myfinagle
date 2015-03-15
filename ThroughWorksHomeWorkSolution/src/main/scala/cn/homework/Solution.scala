package cn.homework

import Util._
/**
 * Created by yangguo on 15-3-14.
 */
object Util{
  type Graph[Key]=Map[Key, List[(Int, Key)]]
  type DailyPlan=List[(String,(String,Int))]
}
class SolutionOne[Key](lookup:Graph[Key]) {
  /**
   * one to five solution
   * @param path
   * @param cost
   * @throws RuntimeException
   * @return
   */
  @throws[RuntimeException]
  def distance( path: List[Key], cost: Int): Int = {
    path match {
      case from :: to :: next_path =>
        val dist = lookup(from).filter(_._2 == to)
        if (dist.size > 0) {
          val tempCost = dist.head._1 + cost
          if (next_path.isEmpty) tempCost
          else distance(to :: next_path, tempCost)
        } else throw new RuntimeException("NO SUCH ROUTE")
    }
  }

  /**
   *
   * @param from where is start
   * @param to where is destination
   * @param maxIterations max iterations number
   * @param maxDistance max distance cost
   * @return
   */
  def searchRouters(from: Key, to: Key, maxIterations: Int = 12, maxDistance: Int = Int.MaxValue): List[(Int, List[(Int, Key)])] = {
    var list: List[(Int, List[(Int, Key)])] = List()
    def doIterator(key: Key, prePath: List[(Int, Key)], numIterations: Int, distance: Int = 0): Unit = {
      if (numIterations < maxIterations && distance < maxDistance) {
        val next = lookup(key)
        next.filter(_._2.equals(to)).foreach { dist =>
          list = (numIterations, dist :: prePath) :: list
        }
        next.foreach(n_next => doIterator(n_next._2, n_next :: prePath, numIterations + 1))
      }
    }
    doIterator(from, List(), 1)
    list
  }


}
object SolutionOne{
  @throws[Exception]
  def apply(routers: String) = {
    var resource: collection.mutable.Map[String, List[(Int, String)]] = collection.mutable.Map()
    for (router <- routers.split(",").map(n => n.trim)) {
      val from = router.charAt(0).toString
      val to = router.charAt(1).toString
      val distance = router.substring(2).toInt
      resource.get(from) match {
        case Some(list) if !list.isEmpty => resource += (from -> ((distance -> to) :: list))
        case _ => resource += (from -> List((distance -> to)))
      }
    }
    new SolutionOne[String](resource.toMap)
  }
  def print={

  }
}

object SolutionTwo{
  def apply()={
    val input="Writing Fast Tests Against Enterprise Rails 60min\nOverdoing it in Python 45min\nLua for the Masses 30min\nRuby Errors from Mismatched Gem Versions 45min\nCommon Ruby Errors 45min\nRails for Python Developers lightning\nCommunicating Over Distance 60min\nAccounting-Driven Development 45min\nWoah 30min\nSit Down and Write 30min\nPair Programming vs Noise 45min\nRails Magic 60min\nRuby on Rails: Why We Should Move On 60min\nClojure Ate Scala (on my project) 45min\nProgramming in the Boondocks of Seattle 30min\nRuby vs. Clojure for Back-End Development 30min\nRuby on Rails Legacy App Maintenance 60min\nA World Without HackerNews 30min\nUser Interface CSS in Rails Apps 30min"
    val sample=input.split("\n").map{t=>
      val line=t.trim
      val splitIndex=line.lastIndexOf(" ")
      val minute=line.substring(splitIndex+1) match{
        case "lightning"=>5
        case min=>min.substring(0,min.indexOf('m')).toInt
      }
      ( line->minute)
    }.toMap

    def total(morning:Int=3*60,afternoon:Int=3*60,sample:List[(String,Int)],include:List[String],exclude:List[String]): Unit ={
      sample()
    }
  }
}
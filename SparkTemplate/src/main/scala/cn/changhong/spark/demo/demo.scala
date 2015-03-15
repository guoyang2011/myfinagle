package cn.changhong.spark.demo

/**
 * Created by yangguo on 15-2-13.
 *
 */
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

object demo{
  def main(args:Array[String]): Unit ={
    val logFile="hdfs://localhost:10000/spam.data"//"/Users/yangguo/hadoop/spark/examples/src/main/scala/org/apache/spark/examples/CassandraTest.scala"
    val conf=new SparkConf().setAppName("Simple Application").setMaster("spark://localhost:7077")
    val sc=new SparkContext("local","wordcount")
    val logData=sc.textFile(logFile,2).cache()
    val numAs=logData.filter(line=>line.contains("a")).count()
    val numBs=logData.filter(line=>line.contains("b")).count()
    val lineN=logData.count()

    logData.map{s=>s.map(c=>(c->1))}.flatMap(l=>l).reduceByKey(_+_).sortByKey(true)
    println(s"Total lines : $lineN,Lines with a: $numAs,Lines with b: $numBs")
    val conf1=new SparkConf().setAppName("Simple Application").setMaster("spark://localhost:7077")
    val sc1=new SparkContext("local","wordcount")
    val logData1=sc1.textFile(logFile,2).cache()
  }
}

import com.mongodb.MongoClient

/**
 * Created by yangguo on 14-12-3.
 */
object MongodbStart {
  def start: Unit ={
    val mongoClient=new MongoClient("localhost",27017)
    val db=mongoClient.getDB("test")
    val coll=db.getCollection("mycol")
  }
}

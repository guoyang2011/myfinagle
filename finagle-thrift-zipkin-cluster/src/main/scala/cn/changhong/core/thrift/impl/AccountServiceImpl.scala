package cn.changhong.core.thrift.impl

import cn.changhong.core.thrift.{User, AccountService}
import com.twitter.util.Future

import scala.collection.Map

/**
 * Created by Administrator on 2014/12/29.
 */



class AccountServiceImpl extends AccountService.FutureIface{
  override def create(user: User): Future[Boolean] = {
    Future.value(true)
  }

  override def get(id: Long): Future[User] = {
    Future.value{
      new User.Immutable(id,"username","iphone","email","passwd")
    }
  }

  override def list(): Future[Seq[User]] = {
    val res=(1 to 10).map{index=>
      new User.Immutable(index,"username","iphone","email","passwd")
    }
    Future.value(res)
  }

  override def map(): Future[Map[String, User]] = {
    val res:scala.collection.mutable.Map[String,User] = scala.collection.mutable.Map()
    (1 to 10).foreach{index=>
      res+=(index.toString->new User.Immutable(index,"username","iphone","email","passwd"))
    }
    Future.value(res)
  }

  override def creates(users: Seq[User]): Future[Seq[Boolean]] = {
    val res=users.map{user=>
      if(user.id%2==0) true
      else false
    }
    println(res.map(_.toString).mkString(","))
    Future.value(res)
  }
}

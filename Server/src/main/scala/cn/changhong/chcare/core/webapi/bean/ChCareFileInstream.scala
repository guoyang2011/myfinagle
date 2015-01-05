package cn.changhong.chcare.core.webapi.bean

import java.util.Date

//package cn.changhong.chcare.core.webapi.u
//

/**
 * Created by yangguo on 14-10-20.
 */
 case class User(ID:Int,Name:String,Passwd:String,Gender:String,Birthday:Date,Height:Double,Weight:Double,Area:String,NickName:String) extends Serializable

package com.changhong.orm.Tables
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  import scala.slick.collection.heterogenous._
  import scala.slick.collection.heterogenous.syntax._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Friends.ddl ++ Groupadmin.ddl ++ Groups.ddl ++ Groupuser.ddl ++ Ofextcomponentconf.ddl ++ Ofgroup.ddl ++ Ofgroupprop.ddl ++ Ofgroupuser.ddl ++ Ofid.ddl ++ Ofmucaffiliation.ddl ++ Ofmucconversationlog.ddl ++ Ofmucmember.ddl ++ Ofmucroom.ddl ++ Ofmucroomprop.ddl ++ Ofmucservice.ddl ++ Ofmucserviceprop.ddl ++ Ofoffline.ddl ++ Ofpresence.ddl ++ Ofprivacylist.ddl ++ Ofprivate.ddl ++ Ofproperty.ddl ++ Ofpubsubaffiliation.ddl ++ Ofpubsubdefaultconf.ddl ++ Ofpubsubitem.ddl ++ Ofpubsubnode.ddl ++ Ofpubsubnodegroups.ddl ++ Ofpubsubnodejids.ddl ++ Ofpubsubsubscription.ddl ++ Ofremoteserverconf.ddl ++ Ofroster.ddl ++ Ofrostergroups.ddl ++ Ofsaslauthorized.ddl ++ Ofsecurityauditlog.ddl ++ Ofuser.ddl ++ Ofuserflag.ddl ++ Ofuserprop.ddl ++ Ofvcard.ddl ++ Ofversion.ddl ++ User.ddl
  
  /** Entity class storing rows of table Friends
   *  @param userid Database column userid DBType(INT)
   *  @param friendid Database column friendid DBType(INT)
   *  @param nick Database column nick DBType(VARCHAR), Length(30,true) */
  case class FriendsRow(userid: Int, friendid: Int, nick: String)
  /** GetResult implicit for fetching FriendsRow objects using plain SQL queries */
  implicit def GetResultFriendsRow(implicit e0: GR[Int], e1: GR[String]): GR[FriendsRow] = GR{
    prs => import prs._
    FriendsRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table friends. Objects of this class serve as prototypes for rows in queries. */
  class Friends(_tableTag: Tag) extends Table[FriendsRow](_tableTag, "friends") {
    def * = (userid, friendid, nick) <> (FriendsRow.tupled, FriendsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (userid.?, friendid.?, nick.?).shaped.<>({r=>import r._; _1.map(_=> FriendsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column userid DBType(INT) */
    val userid: Column[Int] = column[Int]("userid")
    /** Database column friendid DBType(INT) */
    val friendid: Column[Int] = column[Int]("friendid")
    /** Database column nick DBType(VARCHAR), Length(30,true) */
    val nick: Column[String] = column[String]("nick", O.Length(30,varying=true))
    
    /** Primary key of Friends (database name friends_PK) */
    val pk = primaryKey("friends_PK", (userid, friendid))
  }
  /** Collection-like TableQuery object for table Friends */
  lazy val Friends = new TableQuery(tag => new Friends(tag))
  
  /** Entity class storing rows of table Groupadmin
   *  @param groupid Database column groupid DBType(INT)
   *  @param userid Database column userid DBType(INT) */
  case class GroupadminRow(groupid: Int, userid: Int)
  /** GetResult implicit for fetching GroupadminRow objects using plain SQL queries */
  implicit def GetResultGroupadminRow(implicit e0: GR[Int]): GR[GroupadminRow] = GR{
    prs => import prs._
    GroupadminRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table groupadmin. Objects of this class serve as prototypes for rows in queries. */
  class Groupadmin(_tableTag: Tag) extends Table[GroupadminRow](_tableTag, "groupadmin") {
    def * = (groupid, userid) <> (GroupadminRow.tupled, GroupadminRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (groupid.?, userid.?).shaped.<>({r=>import r._; _1.map(_=> GroupadminRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column groupid DBType(INT) */
    val groupid: Column[Int] = column[Int]("groupid")
    /** Database column userid DBType(INT) */
    val userid: Column[Int] = column[Int]("userid")
    
    /** Primary key of Groupadmin (database name groupadmin_PK) */
    val pk = primaryKey("groupadmin_PK", (groupid, userid))
  }
  /** Collection-like TableQuery object for table Groupadmin */
  lazy val Groupadmin = new TableQuery(tag => new Groupadmin(tag))
  
  /** Entity class storing rows of table Groups
   *  @param groupid Database column groupid DBType(INT), AutoInc, PrimaryKey
   *  @param groupname Database column groupname DBType(VARCHAR), Length(30,true)
   *  @param description Database column description DBType(VARCHAR), Length(255,true), Default(None)
   *  @param creatorid Database column creatorid DBType(INT)
   *  @param createdtime Database column createdtime DBType(DATETIME) */
  case class GroupsRow(groupid: Int, groupname: String, description: Option[String] = None, creatorid: Int, createdtime: java.sql.Timestamp)
  /** GetResult implicit for fetching GroupsRow objects using plain SQL queries */
  implicit def GetResultGroupsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[GroupsRow] = GR{
    prs => import prs._
    GroupsRow.tupled((<<[Int], <<[String], <<?[String], <<[Int], <<[java.sql.Timestamp]))
  }
  /** Table description of table groups. Objects of this class serve as prototypes for rows in queries. */
  class Groups(_tableTag: Tag) extends Table[GroupsRow](_tableTag, "groups") {
    def * = (groupid, groupname, description, creatorid, createdtime) <> (GroupsRow.tupled, GroupsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (groupid.?, groupname.?, description, creatorid.?, createdtime.?).shaped.<>({r=>import r._; _1.map(_=> GroupsRow.tupled((_1.get, _2.get, _3, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column groupid DBType(INT), AutoInc, PrimaryKey */
    val groupid: Column[Int] = column[Int]("groupid", O.AutoInc, O.PrimaryKey)
    /** Database column groupname DBType(VARCHAR), Length(30,true) */
    val groupname: Column[String] = column[String]("groupname", O.Length(30,varying=true))
    /** Database column description DBType(VARCHAR), Length(255,true), Default(None) */
    val description: Column[Option[String]] = column[Option[String]]("description", O.Length(255,varying=true), O.Default(None))
    /** Database column creatorid DBType(INT) */
    val creatorid: Column[Int] = column[Int]("creatorid")
    /** Database column createdtime DBType(DATETIME) */
    val createdtime: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("createdtime")
  }
  /** Collection-like TableQuery object for table Groups */
  lazy val Groups = new TableQuery(tag => new Groups(tag))
  
  /** Entity class storing rows of table Groupuser
   *  @param groupid Database column groupid DBType(INT)
   *  @param userid Database column userid DBType(INT)
   *  @param nick Database column nick DBType(VARCHAR), Length(30,true) */
  case class GroupuserRow(groupid: Int, userid: Int, nick: String)
  /** GetResult implicit for fetching GroupuserRow objects using plain SQL queries */
  implicit def GetResultGroupuserRow(implicit e0: GR[Int], e1: GR[String]): GR[GroupuserRow] = GR{
    prs => import prs._
    GroupuserRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table groupuser. Objects of this class serve as prototypes for rows in queries. */
  class Groupuser(_tableTag: Tag) extends Table[GroupuserRow](_tableTag, "groupuser") {
    def * = (groupid, userid, nick) <> (GroupuserRow.tupled, GroupuserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (groupid.?, userid.?, nick.?).shaped.<>({r=>import r._; _1.map(_=> GroupuserRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column groupid DBType(INT) */
    val groupid: Column[Int] = column[Int]("groupid")
    /** Database column userid DBType(INT) */
    val userid: Column[Int] = column[Int]("userid")
    /** Database column nick DBType(VARCHAR), Length(30,true) */
    val nick: Column[String] = column[String]("nick", O.Length(30,varying=true))
    
    /** Primary key of Groupuser (database name groupuser_PK) */
    val pk = primaryKey("groupuser_PK", (groupid, userid))
  }
  /** Collection-like TableQuery object for table Groupuser */
  lazy val Groupuser = new TableQuery(tag => new Groupuser(tag))
  
  /** Entity class storing rows of table Ofextcomponentconf
   *  @param subdomain Database column subdomain DBType(VARCHAR), PrimaryKey, Length(255,true)
   *  @param wildcard Database column wildcard DBType(TINYINT)
   *  @param secret Database column secret DBType(VARCHAR), Length(255,true), Default(None)
   *  @param permission Database column permission DBType(VARCHAR), Length(10,true) */
  case class OfextcomponentconfRow(subdomain: String, wildcard: Byte, secret: Option[String] = None, permission: String)
  /** GetResult implicit for fetching OfextcomponentconfRow objects using plain SQL queries */
  implicit def GetResultOfextcomponentconfRow(implicit e0: GR[String], e1: GR[Byte], e2: GR[Option[String]]): GR[OfextcomponentconfRow] = GR{
    prs => import prs._
    OfextcomponentconfRow.tupled((<<[String], <<[Byte], <<?[String], <<[String]))
  }
  /** Table description of table ofExtComponentConf. Objects of this class serve as prototypes for rows in queries. */
  class Ofextcomponentconf(_tableTag: Tag) extends Table[OfextcomponentconfRow](_tableTag, "ofExtComponentConf") {
    def * = (subdomain, wildcard, secret, permission) <> (OfextcomponentconfRow.tupled, OfextcomponentconfRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (subdomain.?, wildcard.?, secret, permission.?).shaped.<>({r=>import r._; _1.map(_=> OfextcomponentconfRow.tupled((_1.get, _2.get, _3, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column subdomain DBType(VARCHAR), PrimaryKey, Length(255,true) */
    val subdomain: Column[String] = column[String]("subdomain", O.PrimaryKey, O.Length(255,varying=true))
    /** Database column wildcard DBType(TINYINT) */
    val wildcard: Column[Byte] = column[Byte]("wildcard")
    /** Database column secret DBType(VARCHAR), Length(255,true), Default(None) */
    val secret: Column[Option[String]] = column[Option[String]]("secret", O.Length(255,varying=true), O.Default(None))
    /** Database column permission DBType(VARCHAR), Length(10,true) */
    val permission: Column[String] = column[String]("permission", O.Length(10,varying=true))
  }
  /** Collection-like TableQuery object for table Ofextcomponentconf */
  lazy val Ofextcomponentconf = new TableQuery(tag => new Ofextcomponentconf(tag))
  
  /** Entity class storing rows of table Ofgroup
   *  @param groupname Database column groupName DBType(VARCHAR), PrimaryKey, Length(50,true)
   *  @param description Database column description DBType(VARCHAR), Length(255,true), Default(None) */
  case class OfgroupRow(groupname: String, description: Option[String] = None)
  /** GetResult implicit for fetching OfgroupRow objects using plain SQL queries */
  implicit def GetResultOfgroupRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[OfgroupRow] = GR{
    prs => import prs._
    OfgroupRow.tupled((<<[String], <<?[String]))
  }
  /** Table description of table ofGroup. Objects of this class serve as prototypes for rows in queries. */
  class Ofgroup(_tableTag: Tag) extends Table[OfgroupRow](_tableTag, "ofGroup") {
    def * = (groupname, description) <> (OfgroupRow.tupled, OfgroupRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (groupname.?, description).shaped.<>({r=>import r._; _1.map(_=> OfgroupRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column groupName DBType(VARCHAR), PrimaryKey, Length(50,true) */
    val groupname: Column[String] = column[String]("groupName", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column description DBType(VARCHAR), Length(255,true), Default(None) */
    val description: Column[Option[String]] = column[Option[String]]("description", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Ofgroup */
  lazy val Ofgroup = new TableQuery(tag => new Ofgroup(tag))
  
  /** Entity class storing rows of table Ofgroupprop
   *  @param groupname Database column groupName DBType(VARCHAR), Length(50,true)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param propvalue Database column propValue DBType(TEXT), Length(65535,true) */
  case class OfgrouppropRow(groupname: String, name: String, propvalue: String)
  /** GetResult implicit for fetching OfgrouppropRow objects using plain SQL queries */
  implicit def GetResultOfgrouppropRow(implicit e0: GR[String]): GR[OfgrouppropRow] = GR{
    prs => import prs._
    OfgrouppropRow.tupled((<<[String], <<[String], <<[String]))
  }
  /** Table description of table ofGroupProp. Objects of this class serve as prototypes for rows in queries. */
  class Ofgroupprop(_tableTag: Tag) extends Table[OfgrouppropRow](_tableTag, "ofGroupProp") {
    def * = (groupname, name, propvalue) <> (OfgrouppropRow.tupled, OfgrouppropRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (groupname.?, name.?, propvalue.?).shaped.<>({r=>import r._; _1.map(_=> OfgrouppropRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column groupName DBType(VARCHAR), Length(50,true) */
    val groupname: Column[String] = column[String]("groupName", O.Length(50,varying=true))
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column propValue DBType(TEXT), Length(65535,true) */
    val propvalue: Column[String] = column[String]("propValue", O.Length(65535,varying=true))
    
    /** Primary key of Ofgroupprop (database name ofGroupProp_PK) */
    val pk = primaryKey("ofGroupProp_PK", (groupname, name))
  }
  /** Collection-like TableQuery object for table Ofgroupprop */
  lazy val Ofgroupprop = new TableQuery(tag => new Ofgroupprop(tag))
  
  /** Entity class storing rows of table Ofgroupuser
   *  @param groupname Database column groupName DBType(VARCHAR), Length(50,true)
   *  @param username Database column username DBType(VARCHAR), Length(100,true)
   *  @param administrator Database column administrator DBType(TINYINT) */
  case class OfgroupuserRow(groupname: String, username: String, administrator: Byte)
  /** GetResult implicit for fetching OfgroupuserRow objects using plain SQL queries */
  implicit def GetResultOfgroupuserRow(implicit e0: GR[String], e1: GR[Byte]): GR[OfgroupuserRow] = GR{
    prs => import prs._
    OfgroupuserRow.tupled((<<[String], <<[String], <<[Byte]))
  }
  /** Table description of table ofGroupUser. Objects of this class serve as prototypes for rows in queries. */
  class Ofgroupuser(_tableTag: Tag) extends Table[OfgroupuserRow](_tableTag, "ofGroupUser") {
    def * = (groupname, username, administrator) <> (OfgroupuserRow.tupled, OfgroupuserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (groupname.?, username.?, administrator.?).shaped.<>({r=>import r._; _1.map(_=> OfgroupuserRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column groupName DBType(VARCHAR), Length(50,true) */
    val groupname: Column[String] = column[String]("groupName", O.Length(50,varying=true))
    /** Database column username DBType(VARCHAR), Length(100,true) */
    val username: Column[String] = column[String]("username", O.Length(100,varying=true))
    /** Database column administrator DBType(TINYINT) */
    val administrator: Column[Byte] = column[Byte]("administrator")
    
    /** Primary key of Ofgroupuser (database name ofGroupUser_PK) */
    val pk = primaryKey("ofGroupUser_PK", (groupname, username, administrator))
  }
  /** Collection-like TableQuery object for table Ofgroupuser */
  lazy val Ofgroupuser = new TableQuery(tag => new Ofgroupuser(tag))
  
  /** Entity class storing rows of table Ofid
   *  @param idtype Database column idType DBType(INT), PrimaryKey
   *  @param id Database column id DBType(BIGINT) */
  case class OfidRow(idtype: Int, id: Long)
  /** GetResult implicit for fetching OfidRow objects using plain SQL queries */
  implicit def GetResultOfidRow(implicit e0: GR[Int], e1: GR[Long]): GR[OfidRow] = GR{
    prs => import prs._
    OfidRow.tupled((<<[Int], <<[Long]))
  }
  /** Table description of table ofID. Objects of this class serve as prototypes for rows in queries. */
  class Ofid(_tableTag: Tag) extends Table[OfidRow](_tableTag, "ofID") {
    def * = (idtype, id) <> (OfidRow.tupled, OfidRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (idtype.?, id.?).shaped.<>({r=>import r._; _1.map(_=> OfidRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column idType DBType(INT), PrimaryKey */
    val idtype: Column[Int] = column[Int]("idType", O.PrimaryKey)
    /** Database column id DBType(BIGINT) */
    val id: Column[Long] = column[Long]("id")
  }
  /** Collection-like TableQuery object for table Ofid */
  lazy val Ofid = new TableQuery(tag => new Ofid(tag))
  
  /** Entity class storing rows of table Ofmucaffiliation
   *  @param roomid Database column roomID DBType(BIGINT)
   *  @param jid Database column jid DBType(TEXT), Length(65535,true)
   *  @param affiliation Database column affiliation DBType(TINYINT) */
  case class OfmucaffiliationRow(roomid: Long, jid: String, affiliation: Byte)
  /** GetResult implicit for fetching OfmucaffiliationRow objects using plain SQL queries */
  implicit def GetResultOfmucaffiliationRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Byte]): GR[OfmucaffiliationRow] = GR{
    prs => import prs._
    OfmucaffiliationRow.tupled((<<[Long], <<[String], <<[Byte]))
  }
  /** Table description of table ofMucAffiliation. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucaffiliation(_tableTag: Tag) extends Table[OfmucaffiliationRow](_tableTag, "ofMucAffiliation") {
    def * = (roomid, jid, affiliation) <> (OfmucaffiliationRow.tupled, OfmucaffiliationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (roomid.?, jid.?, affiliation.?).shaped.<>({r=>import r._; _1.map(_=> OfmucaffiliationRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column roomID DBType(BIGINT) */
    val roomid: Column[Long] = column[Long]("roomID")
    /** Database column jid DBType(TEXT), Length(65535,true) */
    val jid: Column[String] = column[String]("jid", O.Length(65535,varying=true))
    /** Database column affiliation DBType(TINYINT) */
    val affiliation: Column[Byte] = column[Byte]("affiliation")
    
    /** Primary key of Ofmucaffiliation (database name ofMucAffiliation_PK) */
    val pk = primaryKey("ofMucAffiliation_PK", (roomid, jid))
  }
  /** Collection-like TableQuery object for table Ofmucaffiliation */
  lazy val Ofmucaffiliation = new TableQuery(tag => new Ofmucaffiliation(tag))
  
  /** Entity class storing rows of table Ofmucconversationlog
   *  @param roomid Database column roomID DBType(BIGINT)
   *  @param sender Database column sender DBType(TEXT), Length(65535,true)
   *  @param nickname Database column nickname DBType(VARCHAR), Length(255,true), Default(None)
   *  @param logtime Database column logTime DBType(CHAR), Length(15,false)
   *  @param subject Database column subject DBType(VARCHAR), Length(255,true), Default(None)
   *  @param body Database column body DBType(TEXT), Length(65535,true), Default(None) */
  case class OfmucconversationlogRow(roomid: Long, sender: String, nickname: Option[String] = None, logtime: String, subject: Option[String] = None, body: Option[String] = None)
  /** GetResult implicit for fetching OfmucconversationlogRow objects using plain SQL queries */
  implicit def GetResultOfmucconversationlogRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[OfmucconversationlogRow] = GR{
    prs => import prs._
    OfmucconversationlogRow.tupled((<<[Long], <<[String], <<?[String], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table ofMucConversationLog. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucconversationlog(_tableTag: Tag) extends Table[OfmucconversationlogRow](_tableTag, "ofMucConversationLog") {
    def * = (roomid, sender, nickname, logtime, subject, body) <> (OfmucconversationlogRow.tupled, OfmucconversationlogRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (roomid.?, sender.?, nickname, logtime.?, subject, body).shaped.<>({r=>import r._; _1.map(_=> OfmucconversationlogRow.tupled((_1.get, _2.get, _3, _4.get, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column roomID DBType(BIGINT) */
    val roomid: Column[Long] = column[Long]("roomID")
    /** Database column sender DBType(TEXT), Length(65535,true) */
    val sender: Column[String] = column[String]("sender", O.Length(65535,varying=true))
    /** Database column nickname DBType(VARCHAR), Length(255,true), Default(None) */
    val nickname: Column[Option[String]] = column[Option[String]]("nickname", O.Length(255,varying=true), O.Default(None))
    /** Database column logTime DBType(CHAR), Length(15,false) */
    val logtime: Column[String] = column[String]("logTime", O.Length(15,varying=false))
    /** Database column subject DBType(VARCHAR), Length(255,true), Default(None) */
    val subject: Column[Option[String]] = column[Option[String]]("subject", O.Length(255,varying=true), O.Default(None))
    /** Database column body DBType(TEXT), Length(65535,true), Default(None) */
    val body: Column[Option[String]] = column[Option[String]]("body", O.Length(65535,varying=true), O.Default(None))
    
    /** Index over (logtime) (database name ofMucConversationLog_time_idx) */
    val index1 = index("ofMucConversationLog_time_idx", logtime)
  }
  /** Collection-like TableQuery object for table Ofmucconversationlog */
  lazy val Ofmucconversationlog = new TableQuery(tag => new Ofmucconversationlog(tag))
  
  /** Entity class storing rows of table Ofmucmember
   *  @param roomid Database column roomID DBType(BIGINT)
   *  @param jid Database column jid DBType(TEXT), Length(65535,true)
   *  @param nickname Database column nickname DBType(VARCHAR), Length(255,true), Default(None)
   *  @param firstname Database column firstName DBType(VARCHAR), Length(100,true), Default(None)
   *  @param lastname Database column lastName DBType(VARCHAR), Length(100,true), Default(None)
   *  @param url Database column url DBType(VARCHAR), Length(100,true), Default(None)
   *  @param email Database column email DBType(VARCHAR), Length(100,true), Default(None)
   *  @param faqentry Database column faqentry DBType(VARCHAR), Length(100,true), Default(None) */
  case class OfmucmemberRow(roomid: Long, jid: String, nickname: Option[String] = None, firstname: Option[String] = None, lastname: Option[String] = None, url: Option[String] = None, email: Option[String] = None, faqentry: Option[String] = None)
  /** GetResult implicit for fetching OfmucmemberRow objects using plain SQL queries */
  implicit def GetResultOfmucmemberRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[OfmucmemberRow] = GR{
    prs => import prs._
    OfmucmemberRow.tupled((<<[Long], <<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table ofMucMember. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucmember(_tableTag: Tag) extends Table[OfmucmemberRow](_tableTag, "ofMucMember") {
    def * = (roomid, jid, nickname, firstname, lastname, url, email, faqentry) <> (OfmucmemberRow.tupled, OfmucmemberRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (roomid.?, jid.?, nickname, firstname, lastname, url, email, faqentry).shaped.<>({r=>import r._; _1.map(_=> OfmucmemberRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column roomID DBType(BIGINT) */
    val roomid: Column[Long] = column[Long]("roomID")
    /** Database column jid DBType(TEXT), Length(65535,true) */
    val jid: Column[String] = column[String]("jid", O.Length(65535,varying=true))
    /** Database column nickname DBType(VARCHAR), Length(255,true), Default(None) */
    val nickname: Column[Option[String]] = column[Option[String]]("nickname", O.Length(255,varying=true), O.Default(None))
    /** Database column firstName DBType(VARCHAR), Length(100,true), Default(None) */
    val firstname: Column[Option[String]] = column[Option[String]]("firstName", O.Length(100,varying=true), O.Default(None))
    /** Database column lastName DBType(VARCHAR), Length(100,true), Default(None) */
    val lastname: Column[Option[String]] = column[Option[String]]("lastName", O.Length(100,varying=true), O.Default(None))
    /** Database column url DBType(VARCHAR), Length(100,true), Default(None) */
    val url: Column[Option[String]] = column[Option[String]]("url", O.Length(100,varying=true), O.Default(None))
    /** Database column email DBType(VARCHAR), Length(100,true), Default(None) */
    val email: Column[Option[String]] = column[Option[String]]("email", O.Length(100,varying=true), O.Default(None))
    /** Database column faqentry DBType(VARCHAR), Length(100,true), Default(None) */
    val faqentry: Column[Option[String]] = column[Option[String]]("faqentry", O.Length(100,varying=true), O.Default(None))
    
    /** Primary key of Ofmucmember (database name ofMucMember_PK) */
    val pk = primaryKey("ofMucMember_PK", (roomid, jid))
  }
  /** Collection-like TableQuery object for table Ofmucmember */
  lazy val Ofmucmember = new TableQuery(tag => new Ofmucmember(tag))
  
  /** Row type of table Ofmucroom */
  type OfmucroomRow = HCons[Long,HCons[Long,HCons[String,HCons[String,HCons[String,HCons[String,HCons[Option[String],HCons[String,HCons[Option[String],HCons[Byte,HCons[Int,HCons[Byte,HCons[Byte,HCons[Byte,HCons[Byte,HCons[Option[String],HCons[Byte,HCons[Byte,HCons[Option[String],HCons[Byte,HCons[Byte,HCons[Byte,HCons[Byte,HNil]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for OfmucroomRow providing default values if available in the database schema. */
  def OfmucroomRow(serviceid: Long, roomid: Long, creationdate: String, modificationdate: String, name: String, naturalname: String, description: Option[String] = None, lockeddate: String, emptydate: Option[String] = None, canchangesubject: Byte, maxusers: Int, publicroom: Byte, moderated: Byte, membersonly: Byte, caninvite: Byte, roompassword: Option[String] = None, candiscoverjid: Byte, logenabled: Byte, subject: Option[String] = None, rolestobroadcast: Byte, usereservednick: Byte, canchangenick: Byte, canregister: Byte): OfmucroomRow = {
    serviceid :: roomid :: creationdate :: modificationdate :: name :: naturalname :: description :: lockeddate :: emptydate :: canchangesubject :: maxusers :: publicroom :: moderated :: membersonly :: caninvite :: roompassword :: candiscoverjid :: logenabled :: subject :: rolestobroadcast :: usereservednick :: canchangenick :: canregister :: HNil
  }
  /** GetResult implicit for fetching OfmucroomRow objects using plain SQL queries */
  implicit def GetResultOfmucroomRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Byte], e4: GR[Int]): GR[OfmucroomRow] = GR{
    prs => import prs._
    <<[Long] :: <<[Long] :: <<[String] :: <<[String] :: <<[String] :: <<[String] :: <<?[String] :: <<[String] :: <<?[String] :: <<[Byte] :: <<[Int] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: <<?[String] :: <<[Byte] :: <<[Byte] :: <<?[String] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: HNil
  }
  /** Table description of table ofMucRoom. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucroom(_tableTag: Tag) extends Table[OfmucroomRow](_tableTag, "ofMucRoom") {
    def * = serviceid :: roomid :: creationdate :: modificationdate :: name :: naturalname :: description :: lockeddate :: emptydate :: canchangesubject :: maxusers :: publicroom :: moderated :: membersonly :: caninvite :: roompassword :: candiscoverjid :: logenabled :: subject :: rolestobroadcast :: usereservednick :: canchangenick :: canregister :: HNil
    
    /** Database column serviceID DBType(BIGINT) */
    val serviceid: Column[Long] = column[Long]("serviceID")
    /** Database column roomID DBType(BIGINT) */
    val roomid: Column[Long] = column[Long]("roomID")
    /** Database column creationDate DBType(CHAR), Length(15,false) */
    val creationdate: Column[String] = column[String]("creationDate", O.Length(15,varying=false))
    /** Database column modificationDate DBType(CHAR), Length(15,false) */
    val modificationdate: Column[String] = column[String]("modificationDate", O.Length(15,varying=false))
    /** Database column name DBType(VARCHAR), Length(50,true) */
    val name: Column[String] = column[String]("name", O.Length(50,varying=true))
    /** Database column naturalName DBType(VARCHAR), Length(255,true) */
    val naturalname: Column[String] = column[String]("naturalName", O.Length(255,varying=true))
    /** Database column description DBType(VARCHAR), Length(255,true), Default(None) */
    val description: Column[Option[String]] = column[Option[String]]("description", O.Length(255,varying=true), O.Default(None))
    /** Database column lockedDate DBType(CHAR), Length(15,false) */
    val lockeddate: Column[String] = column[String]("lockedDate", O.Length(15,varying=false))
    /** Database column emptyDate DBType(CHAR), Length(15,false), Default(None) */
    val emptydate: Column[Option[String]] = column[Option[String]]("emptyDate", O.Length(15,varying=false), O.Default(None))
    /** Database column canChangeSubject DBType(TINYINT) */
    val canchangesubject: Column[Byte] = column[Byte]("canChangeSubject")
    /** Database column maxUsers DBType(INT) */
    val maxusers: Column[Int] = column[Int]("maxUsers")
    /** Database column publicRoom DBType(TINYINT) */
    val publicroom: Column[Byte] = column[Byte]("publicRoom")
    /** Database column moderated DBType(TINYINT) */
    val moderated: Column[Byte] = column[Byte]("moderated")
    /** Database column membersOnly DBType(TINYINT) */
    val membersonly: Column[Byte] = column[Byte]("membersOnly")
    /** Database column canInvite DBType(TINYINT) */
    val caninvite: Column[Byte] = column[Byte]("canInvite")
    /** Database column roomPassword DBType(VARCHAR), Length(50,true), Default(None) */
    val roompassword: Column[Option[String]] = column[Option[String]]("roomPassword", O.Length(50,varying=true), O.Default(None))
    /** Database column canDiscoverJID DBType(TINYINT) */
    val candiscoverjid: Column[Byte] = column[Byte]("canDiscoverJID")
    /** Database column logEnabled DBType(TINYINT) */
    val logenabled: Column[Byte] = column[Byte]("logEnabled")
    /** Database column subject DBType(VARCHAR), Length(100,true), Default(None) */
    val subject: Column[Option[String]] = column[Option[String]]("subject", O.Length(100,varying=true), O.Default(None))
    /** Database column rolesToBroadcast DBType(TINYINT) */
    val rolestobroadcast: Column[Byte] = column[Byte]("rolesToBroadcast")
    /** Database column useReservedNick DBType(TINYINT) */
    val usereservednick: Column[Byte] = column[Byte]("useReservedNick")
    /** Database column canChangeNick DBType(TINYINT) */
    val canchangenick: Column[Byte] = column[Byte]("canChangeNick")
    /** Database column canRegister DBType(TINYINT) */
    val canregister: Column[Byte] = column[Byte]("canRegister")
    
    /** Primary key of Ofmucroom (database name ofMucRoom_PK) */
    val pk = primaryKey("ofMucRoom_PK", serviceid :: name :: HNil)
    
    /** Index over (roomid) (database name ofMucRoom_roomid_idx) */
    val index1 = index("ofMucRoom_roomid_idx", roomid :: HNil)
    /** Index over (serviceid) (database name ofMucRoom_serviceid_idx) */
    val index2 = index("ofMucRoom_serviceid_idx", serviceid :: HNil)
  }
  /** Collection-like TableQuery object for table Ofmucroom */
  lazy val Ofmucroom = new TableQuery(tag => new Ofmucroom(tag))
  
  /** Entity class storing rows of table Ofmucroomprop
   *  @param roomid Database column roomID DBType(BIGINT)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param propvalue Database column propValue DBType(TEXT), Length(65535,true) */
  case class OfmucroompropRow(roomid: Long, name: String, propvalue: String)
  /** GetResult implicit for fetching OfmucroompropRow objects using plain SQL queries */
  implicit def GetResultOfmucroompropRow(implicit e0: GR[Long], e1: GR[String]): GR[OfmucroompropRow] = GR{
    prs => import prs._
    OfmucroompropRow.tupled((<<[Long], <<[String], <<[String]))
  }
  /** Table description of table ofMucRoomProp. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucroomprop(_tableTag: Tag) extends Table[OfmucroompropRow](_tableTag, "ofMucRoomProp") {
    def * = (roomid, name, propvalue) <> (OfmucroompropRow.tupled, OfmucroompropRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (roomid.?, name.?, propvalue.?).shaped.<>({r=>import r._; _1.map(_=> OfmucroompropRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column roomID DBType(BIGINT) */
    val roomid: Column[Long] = column[Long]("roomID")
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column propValue DBType(TEXT), Length(65535,true) */
    val propvalue: Column[String] = column[String]("propValue", O.Length(65535,varying=true))
    
    /** Primary key of Ofmucroomprop (database name ofMucRoomProp_PK) */
    val pk = primaryKey("ofMucRoomProp_PK", (roomid, name))
  }
  /** Collection-like TableQuery object for table Ofmucroomprop */
  lazy val Ofmucroomprop = new TableQuery(tag => new Ofmucroomprop(tag))
  
  /** Entity class storing rows of table Ofmucservice
   *  @param serviceid Database column serviceID DBType(BIGINT)
   *  @param subdomain Database column subdomain DBType(VARCHAR), PrimaryKey, Length(255,true)
   *  @param description Database column description DBType(VARCHAR), Length(255,true), Default(None)
   *  @param ishidden Database column isHidden DBType(TINYINT) */
  case class OfmucserviceRow(serviceid: Long, subdomain: String, description: Option[String] = None, ishidden: Byte)
  /** GetResult implicit for fetching OfmucserviceRow objects using plain SQL queries */
  implicit def GetResultOfmucserviceRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Byte]): GR[OfmucserviceRow] = GR{
    prs => import prs._
    OfmucserviceRow.tupled((<<[Long], <<[String], <<?[String], <<[Byte]))
  }
  /** Table description of table ofMucService. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucservice(_tableTag: Tag) extends Table[OfmucserviceRow](_tableTag, "ofMucService") {
    def * = (serviceid, subdomain, description, ishidden) <> (OfmucserviceRow.tupled, OfmucserviceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, subdomain.?, description, ishidden.?).shaped.<>({r=>import r._; _1.map(_=> OfmucserviceRow.tupled((_1.get, _2.get, _3, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(BIGINT) */
    val serviceid: Column[Long] = column[Long]("serviceID")
    /** Database column subdomain DBType(VARCHAR), PrimaryKey, Length(255,true) */
    val subdomain: Column[String] = column[String]("subdomain", O.PrimaryKey, O.Length(255,varying=true))
    /** Database column description DBType(VARCHAR), Length(255,true), Default(None) */
    val description: Column[Option[String]] = column[Option[String]]("description", O.Length(255,varying=true), O.Default(None))
    /** Database column isHidden DBType(TINYINT) */
    val ishidden: Column[Byte] = column[Byte]("isHidden")
    
    /** Index over (serviceid) (database name ofMucService_serviceid_idx) */
    val index1 = index("ofMucService_serviceid_idx", serviceid)
  }
  /** Collection-like TableQuery object for table Ofmucservice */
  lazy val Ofmucservice = new TableQuery(tag => new Ofmucservice(tag))
  
  /** Entity class storing rows of table Ofmucserviceprop
   *  @param serviceid Database column serviceID DBType(BIGINT)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param propvalue Database column propValue DBType(TEXT), Length(65535,true) */
  case class OfmucservicepropRow(serviceid: Long, name: String, propvalue: String)
  /** GetResult implicit for fetching OfmucservicepropRow objects using plain SQL queries */
  implicit def GetResultOfmucservicepropRow(implicit e0: GR[Long], e1: GR[String]): GR[OfmucservicepropRow] = GR{
    prs => import prs._
    OfmucservicepropRow.tupled((<<[Long], <<[String], <<[String]))
  }
  /** Table description of table ofMucServiceProp. Objects of this class serve as prototypes for rows in queries. */
  class Ofmucserviceprop(_tableTag: Tag) extends Table[OfmucservicepropRow](_tableTag, "ofMucServiceProp") {
    def * = (serviceid, name, propvalue) <> (OfmucservicepropRow.tupled, OfmucservicepropRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, name.?, propvalue.?).shaped.<>({r=>import r._; _1.map(_=> OfmucservicepropRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(BIGINT) */
    val serviceid: Column[Long] = column[Long]("serviceID")
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column propValue DBType(TEXT), Length(65535,true) */
    val propvalue: Column[String] = column[String]("propValue", O.Length(65535,varying=true))
    
    /** Primary key of Ofmucserviceprop (database name ofMucServiceProp_PK) */
    val pk = primaryKey("ofMucServiceProp_PK", (serviceid, name))
  }
  /** Collection-like TableQuery object for table Ofmucserviceprop */
  lazy val Ofmucserviceprop = new TableQuery(tag => new Ofmucserviceprop(tag))
  
  /** Entity class storing rows of table Ofoffline
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param messageid Database column messageID DBType(BIGINT)
   *  @param creationdate Database column creationDate DBType(CHAR), Length(15,false)
   *  @param messagesize Database column messageSize DBType(INT)
   *  @param stanza Database column stanza DBType(TEXT), Length(65535,true) */
  case class OfofflineRow(username: String, messageid: Long, creationdate: String, messagesize: Int, stanza: String)
  /** GetResult implicit for fetching OfofflineRow objects using plain SQL queries */
  implicit def GetResultOfofflineRow(implicit e0: GR[String], e1: GR[Long], e2: GR[Int]): GR[OfofflineRow] = GR{
    prs => import prs._
    OfofflineRow.tupled((<<[String], <<[Long], <<[String], <<[Int], <<[String]))
  }
  /** Table description of table ofOffline. Objects of this class serve as prototypes for rows in queries. */
  class Ofoffline(_tableTag: Tag) extends Table[OfofflineRow](_tableTag, "ofOffline") {
    def * = (username, messageid, creationdate, messagesize, stanza) <> (OfofflineRow.tupled, OfofflineRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, messageid.?, creationdate.?, messagesize.?, stanza.?).shaped.<>({r=>import r._; _1.map(_=> OfofflineRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column messageID DBType(BIGINT) */
    val messageid: Column[Long] = column[Long]("messageID")
    /** Database column creationDate DBType(CHAR), Length(15,false) */
    val creationdate: Column[String] = column[String]("creationDate", O.Length(15,varying=false))
    /** Database column messageSize DBType(INT) */
    val messagesize: Column[Int] = column[Int]("messageSize")
    /** Database column stanza DBType(TEXT), Length(65535,true) */
    val stanza: Column[String] = column[String]("stanza", O.Length(65535,varying=true))
    
    /** Primary key of Ofoffline (database name ofOffline_PK) */
    val pk = primaryKey("ofOffline_PK", (username, messageid))
  }
  /** Collection-like TableQuery object for table Ofoffline */
  lazy val Ofoffline = new TableQuery(tag => new Ofoffline(tag))
  
  /** Entity class storing rows of table Ofpresence
   *  @param username Database column username DBType(VARCHAR), PrimaryKey, Length(64,true)
   *  @param offlinepresence Database column offlinePresence DBType(TEXT), Length(65535,true), Default(None)
   *  @param offlinedate Database column offlineDate DBType(CHAR), Length(15,false) */
  case class OfpresenceRow(username: String, offlinepresence: Option[String] = None, offlinedate: String)
  /** GetResult implicit for fetching OfpresenceRow objects using plain SQL queries */
  implicit def GetResultOfpresenceRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[OfpresenceRow] = GR{
    prs => import prs._
    OfpresenceRow.tupled((<<[String], <<?[String], <<[String]))
  }
  /** Table description of table ofPresence. Objects of this class serve as prototypes for rows in queries. */
  class Ofpresence(_tableTag: Tag) extends Table[OfpresenceRow](_tableTag, "ofPresence") {
    def * = (username, offlinepresence, offlinedate) <> (OfpresenceRow.tupled, OfpresenceRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, offlinepresence, offlinedate.?).shaped.<>({r=>import r._; _1.map(_=> OfpresenceRow.tupled((_1.get, _2, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), PrimaryKey, Length(64,true) */
    val username: Column[String] = column[String]("username", O.PrimaryKey, O.Length(64,varying=true))
    /** Database column offlinePresence DBType(TEXT), Length(65535,true), Default(None) */
    val offlinepresence: Column[Option[String]] = column[Option[String]]("offlinePresence", O.Length(65535,varying=true), O.Default(None))
    /** Database column offlineDate DBType(CHAR), Length(15,false) */
    val offlinedate: Column[String] = column[String]("offlineDate", O.Length(15,varying=false))
  }
  /** Collection-like TableQuery object for table Ofpresence */
  lazy val Ofpresence = new TableQuery(tag => new Ofpresence(tag))
  
  /** Entity class storing rows of table Ofprivacylist
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param isdefault Database column isDefault DBType(TINYINT)
   *  @param list Database column list DBType(TEXT), Length(65535,true) */
  case class OfprivacylistRow(username: String, name: String, isdefault: Byte, list: String)
  /** GetResult implicit for fetching OfprivacylistRow objects using plain SQL queries */
  implicit def GetResultOfprivacylistRow(implicit e0: GR[String], e1: GR[Byte]): GR[OfprivacylistRow] = GR{
    prs => import prs._
    OfprivacylistRow.tupled((<<[String], <<[String], <<[Byte], <<[String]))
  }
  /** Table description of table ofPrivacyList. Objects of this class serve as prototypes for rows in queries. */
  class Ofprivacylist(_tableTag: Tag) extends Table[OfprivacylistRow](_tableTag, "ofPrivacyList") {
    def * = (username, name, isdefault, list) <> (OfprivacylistRow.tupled, OfprivacylistRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, name.?, isdefault.?, list.?).shaped.<>({r=>import r._; _1.map(_=> OfprivacylistRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column isDefault DBType(TINYINT) */
    val isdefault: Column[Byte] = column[Byte]("isDefault")
    /** Database column list DBType(TEXT), Length(65535,true) */
    val list: Column[String] = column[String]("list", O.Length(65535,varying=true))
    
    /** Primary key of Ofprivacylist (database name ofPrivacyList_PK) */
    val pk = primaryKey("ofPrivacyList_PK", (username, name))
    
    /** Index over (username,isdefault) (database name ofPrivacyList_default_idx) */
    val index1 = index("ofPrivacyList_default_idx", (username, isdefault))
  }
  /** Collection-like TableQuery object for table Ofprivacylist */
  lazy val Ofprivacylist = new TableQuery(tag => new Ofprivacylist(tag))
  
  /** Entity class storing rows of table Ofprivate
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param namespace Database column namespace DBType(VARCHAR), Length(200,true)
   *  @param privatedata Database column privateData DBType(TEXT), Length(65535,true) */
  case class OfprivateRow(username: String, name: String, namespace: String, privatedata: String)
  /** GetResult implicit for fetching OfprivateRow objects using plain SQL queries */
  implicit def GetResultOfprivateRow(implicit e0: GR[String]): GR[OfprivateRow] = GR{
    prs => import prs._
    OfprivateRow.tupled((<<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table ofPrivate. Objects of this class serve as prototypes for rows in queries. */
  class Ofprivate(_tableTag: Tag) extends Table[OfprivateRow](_tableTag, "ofPrivate") {
    def * = (username, name, namespace, privatedata) <> (OfprivateRow.tupled, OfprivateRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, name.?, namespace.?, privatedata.?).shaped.<>({r=>import r._; _1.map(_=> OfprivateRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column namespace DBType(VARCHAR), Length(200,true) */
    val namespace: Column[String] = column[String]("namespace", O.Length(200,varying=true))
    /** Database column privateData DBType(TEXT), Length(65535,true) */
    val privatedata: Column[String] = column[String]("privateData", O.Length(65535,varying=true))
    
    /** Primary key of Ofprivate (database name ofPrivate_PK) */
    val pk = primaryKey("ofPrivate_PK", (username, name, namespace))
  }
  /** Collection-like TableQuery object for table Ofprivate */
  lazy val Ofprivate = new TableQuery(tag => new Ofprivate(tag))
  
  /** Entity class storing rows of table Ofproperty
   *  @param name Database column name DBType(VARCHAR), PrimaryKey, Length(100,true)
   *  @param propvalue Database column propValue DBType(TEXT), Length(65535,true) */
  case class OfpropertyRow(name: String, propvalue: String)
  /** GetResult implicit for fetching OfpropertyRow objects using plain SQL queries */
  implicit def GetResultOfpropertyRow(implicit e0: GR[String]): GR[OfpropertyRow] = GR{
    prs => import prs._
    OfpropertyRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table ofProperty. Objects of this class serve as prototypes for rows in queries. */
  class Ofproperty(_tableTag: Tag) extends Table[OfpropertyRow](_tableTag, "ofProperty") {
    def * = (name, propvalue) <> (OfpropertyRow.tupled, OfpropertyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (name.?, propvalue.?).shaped.<>({r=>import r._; _1.map(_=> OfpropertyRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column name DBType(VARCHAR), PrimaryKey, Length(100,true) */
    val name: Column[String] = column[String]("name", O.PrimaryKey, O.Length(100,varying=true))
    /** Database column propValue DBType(TEXT), Length(65535,true) */
    val propvalue: Column[String] = column[String]("propValue", O.Length(65535,varying=true))
  }
  /** Collection-like TableQuery object for table Ofproperty */
  lazy val Ofproperty = new TableQuery(tag => new Ofproperty(tag))
  
  /** Entity class storing rows of table Ofpubsubaffiliation
   *  @param serviceid Database column serviceID DBType(VARCHAR), Length(100,true)
   *  @param nodeid Database column nodeID DBType(VARCHAR), Length(100,true)
   *  @param jid Database column jid DBType(VARCHAR), Length(255,true)
   *  @param affiliation Database column affiliation DBType(VARCHAR), Length(10,true) */
  case class OfpubsubaffiliationRow(serviceid: String, nodeid: String, jid: String, affiliation: String)
  /** GetResult implicit for fetching OfpubsubaffiliationRow objects using plain SQL queries */
  implicit def GetResultOfpubsubaffiliationRow(implicit e0: GR[String]): GR[OfpubsubaffiliationRow] = GR{
    prs => import prs._
    OfpubsubaffiliationRow.tupled((<<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table ofPubsubAffiliation. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubaffiliation(_tableTag: Tag) extends Table[OfpubsubaffiliationRow](_tableTag, "ofPubsubAffiliation") {
    def * = (serviceid, nodeid, jid, affiliation) <> (OfpubsubaffiliationRow.tupled, OfpubsubaffiliationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, nodeid.?, jid.?, affiliation.?).shaped.<>({r=>import r._; _1.map(_=> OfpubsubaffiliationRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column nodeID DBType(VARCHAR), Length(100,true) */
    val nodeid: Column[String] = column[String]("nodeID", O.Length(100,varying=true))
    /** Database column jid DBType(VARCHAR), Length(255,true) */
    val jid: Column[String] = column[String]("jid", O.Length(255,varying=true))
    /** Database column affiliation DBType(VARCHAR), Length(10,true) */
    val affiliation: Column[String] = column[String]("affiliation", O.Length(10,varying=true))
    
    /** Primary key of Ofpubsubaffiliation (database name ofPubsubAffiliation_PK) */
    val pk = primaryKey("ofPubsubAffiliation_PK", (serviceid, nodeid, jid))
  }
  /** Collection-like TableQuery object for table Ofpubsubaffiliation */
  lazy val Ofpubsubaffiliation = new TableQuery(tag => new Ofpubsubaffiliation(tag))
  
  /** Entity class storing rows of table Ofpubsubdefaultconf
   *  @param serviceid Database column serviceID DBType(VARCHAR), Length(100,true)
   *  @param leaf Database column leaf DBType(TINYINT)
   *  @param deliverpayloads Database column deliverPayloads DBType(TINYINT)
   *  @param maxpayloadsize Database column maxPayloadSize DBType(INT)
   *  @param persistitems Database column persistItems DBType(TINYINT)
   *  @param maxitems Database column maxItems DBType(INT)
   *  @param notifyconfigchanges Database column notifyConfigChanges DBType(TINYINT)
   *  @param notifydelete Database column notifyDelete DBType(TINYINT)
   *  @param notifyretract Database column notifyRetract DBType(TINYINT)
   *  @param presencebased Database column presenceBased DBType(TINYINT)
   *  @param senditemsubscribe Database column sendItemSubscribe DBType(TINYINT)
   *  @param publishermodel Database column publisherModel DBType(VARCHAR), Length(15,true)
   *  @param subscriptionenabled Database column subscriptionEnabled DBType(TINYINT)
   *  @param accessmodel Database column accessModel DBType(VARCHAR), Length(10,true)
   *  @param language Database column language DBType(VARCHAR), Length(255,true), Default(None)
   *  @param replypolicy Database column replyPolicy DBType(VARCHAR), Length(15,true), Default(None)
   *  @param associationpolicy Database column associationPolicy DBType(VARCHAR), Length(15,true)
   *  @param maxleafnodes Database column maxLeafNodes DBType(INT) */
  case class OfpubsubdefaultconfRow(serviceid: String, leaf: Byte, deliverpayloads: Byte, maxpayloadsize: Int, persistitems: Byte, maxitems: Int, notifyconfigchanges: Byte, notifydelete: Byte, notifyretract: Byte, presencebased: Byte, senditemsubscribe: Byte, publishermodel: String, subscriptionenabled: Byte, accessmodel: String, language: Option[String] = None, replypolicy: Option[String] = None, associationpolicy: String, maxleafnodes: Int)
  /** GetResult implicit for fetching OfpubsubdefaultconfRow objects using plain SQL queries */
  implicit def GetResultOfpubsubdefaultconfRow(implicit e0: GR[String], e1: GR[Byte], e2: GR[Int], e3: GR[Option[String]]): GR[OfpubsubdefaultconfRow] = GR{
    prs => import prs._
    OfpubsubdefaultconfRow.tupled((<<[String], <<[Byte], <<[Byte], <<[Int], <<[Byte], <<[Int], <<[Byte], <<[Byte], <<[Byte], <<[Byte], <<[Byte], <<[String], <<[Byte], <<[String], <<?[String], <<?[String], <<[String], <<[Int]))
  }
  /** Table description of table ofPubsubDefaultConf. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubdefaultconf(_tableTag: Tag) extends Table[OfpubsubdefaultconfRow](_tableTag, "ofPubsubDefaultConf") {
    def * = (serviceid, leaf, deliverpayloads, maxpayloadsize, persistitems, maxitems, notifyconfigchanges, notifydelete, notifyretract, presencebased, senditemsubscribe, publishermodel, subscriptionenabled, accessmodel, language, replypolicy, associationpolicy, maxleafnodes) <> (OfpubsubdefaultconfRow.tupled, OfpubsubdefaultconfRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, leaf.?, deliverpayloads.?, maxpayloadsize.?, persistitems.?, maxitems.?, notifyconfigchanges.?, notifydelete.?, notifyretract.?, presencebased.?, senditemsubscribe.?, publishermodel.?, subscriptionenabled.?, accessmodel.?, language, replypolicy, associationpolicy.?, maxleafnodes.?).shaped.<>({r=>import r._; _1.map(_=> OfpubsubdefaultconfRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15, _16, _17.get, _18.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column leaf DBType(TINYINT) */
    val leaf: Column[Byte] = column[Byte]("leaf")
    /** Database column deliverPayloads DBType(TINYINT) */
    val deliverpayloads: Column[Byte] = column[Byte]("deliverPayloads")
    /** Database column maxPayloadSize DBType(INT) */
    val maxpayloadsize: Column[Int] = column[Int]("maxPayloadSize")
    /** Database column persistItems DBType(TINYINT) */
    val persistitems: Column[Byte] = column[Byte]("persistItems")
    /** Database column maxItems DBType(INT) */
    val maxitems: Column[Int] = column[Int]("maxItems")
    /** Database column notifyConfigChanges DBType(TINYINT) */
    val notifyconfigchanges: Column[Byte] = column[Byte]("notifyConfigChanges")
    /** Database column notifyDelete DBType(TINYINT) */
    val notifydelete: Column[Byte] = column[Byte]("notifyDelete")
    /** Database column notifyRetract DBType(TINYINT) */
    val notifyretract: Column[Byte] = column[Byte]("notifyRetract")
    /** Database column presenceBased DBType(TINYINT) */
    val presencebased: Column[Byte] = column[Byte]("presenceBased")
    /** Database column sendItemSubscribe DBType(TINYINT) */
    val senditemsubscribe: Column[Byte] = column[Byte]("sendItemSubscribe")
    /** Database column publisherModel DBType(VARCHAR), Length(15,true) */
    val publishermodel: Column[String] = column[String]("publisherModel", O.Length(15,varying=true))
    /** Database column subscriptionEnabled DBType(TINYINT) */
    val subscriptionenabled: Column[Byte] = column[Byte]("subscriptionEnabled")
    /** Database column accessModel DBType(VARCHAR), Length(10,true) */
    val accessmodel: Column[String] = column[String]("accessModel", O.Length(10,varying=true))
    /** Database column language DBType(VARCHAR), Length(255,true), Default(None) */
    val language: Column[Option[String]] = column[Option[String]]("language", O.Length(255,varying=true), O.Default(None))
    /** Database column replyPolicy DBType(VARCHAR), Length(15,true), Default(None) */
    val replypolicy: Column[Option[String]] = column[Option[String]]("replyPolicy", O.Length(15,varying=true), O.Default(None))
    /** Database column associationPolicy DBType(VARCHAR), Length(15,true) */
    val associationpolicy: Column[String] = column[String]("associationPolicy", O.Length(15,varying=true))
    /** Database column maxLeafNodes DBType(INT) */
    val maxleafnodes: Column[Int] = column[Int]("maxLeafNodes")
    
    /** Primary key of Ofpubsubdefaultconf (database name ofPubsubDefaultConf_PK) */
    val pk = primaryKey("ofPubsubDefaultConf_PK", (serviceid, leaf))
  }
  /** Collection-like TableQuery object for table Ofpubsubdefaultconf */
  lazy val Ofpubsubdefaultconf = new TableQuery(tag => new Ofpubsubdefaultconf(tag))
  
  /** Entity class storing rows of table Ofpubsubitem
   *  @param serviceid Database column serviceID DBType(VARCHAR), Length(100,true)
   *  @param nodeid Database column nodeID DBType(VARCHAR), Length(100,true)
   *  @param id Database column id DBType(VARCHAR), Length(100,true)
   *  @param jid Database column jid DBType(VARCHAR), Length(255,true)
   *  @param creationdate Database column creationDate DBType(CHAR), Length(15,false)
   *  @param payload Database column payload DBType(MEDIUMTEXT), Length(16777215,true), Default(None) */
  case class OfpubsubitemRow(serviceid: String, nodeid: String, id: String, jid: String, creationdate: String, payload: Option[String] = None)
  /** GetResult implicit for fetching OfpubsubitemRow objects using plain SQL queries */
  implicit def GetResultOfpubsubitemRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[OfpubsubitemRow] = GR{
    prs => import prs._
    OfpubsubitemRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<?[String]))
  }
  /** Table description of table ofPubsubItem. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubitem(_tableTag: Tag) extends Table[OfpubsubitemRow](_tableTag, "ofPubsubItem") {
    def * = (serviceid, nodeid, id, jid, creationdate, payload) <> (OfpubsubitemRow.tupled, OfpubsubitemRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, nodeid.?, id.?, jid.?, creationdate.?, payload).shaped.<>({r=>import r._; _1.map(_=> OfpubsubitemRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column nodeID DBType(VARCHAR), Length(100,true) */
    val nodeid: Column[String] = column[String]("nodeID", O.Length(100,varying=true))
    /** Database column id DBType(VARCHAR), Length(100,true) */
    val id: Column[String] = column[String]("id", O.Length(100,varying=true))
    /** Database column jid DBType(VARCHAR), Length(255,true) */
    val jid: Column[String] = column[String]("jid", O.Length(255,varying=true))
    /** Database column creationDate DBType(CHAR), Length(15,false) */
    val creationdate: Column[String] = column[String]("creationDate", O.Length(15,varying=false))
    /** Database column payload DBType(MEDIUMTEXT), Length(16777215,true), Default(None) */
    val payload: Column[Option[String]] = column[Option[String]]("payload", O.Length(16777215,varying=true), O.Default(None))
    
    /** Primary key of Ofpubsubitem (database name ofPubsubItem_PK) */
    val pk = primaryKey("ofPubsubItem_PK", (serviceid, nodeid, id))
  }
  /** Collection-like TableQuery object for table Ofpubsubitem */
  lazy val Ofpubsubitem = new TableQuery(tag => new Ofpubsubitem(tag))
  
  /** Row type of table Ofpubsubnode */
  type OfpubsubnodeRow = HCons[String,HCons[String,HCons[Byte,HCons[String,HCons[String,HCons[Option[String],HCons[Byte,HCons[Option[Int],HCons[Option[Byte],HCons[Option[Int],HCons[Byte,HCons[Byte,HCons[Byte,HCons[Byte,HCons[Byte,HCons[String,HCons[Byte,HCons[Byte,HCons[String,HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[String,HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[String],HCons[Option[Int],HNil]]]]]]]]]]]]]]]]]]]]]]]]]]]]]
  /** Constructor for OfpubsubnodeRow providing default values if available in the database schema. */
  def OfpubsubnodeRow(serviceid: String, nodeid: String, leaf: Byte, creationdate: String, modificationdate: String, parent: Option[String] = None, deliverpayloads: Byte, maxpayloadsize: Option[Int] = None, persistitems: Option[Byte] = None, maxitems: Option[Int] = None, notifyconfigchanges: Byte, notifydelete: Byte, notifyretract: Byte, presencebased: Byte, senditemsubscribe: Byte, publishermodel: String, subscriptionenabled: Byte, configsubscription: Byte, accessmodel: String, payloadtype: Option[String] = None, bodyxslt: Option[String] = None, dataformxslt: Option[String] = None, creator: String, description: Option[String] = None, language: Option[String] = None, name: Option[String] = None, replypolicy: Option[String] = None, associationpolicy: Option[String] = None, maxleafnodes: Option[Int] = None): OfpubsubnodeRow = {
    serviceid :: nodeid :: leaf :: creationdate :: modificationdate :: parent :: deliverpayloads :: maxpayloadsize :: persistitems :: maxitems :: notifyconfigchanges :: notifydelete :: notifyretract :: presencebased :: senditemsubscribe :: publishermodel :: subscriptionenabled :: configsubscription :: accessmodel :: payloadtype :: bodyxslt :: dataformxslt :: creator :: description :: language :: name :: replypolicy :: associationpolicy :: maxleafnodes :: HNil
  }
  /** GetResult implicit for fetching OfpubsubnodeRow objects using plain SQL queries */
  implicit def GetResultOfpubsubnodeRow(implicit e0: GR[String], e1: GR[Byte], e2: GR[Option[String]], e3: GR[Option[Int]], e4: GR[Option[Byte]]): GR[OfpubsubnodeRow] = GR{
    prs => import prs._
    <<[String] :: <<[String] :: <<[Byte] :: <<[String] :: <<[String] :: <<?[String] :: <<[Byte] :: <<?[Int] :: <<?[Byte] :: <<?[Int] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: <<[Byte] :: <<[String] :: <<[Byte] :: <<[Byte] :: <<[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[String] :: <<?[Int] :: HNil
  }
  /** Table description of table ofPubsubNode. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubnode(_tableTag: Tag) extends Table[OfpubsubnodeRow](_tableTag, "ofPubsubNode") {
    def * = serviceid :: nodeid :: leaf :: creationdate :: modificationdate :: parent :: deliverpayloads :: maxpayloadsize :: persistitems :: maxitems :: notifyconfigchanges :: notifydelete :: notifyretract :: presencebased :: senditemsubscribe :: publishermodel :: subscriptionenabled :: configsubscription :: accessmodel :: payloadtype :: bodyxslt :: dataformxslt :: creator :: description :: language :: name :: replypolicy :: associationpolicy :: maxleafnodes :: HNil
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column nodeID DBType(VARCHAR), Length(100,true) */
    val nodeid: Column[String] = column[String]("nodeID", O.Length(100,varying=true))
    /** Database column leaf DBType(TINYINT) */
    val leaf: Column[Byte] = column[Byte]("leaf")
    /** Database column creationDate DBType(CHAR), Length(15,false) */
    val creationdate: Column[String] = column[String]("creationDate", O.Length(15,varying=false))
    /** Database column modificationDate DBType(CHAR), Length(15,false) */
    val modificationdate: Column[String] = column[String]("modificationDate", O.Length(15,varying=false))
    /** Database column parent DBType(VARCHAR), Length(100,true), Default(None) */
    val parent: Column[Option[String]] = column[Option[String]]("parent", O.Length(100,varying=true), O.Default(None))
    /** Database column deliverPayloads DBType(TINYINT) */
    val deliverpayloads: Column[Byte] = column[Byte]("deliverPayloads")
    /** Database column maxPayloadSize DBType(INT), Default(None) */
    val maxpayloadsize: Column[Option[Int]] = column[Option[Int]]("maxPayloadSize", O.Default(None))
    /** Database column persistItems DBType(TINYINT), Default(None) */
    val persistitems: Column[Option[Byte]] = column[Option[Byte]]("persistItems", O.Default(None))
    /** Database column maxItems DBType(INT), Default(None) */
    val maxitems: Column[Option[Int]] = column[Option[Int]]("maxItems", O.Default(None))
    /** Database column notifyConfigChanges DBType(TINYINT) */
    val notifyconfigchanges: Column[Byte] = column[Byte]("notifyConfigChanges")
    /** Database column notifyDelete DBType(TINYINT) */
    val notifydelete: Column[Byte] = column[Byte]("notifyDelete")
    /** Database column notifyRetract DBType(TINYINT) */
    val notifyretract: Column[Byte] = column[Byte]("notifyRetract")
    /** Database column presenceBased DBType(TINYINT) */
    val presencebased: Column[Byte] = column[Byte]("presenceBased")
    /** Database column sendItemSubscribe DBType(TINYINT) */
    val senditemsubscribe: Column[Byte] = column[Byte]("sendItemSubscribe")
    /** Database column publisherModel DBType(VARCHAR), Length(15,true) */
    val publishermodel: Column[String] = column[String]("publisherModel", O.Length(15,varying=true))
    /** Database column subscriptionEnabled DBType(TINYINT) */
    val subscriptionenabled: Column[Byte] = column[Byte]("subscriptionEnabled")
    /** Database column configSubscription DBType(TINYINT) */
    val configsubscription: Column[Byte] = column[Byte]("configSubscription")
    /** Database column accessModel DBType(VARCHAR), Length(10,true) */
    val accessmodel: Column[String] = column[String]("accessModel", O.Length(10,varying=true))
    /** Database column payloadType DBType(VARCHAR), Length(100,true), Default(None) */
    val payloadtype: Column[Option[String]] = column[Option[String]]("payloadType", O.Length(100,varying=true), O.Default(None))
    /** Database column bodyXSLT DBType(VARCHAR), Length(100,true), Default(None) */
    val bodyxslt: Column[Option[String]] = column[Option[String]]("bodyXSLT", O.Length(100,varying=true), O.Default(None))
    /** Database column dataformXSLT DBType(VARCHAR), Length(100,true), Default(None) */
    val dataformxslt: Column[Option[String]] = column[Option[String]]("dataformXSLT", O.Length(100,varying=true), O.Default(None))
    /** Database column creator DBType(VARCHAR), Length(255,true) */
    val creator: Column[String] = column[String]("creator", O.Length(255,varying=true))
    /** Database column description DBType(VARCHAR), Length(255,true), Default(None) */
    val description: Column[Option[String]] = column[Option[String]]("description", O.Length(255,varying=true), O.Default(None))
    /** Database column language DBType(VARCHAR), Length(255,true), Default(None) */
    val language: Column[Option[String]] = column[Option[String]]("language", O.Length(255,varying=true), O.Default(None))
    /** Database column name DBType(VARCHAR), Length(50,true), Default(None) */
    val name: Column[Option[String]] = column[Option[String]]("name", O.Length(50,varying=true), O.Default(None))
    /** Database column replyPolicy DBType(VARCHAR), Length(15,true), Default(None) */
    val replypolicy: Column[Option[String]] = column[Option[String]]("replyPolicy", O.Length(15,varying=true), O.Default(None))
    /** Database column associationPolicy DBType(VARCHAR), Length(15,true), Default(None) */
    val associationpolicy: Column[Option[String]] = column[Option[String]]("associationPolicy", O.Length(15,varying=true), O.Default(None))
    /** Database column maxLeafNodes DBType(INT), Default(None) */
    val maxleafnodes: Column[Option[Int]] = column[Option[Int]]("maxLeafNodes", O.Default(None))
    
    /** Primary key of Ofpubsubnode (database name ofPubsubNode_PK) */
    val pk = primaryKey("ofPubsubNode_PK", serviceid :: nodeid :: HNil)
  }
  /** Collection-like TableQuery object for table Ofpubsubnode */
  lazy val Ofpubsubnode = new TableQuery(tag => new Ofpubsubnode(tag))
  
  /** Entity class storing rows of table Ofpubsubnodegroups
   *  @param serviceid Database column serviceID DBType(VARCHAR), Length(100,true)
   *  @param nodeid Database column nodeID DBType(VARCHAR), Length(100,true)
   *  @param rostergroup Database column rosterGroup DBType(VARCHAR), Length(100,true) */
  case class OfpubsubnodegroupsRow(serviceid: String, nodeid: String, rostergroup: String)
  /** GetResult implicit for fetching OfpubsubnodegroupsRow objects using plain SQL queries */
  implicit def GetResultOfpubsubnodegroupsRow(implicit e0: GR[String]): GR[OfpubsubnodegroupsRow] = GR{
    prs => import prs._
    OfpubsubnodegroupsRow.tupled((<<[String], <<[String], <<[String]))
  }
  /** Table description of table ofPubsubNodeGroups. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubnodegroups(_tableTag: Tag) extends Table[OfpubsubnodegroupsRow](_tableTag, "ofPubsubNodeGroups") {
    def * = (serviceid, nodeid, rostergroup) <> (OfpubsubnodegroupsRow.tupled, OfpubsubnodegroupsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, nodeid.?, rostergroup.?).shaped.<>({r=>import r._; _1.map(_=> OfpubsubnodegroupsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column nodeID DBType(VARCHAR), Length(100,true) */
    val nodeid: Column[String] = column[String]("nodeID", O.Length(100,varying=true))
    /** Database column rosterGroup DBType(VARCHAR), Length(100,true) */
    val rostergroup: Column[String] = column[String]("rosterGroup", O.Length(100,varying=true))
    
    /** Index over (serviceid,nodeid) (database name ofPubsubNodeGroups_idx) */
    val index1 = index("ofPubsubNodeGroups_idx", (serviceid, nodeid))
  }
  /** Collection-like TableQuery object for table Ofpubsubnodegroups */
  lazy val Ofpubsubnodegroups = new TableQuery(tag => new Ofpubsubnodegroups(tag))
  
  /** Entity class storing rows of table Ofpubsubnodejids
   *  @param serviceid Database column serviceID DBType(VARCHAR), Length(100,true)
   *  @param nodeid Database column nodeID DBType(VARCHAR), Length(100,true)
   *  @param jid Database column jid DBType(VARCHAR), Length(255,true)
   *  @param associationtype Database column associationType DBType(VARCHAR), Length(20,true) */
  case class OfpubsubnodejidsRow(serviceid: String, nodeid: String, jid: String, associationtype: String)
  /** GetResult implicit for fetching OfpubsubnodejidsRow objects using plain SQL queries */
  implicit def GetResultOfpubsubnodejidsRow(implicit e0: GR[String]): GR[OfpubsubnodejidsRow] = GR{
    prs => import prs._
    OfpubsubnodejidsRow.tupled((<<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table ofPubsubNodeJIDs. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubnodejids(_tableTag: Tag) extends Table[OfpubsubnodejidsRow](_tableTag, "ofPubsubNodeJIDs") {
    def * = (serviceid, nodeid, jid, associationtype) <> (OfpubsubnodejidsRow.tupled, OfpubsubnodejidsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, nodeid.?, jid.?, associationtype.?).shaped.<>({r=>import r._; _1.map(_=> OfpubsubnodejidsRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column nodeID DBType(VARCHAR), Length(100,true) */
    val nodeid: Column[String] = column[String]("nodeID", O.Length(100,varying=true))
    /** Database column jid DBType(VARCHAR), Length(255,true) */
    val jid: Column[String] = column[String]("jid", O.Length(255,varying=true))
    /** Database column associationType DBType(VARCHAR), Length(20,true) */
    val associationtype: Column[String] = column[String]("associationType", O.Length(20,varying=true))
    
    /** Primary key of Ofpubsubnodejids (database name ofPubsubNodeJIDs_PK) */
    val pk = primaryKey("ofPubsubNodeJIDs_PK", (serviceid, nodeid, jid))
  }
  /** Collection-like TableQuery object for table Ofpubsubnodejids */
  lazy val Ofpubsubnodejids = new TableQuery(tag => new Ofpubsubnodejids(tag))
  
  /** Entity class storing rows of table Ofpubsubsubscription
   *  @param serviceid Database column serviceID DBType(VARCHAR), Length(100,true)
   *  @param nodeid Database column nodeID DBType(VARCHAR), Length(100,true)
   *  @param id Database column id DBType(VARCHAR), Length(100,true)
   *  @param jid Database column jid DBType(VARCHAR), Length(255,true)
   *  @param owner Database column owner DBType(VARCHAR), Length(255,true)
   *  @param state Database column state DBType(VARCHAR), Length(15,true)
   *  @param deliver Database column deliver DBType(TINYINT)
   *  @param digest Database column digest DBType(TINYINT)
   *  @param digestFrequency Database column digest_frequency DBType(INT)
   *  @param expire Database column expire DBType(CHAR), Length(15,false), Default(None)
   *  @param includebody Database column includeBody DBType(TINYINT)
   *  @param showvalues Database column showValues DBType(VARCHAR), Length(30,true), Default(None)
   *  @param subscriptiontype Database column subscriptionType DBType(VARCHAR), Length(10,true)
   *  @param subscriptiondepth Database column subscriptionDepth DBType(TINYINT)
   *  @param keyword Database column keyword DBType(VARCHAR), Length(200,true), Default(None) */
  case class OfpubsubsubscriptionRow(serviceid: String, nodeid: String, id: String, jid: String, owner: String, state: String, deliver: Byte, digest: Byte, digestFrequency: Int, expire: Option[String] = None, includebody: Byte, showvalues: Option[String] = None, subscriptiontype: String, subscriptiondepth: Byte, keyword: Option[String] = None)
  /** GetResult implicit for fetching OfpubsubsubscriptionRow objects using plain SQL queries */
  implicit def GetResultOfpubsubsubscriptionRow(implicit e0: GR[String], e1: GR[Byte], e2: GR[Int], e3: GR[Option[String]]): GR[OfpubsubsubscriptionRow] = GR{
    prs => import prs._
    OfpubsubsubscriptionRow.tupled((<<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Byte], <<[Byte], <<[Int], <<?[String], <<[Byte], <<?[String], <<[String], <<[Byte], <<?[String]))
  }
  /** Table description of table ofPubsubSubscription. Objects of this class serve as prototypes for rows in queries. */
  class Ofpubsubsubscription(_tableTag: Tag) extends Table[OfpubsubsubscriptionRow](_tableTag, "ofPubsubSubscription") {
    def * = (serviceid, nodeid, id, jid, owner, state, deliver, digest, digestFrequency, expire, includebody, showvalues, subscriptiontype, subscriptiondepth, keyword) <> (OfpubsubsubscriptionRow.tupled, OfpubsubsubscriptionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (serviceid.?, nodeid.?, id.?, jid.?, owner.?, state.?, deliver.?, digest.?, digestFrequency.?, expire, includebody.?, showvalues, subscriptiontype.?, subscriptiondepth.?, keyword).shaped.<>({r=>import r._; _1.map(_=> OfpubsubsubscriptionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10, _11.get, _12, _13.get, _14.get, _15)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column serviceID DBType(VARCHAR), Length(100,true) */
    val serviceid: Column[String] = column[String]("serviceID", O.Length(100,varying=true))
    /** Database column nodeID DBType(VARCHAR), Length(100,true) */
    val nodeid: Column[String] = column[String]("nodeID", O.Length(100,varying=true))
    /** Database column id DBType(VARCHAR), Length(100,true) */
    val id: Column[String] = column[String]("id", O.Length(100,varying=true))
    /** Database column jid DBType(VARCHAR), Length(255,true) */
    val jid: Column[String] = column[String]("jid", O.Length(255,varying=true))
    /** Database column owner DBType(VARCHAR), Length(255,true) */
    val owner: Column[String] = column[String]("owner", O.Length(255,varying=true))
    /** Database column state DBType(VARCHAR), Length(15,true) */
    val state: Column[String] = column[String]("state", O.Length(15,varying=true))
    /** Database column deliver DBType(TINYINT) */
    val deliver: Column[Byte] = column[Byte]("deliver")
    /** Database column digest DBType(TINYINT) */
    val digest: Column[Byte] = column[Byte]("digest")
    /** Database column digest_frequency DBType(INT) */
    val digestFrequency: Column[Int] = column[Int]("digest_frequency")
    /** Database column expire DBType(CHAR), Length(15,false), Default(None) */
    val expire: Column[Option[String]] = column[Option[String]]("expire", O.Length(15,varying=false), O.Default(None))
    /** Database column includeBody DBType(TINYINT) */
    val includebody: Column[Byte] = column[Byte]("includeBody")
    /** Database column showValues DBType(VARCHAR), Length(30,true), Default(None) */
    val showvalues: Column[Option[String]] = column[Option[String]]("showValues", O.Length(30,varying=true), O.Default(None))
    /** Database column subscriptionType DBType(VARCHAR), Length(10,true) */
    val subscriptiontype: Column[String] = column[String]("subscriptionType", O.Length(10,varying=true))
    /** Database column subscriptionDepth DBType(TINYINT) */
    val subscriptiondepth: Column[Byte] = column[Byte]("subscriptionDepth")
    /** Database column keyword DBType(VARCHAR), Length(200,true), Default(None) */
    val keyword: Column[Option[String]] = column[Option[String]]("keyword", O.Length(200,varying=true), O.Default(None))
    
    /** Primary key of Ofpubsubsubscription (database name ofPubsubSubscription_PK) */
    val pk = primaryKey("ofPubsubSubscription_PK", (serviceid, nodeid, id))
  }
  /** Collection-like TableQuery object for table Ofpubsubsubscription */
  lazy val Ofpubsubsubscription = new TableQuery(tag => new Ofpubsubsubscription(tag))
  
  /** Entity class storing rows of table Ofremoteserverconf
   *  @param xmppdomain Database column xmppDomain DBType(VARCHAR), PrimaryKey, Length(255,true)
   *  @param remoteport Database column remotePort DBType(INT), Default(None)
   *  @param permission Database column permission DBType(VARCHAR), Length(10,true) */
  case class OfremoteserverconfRow(xmppdomain: String, remoteport: Option[Int] = None, permission: String)
  /** GetResult implicit for fetching OfremoteserverconfRow objects using plain SQL queries */
  implicit def GetResultOfremoteserverconfRow(implicit e0: GR[String], e1: GR[Option[Int]]): GR[OfremoteserverconfRow] = GR{
    prs => import prs._
    OfremoteserverconfRow.tupled((<<[String], <<?[Int], <<[String]))
  }
  /** Table description of table ofRemoteServerConf. Objects of this class serve as prototypes for rows in queries. */
  class Ofremoteserverconf(_tableTag: Tag) extends Table[OfremoteserverconfRow](_tableTag, "ofRemoteServerConf") {
    def * = (xmppdomain, remoteport, permission) <> (OfremoteserverconfRow.tupled, OfremoteserverconfRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (xmppdomain.?, remoteport, permission.?).shaped.<>({r=>import r._; _1.map(_=> OfremoteserverconfRow.tupled((_1.get, _2, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column xmppDomain DBType(VARCHAR), PrimaryKey, Length(255,true) */
    val xmppdomain: Column[String] = column[String]("xmppDomain", O.PrimaryKey, O.Length(255,varying=true))
    /** Database column remotePort DBType(INT), Default(None) */
    val remoteport: Column[Option[Int]] = column[Option[Int]]("remotePort", O.Default(None))
    /** Database column permission DBType(VARCHAR), Length(10,true) */
    val permission: Column[String] = column[String]("permission", O.Length(10,varying=true))
  }
  /** Collection-like TableQuery object for table Ofremoteserverconf */
  lazy val Ofremoteserverconf = new TableQuery(tag => new Ofremoteserverconf(tag))
  
  /** Entity class storing rows of table Ofroster
   *  @param rosterid Database column rosterID DBType(BIGINT), PrimaryKey
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param jid Database column jid DBType(VARCHAR), Length(1024,true)
   *  @param sub Database column sub DBType(TINYINT)
   *  @param ask Database column ask DBType(TINYINT)
   *  @param recv Database column recv DBType(TINYINT)
   *  @param nick Database column nick DBType(VARCHAR), Length(255,true), Default(None) */
  case class OfrosterRow(rosterid: Long, username: String, jid: String, sub: Byte, ask: Byte, recv: Byte, nick: Option[String] = None)
  /** GetResult implicit for fetching OfrosterRow objects using plain SQL queries */
  implicit def GetResultOfrosterRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Byte], e3: GR[Option[String]]): GR[OfrosterRow] = GR{
    prs => import prs._
    OfrosterRow.tupled((<<[Long], <<[String], <<[String], <<[Byte], <<[Byte], <<[Byte], <<?[String]))
  }
  /** Table description of table ofRoster. Objects of this class serve as prototypes for rows in queries. */
  class Ofroster(_tableTag: Tag) extends Table[OfrosterRow](_tableTag, "ofRoster") {
    def * = (rosterid, username, jid, sub, ask, recv, nick) <> (OfrosterRow.tupled, OfrosterRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (rosterid.?, username.?, jid.?, sub.?, ask.?, recv.?, nick).shaped.<>({r=>import r._; _1.map(_=> OfrosterRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column rosterID DBType(BIGINT), PrimaryKey */
    val rosterid: Column[Long] = column[Long]("rosterID", O.PrimaryKey)
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column jid DBType(VARCHAR), Length(1024,true) */
    val jid: Column[String] = column[String]("jid", O.Length(1024,varying=true))
    /** Database column sub DBType(TINYINT) */
    val sub: Column[Byte] = column[Byte]("sub")
    /** Database column ask DBType(TINYINT) */
    val ask: Column[Byte] = column[Byte]("ask")
    /** Database column recv DBType(TINYINT) */
    val recv: Column[Byte] = column[Byte]("recv")
    /** Database column nick DBType(VARCHAR), Length(255,true), Default(None) */
    val nick: Column[Option[String]] = column[Option[String]]("nick", O.Length(255,varying=true), O.Default(None))
    
    /** Index over (jid) (database name ofRoster_jid_idx) */
    val index1 = index("ofRoster_jid_idx", jid)
    /** Index over (username) (database name ofRoster_unameid_idx) */
    val index2 = index("ofRoster_unameid_idx", username)
  }
  /** Collection-like TableQuery object for table Ofroster */
  lazy val Ofroster = new TableQuery(tag => new Ofroster(tag))
  
  /** Entity class storing rows of table Ofrostergroups
   *  @param rosterid Database column rosterID DBType(BIGINT)
   *  @param rank Database column rank DBType(TINYINT)
   *  @param groupname Database column groupName DBType(VARCHAR), Length(255,true) */
  case class OfrostergroupsRow(rosterid: Long, rank: Byte, groupname: String)
  /** GetResult implicit for fetching OfrostergroupsRow objects using plain SQL queries */
  implicit def GetResultOfrostergroupsRow(implicit e0: GR[Long], e1: GR[Byte], e2: GR[String]): GR[OfrostergroupsRow] = GR{
    prs => import prs._
    OfrostergroupsRow.tupled((<<[Long], <<[Byte], <<[String]))
  }
  /** Table description of table ofRosterGroups. Objects of this class serve as prototypes for rows in queries. */
  class Ofrostergroups(_tableTag: Tag) extends Table[OfrostergroupsRow](_tableTag, "ofRosterGroups") {
    def * = (rosterid, rank, groupname) <> (OfrostergroupsRow.tupled, OfrostergroupsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (rosterid.?, rank.?, groupname.?).shaped.<>({r=>import r._; _1.map(_=> OfrostergroupsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column rosterID DBType(BIGINT) */
    val rosterid: Column[Long] = column[Long]("rosterID")
    /** Database column rank DBType(TINYINT) */
    val rank: Column[Byte] = column[Byte]("rank")
    /** Database column groupName DBType(VARCHAR), Length(255,true) */
    val groupname: Column[String] = column[String]("groupName", O.Length(255,varying=true))
    
    /** Primary key of Ofrostergroups (database name ofRosterGroups_PK) */
    val pk = primaryKey("ofRosterGroups_PK", (rosterid, rank))
    
    /** Index over (rosterid) (database name ofRosterGroup_rosterid_idx) */
    val index1 = index("ofRosterGroup_rosterid_idx", rosterid)
  }
  /** Collection-like TableQuery object for table Ofrostergroups */
  lazy val Ofrostergroups = new TableQuery(tag => new Ofrostergroups(tag))
  
  /** Entity class storing rows of table Ofsaslauthorized
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param principal Database column principal DBType(TEXT), Length(65535,true) */
  case class OfsaslauthorizedRow(username: String, principal: String)
  /** GetResult implicit for fetching OfsaslauthorizedRow objects using plain SQL queries */
  implicit def GetResultOfsaslauthorizedRow(implicit e0: GR[String]): GR[OfsaslauthorizedRow] = GR{
    prs => import prs._
    OfsaslauthorizedRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table ofSASLAuthorized. Objects of this class serve as prototypes for rows in queries. */
  class Ofsaslauthorized(_tableTag: Tag) extends Table[OfsaslauthorizedRow](_tableTag, "ofSASLAuthorized") {
    def * = (username, principal) <> (OfsaslauthorizedRow.tupled, OfsaslauthorizedRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, principal.?).shaped.<>({r=>import r._; _1.map(_=> OfsaslauthorizedRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column principal DBType(TEXT), Length(65535,true) */
    val principal: Column[String] = column[String]("principal", O.Length(65535,varying=true))
    
    /** Primary key of Ofsaslauthorized (database name ofSASLAuthorized_PK) */
    val pk = primaryKey("ofSASLAuthorized_PK", (username, principal))
  }
  /** Collection-like TableQuery object for table Ofsaslauthorized */
  lazy val Ofsaslauthorized = new TableQuery(tag => new Ofsaslauthorized(tag))
  
  /** Entity class storing rows of table Ofsecurityauditlog
   *  @param msgid Database column msgID DBType(BIGINT), PrimaryKey
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param entrystamp Database column entryStamp DBType(BIGINT)
   *  @param summary Database column summary DBType(VARCHAR), Length(255,true)
   *  @param node Database column node DBType(VARCHAR), Length(255,true)
   *  @param details Database column details DBType(TEXT), Length(65535,true), Default(None) */
  case class OfsecurityauditlogRow(msgid: Long, username: String, entrystamp: Long, summary: String, node: String, details: Option[String] = None)
  /** GetResult implicit for fetching OfsecurityauditlogRow objects using plain SQL queries */
  implicit def GetResultOfsecurityauditlogRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[OfsecurityauditlogRow] = GR{
    prs => import prs._
    OfsecurityauditlogRow.tupled((<<[Long], <<[String], <<[Long], <<[String], <<[String], <<?[String]))
  }
  /** Table description of table ofSecurityAuditLog. Objects of this class serve as prototypes for rows in queries. */
  class Ofsecurityauditlog(_tableTag: Tag) extends Table[OfsecurityauditlogRow](_tableTag, "ofSecurityAuditLog") {
    def * = (msgid, username, entrystamp, summary, node, details) <> (OfsecurityauditlogRow.tupled, OfsecurityauditlogRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (msgid.?, username.?, entrystamp.?, summary.?, node.?, details).shaped.<>({r=>import r._; _1.map(_=> OfsecurityauditlogRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column msgID DBType(BIGINT), PrimaryKey */
    val msgid: Column[Long] = column[Long]("msgID", O.PrimaryKey)
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column entryStamp DBType(BIGINT) */
    val entrystamp: Column[Long] = column[Long]("entryStamp")
    /** Database column summary DBType(VARCHAR), Length(255,true) */
    val summary: Column[String] = column[String]("summary", O.Length(255,varying=true))
    /** Database column node DBType(VARCHAR), Length(255,true) */
    val node: Column[String] = column[String]("node", O.Length(255,varying=true))
    /** Database column details DBType(TEXT), Length(65535,true), Default(None) */
    val details: Column[Option[String]] = column[Option[String]]("details", O.Length(65535,varying=true), O.Default(None))
    
    /** Index over (entrystamp) (database name ofSecurityAuditLog_tstamp_idx) */
    val index1 = index("ofSecurityAuditLog_tstamp_idx", entrystamp)
    /** Index over (username) (database name ofSecurityAuditLog_uname_idx) */
    val index2 = index("ofSecurityAuditLog_uname_idx", username)
  }
  /** Collection-like TableQuery object for table Ofsecurityauditlog */
  lazy val Ofsecurityauditlog = new TableQuery(tag => new Ofsecurityauditlog(tag))
  
  /** Entity class storing rows of table Ofuser
   *  @param username Database column username DBType(VARCHAR), PrimaryKey, Length(64,true)
   *  @param plainpassword Database column plainPassword DBType(VARCHAR), Length(32,true), Default(None)
   *  @param encryptedpassword Database column encryptedPassword DBType(VARCHAR), Length(255,true), Default(None)
   *  @param name Database column name DBType(VARCHAR), Length(100,true), Default(None)
   *  @param email Database column email DBType(VARCHAR), Length(100,true), Default(None)
   *  @param creationdate Database column creationDate DBType(CHAR), Length(15,false)
   *  @param modificationdate Database column modificationDate DBType(CHAR), Length(15,false) */
  case class OfuserRow(username: String, plainpassword: Option[String] = None, encryptedpassword: Option[String] = None, name: Option[String] = None, email: Option[String] = None, creationdate: String, modificationdate: String)
  /** GetResult implicit for fetching OfuserRow objects using plain SQL queries */
  implicit def GetResultOfuserRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[OfuserRow] = GR{
    prs => import prs._
    OfuserRow.tupled((<<[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[String], <<[String]))
  }
  /** Table description of table ofUser. Objects of this class serve as prototypes for rows in queries. */
  class Ofuser(_tableTag: Tag) extends Table[OfuserRow](_tableTag, "ofUser") {
    def * = (username, plainpassword, encryptedpassword, name, email, creationdate, modificationdate) <> (OfuserRow.tupled, OfuserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, plainpassword, encryptedpassword, name, email, creationdate.?, modificationdate.?).shaped.<>({r=>import r._; _1.map(_=> OfuserRow.tupled((_1.get, _2, _3, _4, _5, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), PrimaryKey, Length(64,true) */
    val username: Column[String] = column[String]("username", O.PrimaryKey, O.Length(64,varying=true))
    /** Database column plainPassword DBType(VARCHAR), Length(32,true), Default(None) */
    val plainpassword: Column[Option[String]] = column[Option[String]]("plainPassword", O.Length(32,varying=true), O.Default(None))
    /** Database column encryptedPassword DBType(VARCHAR), Length(255,true), Default(None) */
    val encryptedpassword: Column[Option[String]] = column[Option[String]]("encryptedPassword", O.Length(255,varying=true), O.Default(None))
    /** Database column name DBType(VARCHAR), Length(100,true), Default(None) */
    val name: Column[Option[String]] = column[Option[String]]("name", O.Length(100,varying=true), O.Default(None))
    /** Database column email DBType(VARCHAR), Length(100,true), Default(None) */
    val email: Column[Option[String]] = column[Option[String]]("email", O.Length(100,varying=true), O.Default(None))
    /** Database column creationDate DBType(CHAR), Length(15,false) */
    val creationdate: Column[String] = column[String]("creationDate", O.Length(15,varying=false))
    /** Database column modificationDate DBType(CHAR), Length(15,false) */
    val modificationdate: Column[String] = column[String]("modificationDate", O.Length(15,varying=false))
    
    /** Index over (creationdate) (database name ofUser_cDate_idx) */
    val index1 = index("ofUser_cDate_idx", creationdate)
  }
  /** Collection-like TableQuery object for table Ofuser */
  lazy val Ofuser = new TableQuery(tag => new Ofuser(tag))
  
  /** Entity class storing rows of table Ofuserflag
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param starttime Database column startTime DBType(CHAR), Length(15,false), Default(None)
   *  @param endtime Database column endTime DBType(CHAR), Length(15,false), Default(None) */
  case class OfuserflagRow(username: String, name: String, starttime: Option[String] = None, endtime: Option[String] = None)
  /** GetResult implicit for fetching OfuserflagRow objects using plain SQL queries */
  implicit def GetResultOfuserflagRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[OfuserflagRow] = GR{
    prs => import prs._
    OfuserflagRow.tupled((<<[String], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table ofUserFlag. Objects of this class serve as prototypes for rows in queries. */
  class Ofuserflag(_tableTag: Tag) extends Table[OfuserflagRow](_tableTag, "ofUserFlag") {
    def * = (username, name, starttime, endtime) <> (OfuserflagRow.tupled, OfuserflagRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, name.?, starttime, endtime).shaped.<>({r=>import r._; _1.map(_=> OfuserflagRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column startTime DBType(CHAR), Length(15,false), Default(None) */
    val starttime: Column[Option[String]] = column[Option[String]]("startTime", O.Length(15,varying=false), O.Default(None))
    /** Database column endTime DBType(CHAR), Length(15,false), Default(None) */
    val endtime: Column[Option[String]] = column[Option[String]]("endTime", O.Length(15,varying=false), O.Default(None))
    
    /** Primary key of Ofuserflag (database name ofUserFlag_PK) */
    val pk = primaryKey("ofUserFlag_PK", (username, name))
    
    /** Index over (endtime) (database name ofUserFlag_eTime_idx) */
    val index1 = index("ofUserFlag_eTime_idx", endtime)
    /** Index over (starttime) (database name ofUserFlag_sTime_idx) */
    val index2 = index("ofUserFlag_sTime_idx", starttime)
  }
  /** Collection-like TableQuery object for table Ofuserflag */
  lazy val Ofuserflag = new TableQuery(tag => new Ofuserflag(tag))
  
  /** Entity class storing rows of table Ofuserprop
   *  @param username Database column username DBType(VARCHAR), Length(64,true)
   *  @param name Database column name DBType(VARCHAR), Length(100,true)
   *  @param propvalue Database column propValue DBType(TEXT), Length(65535,true) */
  case class OfuserpropRow(username: String, name: String, propvalue: String)
  /** GetResult implicit for fetching OfuserpropRow objects using plain SQL queries */
  implicit def GetResultOfuserpropRow(implicit e0: GR[String]): GR[OfuserpropRow] = GR{
    prs => import prs._
    OfuserpropRow.tupled((<<[String], <<[String], <<[String]))
  }
  /** Table description of table ofUserProp. Objects of this class serve as prototypes for rows in queries. */
  class Ofuserprop(_tableTag: Tag) extends Table[OfuserpropRow](_tableTag, "ofUserProp") {
    def * = (username, name, propvalue) <> (OfuserpropRow.tupled, OfuserpropRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, name.?, propvalue.?).shaped.<>({r=>import r._; _1.map(_=> OfuserpropRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), Length(64,true) */
    val username: Column[String] = column[String]("username", O.Length(64,varying=true))
    /** Database column name DBType(VARCHAR), Length(100,true) */
    val name: Column[String] = column[String]("name", O.Length(100,varying=true))
    /** Database column propValue DBType(TEXT), Length(65535,true) */
    val propvalue: Column[String] = column[String]("propValue", O.Length(65535,varying=true))
    
    /** Primary key of Ofuserprop (database name ofUserProp_PK) */
    val pk = primaryKey("ofUserProp_PK", (username, name))
  }
  /** Collection-like TableQuery object for table Ofuserprop */
  lazy val Ofuserprop = new TableQuery(tag => new Ofuserprop(tag))
  
  /** Entity class storing rows of table Ofvcard
   *  @param username Database column username DBType(VARCHAR), PrimaryKey, Length(64,true)
   *  @param vcard Database column vcard DBType(MEDIUMTEXT), Length(16777215,true) */
  case class OfvcardRow(username: String, vcard: String)
  /** GetResult implicit for fetching OfvcardRow objects using plain SQL queries */
  implicit def GetResultOfvcardRow(implicit e0: GR[String]): GR[OfvcardRow] = GR{
    prs => import prs._
    OfvcardRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table ofVCard. Objects of this class serve as prototypes for rows in queries. */
  class Ofvcard(_tableTag: Tag) extends Table[OfvcardRow](_tableTag, "ofVCard") {
    def * = (username, vcard) <> (OfvcardRow.tupled, OfvcardRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (username.?, vcard.?).shaped.<>({r=>import r._; _1.map(_=> OfvcardRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column username DBType(VARCHAR), PrimaryKey, Length(64,true) */
    val username: Column[String] = column[String]("username", O.PrimaryKey, O.Length(64,varying=true))
    /** Database column vcard DBType(MEDIUMTEXT), Length(16777215,true) */
    val vcard: Column[String] = column[String]("vcard", O.Length(16777215,varying=true))
  }
  /** Collection-like TableQuery object for table Ofvcard */
  lazy val Ofvcard = new TableQuery(tag => new Ofvcard(tag))
  
  /** Entity class storing rows of table Ofversion
   *  @param name Database column name DBType(VARCHAR), PrimaryKey, Length(50,true)
   *  @param version Database column version DBType(INT) */
  case class OfversionRow(name: String, version: Int)
  /** GetResult implicit for fetching OfversionRow objects using plain SQL queries */
  implicit def GetResultOfversionRow(implicit e0: GR[String], e1: GR[Int]): GR[OfversionRow] = GR{
    prs => import prs._
    OfversionRow.tupled((<<[String], <<[Int]))
  }
  /** Table description of table ofVersion. Objects of this class serve as prototypes for rows in queries. */
  class Ofversion(_tableTag: Tag) extends Table[OfversionRow](_tableTag, "ofVersion") {
    def * = (name, version) <> (OfversionRow.tupled, OfversionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (name.?, version.?).shaped.<>({r=>import r._; _1.map(_=> OfversionRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column name DBType(VARCHAR), PrimaryKey, Length(50,true) */
    val name: Column[String] = column[String]("name", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column version DBType(INT) */
    val version: Column[Int] = column[Int]("version")
  }
  /** Collection-like TableQuery object for table Ofversion */
  lazy val Ofversion = new TableQuery(tag => new Ofversion(tag))
  
  /** Entity class storing rows of table User
   *  @param userid Database column userid DBType(INT), AutoInc
   *  @param username Database column username DBType(VARCHAR), Length(30,true)
   *  @param password Database column password DBType(VARCHAR), Length(128,true)
   *  @param email Database column email DBType(VARCHAR), Length(100,true), Default(None)
   *  @param sex Database column sex DBType(VARCHAR), Length(2,true) */
  case class UserRow(userid: Int, username: String, password: String, email: Option[String] = None, sex: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<?[String], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, "user") {
    def * = (userid, username, password, email, sex) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (userid.?, username.?, password.?, email, sex.?).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column userid DBType(INT), AutoInc */
    val userid: Column[Int] = column[Int]("userid", O.AutoInc)
    /** Database column username DBType(VARCHAR), Length(30,true) */
    val username: Column[String] = column[String]("username", O.Length(30,varying=true))
    /** Database column password DBType(VARCHAR), Length(128,true) */
    val password: Column[String] = column[String]("password", O.Length(128,varying=true))
    /** Database column email DBType(VARCHAR), Length(100,true), Default(None) */
    val email: Column[Option[String]] = column[Option[String]]("email", O.Length(100,varying=true), O.Default(None))
    /** Database column sex DBType(VARCHAR), Length(2,true) */
    val sex: Column[String] = column[String]("sex", O.Length(2,varying=true))
    
    /** Primary key of User (database name user_PK) */
    val pk = primaryKey("user_PK", (userid, username))
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}
package cn.changhong.persistent.Tables
// AUTO-GENERATED Slick data model

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
object Tables {
  implicit val profile: scala.slick.driver.JdbcProfile = scala.slick.driver.MySQLDriver
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = AccessToken.ddl ++ AutoCode.ddl ++ Client.ddl ++ User.ddl
  
  /** Entity class storing rows of table AccessToken
   *  @param accessToken Database column access_token DBType(VARCHAR), PrimaryKey, Length(100,true)
   *  @param refreshToken Database column refresh_token DBType(VARCHAR), Length(100,true)
   *  @param userId Database column user_id DBType(BIGINT)
   *  @param clientId Database column client_id DBType(VARCHAR), Length(200,true)
   *  @param createdAt Database column created_at DBType(INT)
   *  @param expiresIn Database column expires_in DBType(INT)
   *  @param clientType Database column client_type DBType(INT)
   *  @param tokenType Database column token_type DBType(INT) */
  case class AccessTokenRow(accessToken: String, refreshToken: String, userId: Long, clientId: String, createdAt: Long, expiresIn: Int, clientType: Int, tokenType: Int)
  /** GetResult implicit for fetching AccessTokenRow objects using plain SQL queries */
  implicit def GetResultAccessTokenRow(implicit e0: GR[String], e1: GR[Long], e2: GR[Int]): GR[AccessTokenRow] = GR{
    prs => import prs._
    AccessTokenRow.tupled((<<[String], <<[String], <<[Long], <<[String], <<[Int], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table access_token. Objects of this class serve as prototypes for rows in queries. */
  class AccessToken(_tableTag: Tag) extends Table[AccessTokenRow](_tableTag, "access_token") {
    def * = (accessToken, refreshToken, userId, clientId, createdAt, expiresIn, clientType, tokenType) <> (AccessTokenRow.tupled, AccessTokenRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (accessToken.?, refreshToken.?, userId.?, clientId.?, createdAt.?, expiresIn.?, clientType.?, tokenType.?).shaped.<>({r=>import r._; _1.map(_=> AccessTokenRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column access_token DBType(VARCHAR), PrimaryKey, Length(100,true) */
    val accessToken: Column[String] = column[String]("access_token", O.PrimaryKey, O.Length(100,varying=true))
    /** Database column refresh_token DBType(VARCHAR), Length(100,true) */
    val refreshToken: Column[String] = column[String]("refresh_token", O.Length(100,varying=true))
    /** Database column user_id DBType(BIGINT) */
    val userId: Column[Long] = column[Long]("user_id")
    /** Database column client_id DBType(VARCHAR), Length(200,true) */
    val clientId: Column[String] = column[String]("client_id", O.Length(200,varying=true))
    /** Database column created_at DBType(INT) */
    val createdAt: Column[Long] = column[Long]("created_at")
    /** Database column expires_in DBType(INT) */
    val expiresIn: Column[Int] = column[Int]("expires_in")
    /** Database column client_type DBType(INT) */
    val clientType: Column[Int] = column[Int]("client_type")
    /** Database column token_type DBType(INT) */
    val tokenType: Column[Int] = column[Int]("token_type")
    
    /** Foreign key referencing User (database name fk_access_token_user_id) */
    lazy val userFk = foreignKey("fk_access_token_user_id", userId, User)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AccessToken */
  lazy val AccessToken = new TableQuery(tag => new AccessToken(tag))
  
  /** Entity class storing rows of table AutoCode
   *  @param authorizationCode Database column authorization_code DBType(VARCHAR), PrimaryKey, Length(80,true)
   *  @param userId Database column user_id DBType(BIGINT)
   *  @param redirectUri Database column redirect_uri DBType(VARCHAR), Length(400,true), Default(None)
   *  @param createdAt Database column created_at DBType(INT)
   *  @param scope Database column scope DBType(VARCHAR), Length(200,true)
   *  @param clientId Database column client_id DBType(VARCHAR), Length(200,true)
   *  @param expiresIn Database column expires_in DBType(INT) */
  case class AutoCodeRow(authorizationCode: String, userId: Long, redirectUri: Option[String] = None, createdAt: Int, scope: String, clientId: String, expiresIn: Int)
  /** GetResult implicit for fetching AutoCodeRow objects using plain SQL queries */
  implicit def GetResultAutoCodeRow(implicit e0: GR[String], e1: GR[Long], e2: GR[Option[String]], e3: GR[Int]): GR[AutoCodeRow] = GR{
    prs => import prs._
    AutoCodeRow.tupled((<<[String], <<[Long], <<?[String], <<[Int], <<[String], <<[String], <<[Int]))
  }
  /** Table description of table auto_code. Objects of this class serve as prototypes for rows in queries. */
  class AutoCode(_tableTag: Tag) extends Table[AutoCodeRow](_tableTag, "auto_code") {
    def * = (authorizationCode, userId, redirectUri, createdAt, scope, clientId, expiresIn) <> (AutoCodeRow.tupled, AutoCodeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (authorizationCode.?, userId.?, redirectUri, createdAt.?, scope.?, clientId.?, expiresIn.?).shaped.<>({r=>import r._; _1.map(_=> AutoCodeRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column authorization_code DBType(VARCHAR), PrimaryKey, Length(80,true) */
    val authorizationCode: Column[String] = column[String]("authorization_code", O.PrimaryKey, O.Length(80,varying=true))
    /** Database column user_id DBType(BIGINT) */
    val userId: Column[Long] = column[Long]("user_id")
    /** Database column redirect_uri DBType(VARCHAR), Length(400,true), Default(None) */
    val redirectUri: Column[Option[String]] = column[Option[String]]("redirect_uri", O.Length(400,varying=true), O.Default(None))
    /** Database column created_at DBType(INT) */
    val createdAt: Column[Int] = column[Int]("created_at")
    /** Database column scope DBType(VARCHAR), Length(200,true) */
    val scope: Column[String] = column[String]("scope", O.Length(200,varying=true))
    /** Database column client_id DBType(VARCHAR), Length(200,true) */
    val clientId: Column[String] = column[String]("client_id", O.Length(200,varying=true))
    /** Database column expires_in DBType(INT) */
    val expiresIn: Column[Int] = column[Int]("expires_in")
    
    /** Foreign key referencing Client (database name fk_auth_code_client_id) */
    lazy val clientFk = foreignKey("fk_auth_code_client_id", clientId, Client)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing User (database name fk_auth_code_user_id) */
    lazy val userFk = foreignKey("fk_auth_code_user_id", userId, User)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table AutoCode */
  lazy val AutoCode = new TableQuery(tag => new AutoCode(tag))
  
  /** Entity class storing rows of table Client
   *  @param id Database column id DBType(VARCHAR), PrimaryKey, Length(200,true)
   *  @param secret Database column secret DBType(VARCHAR), Length(80,true)
   *  @param redirectUri Database column redirect_uri DBType(VARCHAR), Length(400,true), Default(None)
   *  @param scope Database column scope DBType(VARCHAR), Length(200,true), Default(None) */
  case class ClientRow(id: String, secret: String, redirectUri: Option[String] = None, scope: Option[String] = None)
  /** GetResult implicit for fetching ClientRow objects using plain SQL queries */
  implicit def GetResultClientRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[ClientRow] = GR{
    prs => import prs._
    ClientRow.tupled((<<[String], <<[String], <<?[String], <<?[String]))
  }
  /** Table description of table client. Objects of this class serve as prototypes for rows in queries. */
  class Client(_tableTag: Tag) extends Table[ClientRow](_tableTag, "client") {
    def * = (id, secret, redirectUri, scope) <> (ClientRow.tupled, ClientRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, secret.?, redirectUri, scope).shaped.<>({r=>import r._; _1.map(_=> ClientRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(VARCHAR), PrimaryKey, Length(200,true) */
    val id: Column[String] = column[String]("id", O.PrimaryKey, O.Length(200,varying=true))
    /** Database column secret DBType(VARCHAR), Length(80,true) */
    val secret: Column[String] = column[String]("secret", O.Length(80,varying=true))
    /** Database column redirect_uri DBType(VARCHAR), Length(400,true), Default(None) */
    val redirectUri: Column[Option[String]] = column[Option[String]]("redirect_uri", O.Length(400,varying=true), O.Default(None))
    /** Database column scope DBType(VARCHAR), Length(200,true), Default(None) */
    val scope: Column[Option[String]] = column[Option[String]]("scope", O.Length(200,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table Client */
  lazy val Client = new TableQuery(tag => new Client(tag))
  
  /** Entity class storing rows of table User
   *  @param id Database column id DBType(BIGINT), AutoInc, PrimaryKey
   *  @param username Database column username DBType(VARCHAR), Length(20,true)
   *  @param iphone Database column iphone DBType(VARCHAR), Length(15,true), Default(None)
   *  @param email Database column email DBType(VARCHAR), Length(40,true), Default(None)
   *  @param password Database column password DBType(VARCHAR), Length(20,true) */
  case class UserRow(id: Long, username: String, iphone: Option[String] = None, email: Option[String] = None, password: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Long], <<[String], <<?[String], <<?[String], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, "user") {
    def * = (id, username, iphone, email, password) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, username.?, iphone, email, password.?).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(BIGINT), AutoInc, PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username DBType(VARCHAR), Length(20,true) */
    val username: Column[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column iphone DBType(VARCHAR), Length(15,true), Default(None) */
    val iphone: Column[Option[String]] = column[Option[String]]("iphone", O.Length(15,varying=true), O.Default(None))
    /** Database column email DBType(VARCHAR), Length(40,true), Default(None) */
    val email: Column[Option[String]] = column[Option[String]]("email", O.Length(40,varying=true), O.Default(None))
    /** Database column password DBType(VARCHAR), Length(20,true) */
    val password: Column[String] = column[String]("password", O.Length(20,varying=true))
    
    /** Uniqueness Index over (email) (database name ix_email_unique) */
    val index1 = index("ix_email_unique", email, unique=true)
    /** Uniqueness Index over (iphone) (database name ix_iphone_unique) */
    val index2 = index("ix_iphone_unique", iphone, unique=true)
    /** Uniqueness Index over (username) (database name ix_username_unique) */
    val index3 = index("ix_username_unique", username, unique=true)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}
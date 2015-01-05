import sbt._
object MyBuild extends Build{
  lazy val root = Project("root",file("."))
  lazy val orm:Project=Project("orm",file("orm")) dependsOn(util % "test->compile")
  lazy val controller:Project=Project("controller",file("Controllers")) aggregate(orm,util) dependsOn(orm % "test->compile",util % "test->compile")
  lazy val util:Project=Project("util",file("Util"))
  lazy val router:Project=Project("router",file("Router")) dependsOn(controller % "test->compile",util % "test->compile")
}
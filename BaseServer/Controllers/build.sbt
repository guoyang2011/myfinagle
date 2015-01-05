name := "Controllers"

version := "1.0"

libraryDependencies += "com.twitter" % "finagle-http_2.10" % "6.22.0"

libraryDependencies += "com.twitter" % "util-core_2.10" % "6.22.0"

libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.22.0"

libraryDependencies += "net.liftweb" % "lift-json_2.10" % "3.0-M1" withSources()

libraryDependencies += "com.typesafe.slick" % "slick-codegen_2.10" % "2.1.0"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.33"

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.4"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.4"

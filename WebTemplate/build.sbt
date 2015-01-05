import com.github.retronym.SbtOneJar._
oneJarSettings

name := "WebTemplate"

version := "1.0"

libraryDependencies += "com.twitter" % "finagle-http_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "util-core_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "io.netty" % "netty" % "3.9.4.final" withSources() withJavadoc()

libraryDependencies += "net.liftweb" % "lift-json_2.10" % "3.0-M1" withSources() withJavadoc()

libraryDependencies += "com.typesafe.slick" % "slick-codegen_2.10" % "2.1.0" withSources() withJavadoc()

libraryDependencies += "com.typesafe.slick" % "slick_2.10" % "2.1.0" withSources() withJavadoc()

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.33"

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.4" withSources() withJavadoc()

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.4" withSources() withJavadoc()

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.6.4" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-redis_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "redis.clients" % "jedis" % "2.6.1" withSources() withJavadoc()

libraryDependencies += "org.apache.commons" % "commons-pool2" % "2.0" withSources() withJavadoc()
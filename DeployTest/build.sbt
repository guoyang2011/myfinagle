import com.github.retronym.SbtOneJar._

oneJarSettings

name := "DeployTest"

version := "1.0"

libraryDependencies += "nl.razko" %% "scraper" % "0.4.1" withSources() withJavadoc()

libraryDependencies += "net.liftweb" % "lift-json_2.10" % "3.0-M1" withSources() withJavadoc()

libraryDependencies += "org.im4java" % "im4java" % "1.4.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-http_2.10" % "6.22.0"// withSources() withJavadoc()

libraryDependencies += "com.twitter" % "util-core_2.10" % "6.22.0" //withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.22.0"

libraryDependencies += "org.apache.zookeeper" % "zookeeper" % "3.4.6" withSources() withJavadoc()

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "2.12.4" withSources() withJavadoc()


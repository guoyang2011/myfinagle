import com.github.retronym.SbtOneJar._

com.twitter.scrooge.ScroogeSBT.newSettings

oneJarSettings

name := "mesosframeworkdemo"

version := "1.0"

libraryDependencies += "org.apache.mesos" % "mesos" % "0.21.1" withJavadoc() withSources()

name := "ZipkinDemo"

version := "1.0"

libraryDependencies += "net.liftweb" % "lift-json_2.10" % "3.0-M1" withJavadoc() withSources()

libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.22.0" withJavadoc() withSources()

libraryDependencies += "com.twitter" % "finagle-http_2.10" % "6.22.0" withJavadoc() withSources()

libraryDependencies += "com.twitter" % "util-core_2.10" % "6.22.0" withJavadoc() withSources()

libraryDependencies += "com.twitter" % "scrooge-core_2.10" % "3.17.0" withJavadoc() withSources()

libraryDependencies += "com.twitter" % "finagle-zipkin_2.10" % "6.22.0" withJavadoc() withSources()

libraryDependencies += "org.apache.thrift" % "libthrift" % "0.9.1" withJavadoc() withSources()

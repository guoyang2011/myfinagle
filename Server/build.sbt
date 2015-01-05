name := "Server"

version := "1.0"

//libraryDependencies += "io.netty" % "netty-all" % "4.0.23.Final" withSources() withJavadoc()

libraryDependencies += "com.google.protobuf" % "protobuf-java" % "2.6.0" //withSources() withJavadoc()

libraryDependencies += "org.apache.spark" %% "spark-core" % "0.9.1" //withSources() withJavadoc()

resolvers +="Akka Repository" at "http://repo.akka.io/releases/"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-cluster" % "2.3.4")

//libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.2.0" withSources() withJavadoc()

//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "0.9.1" withSources() withJavadoc()

resolvers ++= Seq(
  "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/")

libraryDependencies ++= Seq(
  "com.chuusai" % "shapeless_2.10.4" % "2.0.0"
  // "com.chuusai" % "shapeless" % "2.0.0" cross CrossVersion.full  // Alternatively ...
)

libraryDependencies += "com.typesafe.slick" % "slick_2.10" % "2.1.0" //withSources() withJavadoc()

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-http-core-experimental" % "0.4" )//withSources() withJavadoc())

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-stream-experimental" % "0.4" )//withSources() withJavadoc())

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.2.3" //withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka" % "akka-kernel_2.10" % "2.2.3" //withSources() withJavadoc()

libraryDependencies += "com.google.code.gson" % "gson" % "2.3" //withSources() withJavadoc()

libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.4.3" //withSources() withJavadoc()

//libraryDependencies += "net.liftweb" % "lift-json" % "2.0" withSources() withJavadoc()

libraryDependencies += "junit" % "junit" % "4.10"

libraryDependencies += "com.twitter" % "finagle-http_2.10" % "6.22.0"// withSources() withJavadoc()

libraryDependencies += "com.twitter" % "util-core_2.10" % "6.22.0" //withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.22.0"

//libraryDependencies += "com.twitter" % "finagle-thrift_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" %% "finagle-oauth2" % "0.1.3"

//libraryDependencies += "com.twitter" % "finagle-core_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-stream_2.10" % "6.22.0"

//libraryDependencies += "com.twitter" % "finagle-benchmark_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-commons-stats_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-exception_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-exp_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-httpx_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-kestrel_2.10" % "6.22.0" //withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-memcached_2.10" % "6.22.0" //withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-memcachedx_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-mux_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-mysql_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-native_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-protobuf_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-redis_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-serversets_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.twitter" % "finagle-spdy_2.10" % "6.22.0" //withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-stats_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-stress_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-swift_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-thrift_2.10" % "6.22.0" withSources() withJavadoc()

//libraryDependencies += "com.twitter" % "finagle-zipkin_2.10" % "6.22.0" withSources() withJavadoc()

libraryDependencies += "com.google.guava" % "guava" % "18.0" //withSources() withJavadoc()

libraryDependencies += "com.nulab-inc" % "scala-oauth2-core_2.10" % "0.10.0" //withSources() withJavadoc()


resolvers += "Finagle-OAuth2" at "http://repo.konfettin.ru"

libraryDependencies += "com.twitter" %% "finagle-oauth2" % "0.1.3" //withSources() withJavadoc()

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.4" //withSources() withJavadoc()


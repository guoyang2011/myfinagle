name := "SparkTemplate"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.2.0" withSources() withJavadoc()

libraryDependencies += "org.apache.spark" %%"spark-core_2.10" % "1.0.0" withSources() withJavadoc()

libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "1.0.0" withSources() withJavadoc()

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.0.0" withSources() withJavadoc()

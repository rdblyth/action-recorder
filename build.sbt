enablePlugins(JavaAppPackaging)

name := "action-service"
version := "1.0"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion       = "2.4.9"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.slick" %% "slick" % "3.1.1",
    "ch.qos.logback" % "logback-classic" % "1.1.1",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "com.h2database" % "h2" % "1.4.191"
  )
}

Revolver.settings

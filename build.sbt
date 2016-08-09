enablePlugins(JavaAppPackaging)

name := "action-service"
version := "1.0"
scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.3"
  val scalaTestV  = "2.2.6"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV,
    "com.typesafe.slick" %% "slick" % "3.1.1",
    //"ch.qos.logback" % "logback-classic" % "1.0.9",
    "org.slf4j" % "slf4j-nop" % "1.7.19",
    "org.scalatest"     %% "scalatest" % scalaTestV % "test",
    "com.h2database" % "h2" % "1.4.191"
  )
}

Revolver.settings

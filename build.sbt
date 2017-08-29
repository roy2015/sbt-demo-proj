name:="sbt-demo"
organization:="com.best.roy"
version:="0.0.1-SNAPSHOT"
scalaVersion:="2.10.6"

resolvers += Resolver.mavenLocal


libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-lang3" % "3.4" ,
//  "com.best.v8" % "v8-biz-core" % "1.2.7",
  "org.scala-lang" % "scala-actors" % "2.10.6",
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.9",
  "com.typesafe.akka" % "akka-remote_2.10" % "2.3.9"
//  "com.eed3si9n" %% "sbt-assembly" % "0.13.0"
)

mainClass in assembly := Some( "exercise.akka.Worker")


name := """backend"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.7"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.postgresql" % "postgresql" % "42.3.1"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

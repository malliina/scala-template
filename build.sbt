lazy val template = project
  .in(file("."))

version := "0.0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.7" % Test
)

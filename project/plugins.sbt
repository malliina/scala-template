scalaVersion := "2.12.13"

Seq(
  "ch.epfl.scala" % "sbt-bloop" % "1.4.8",
  "org.scalameta" % "sbt-scalafmt" % "2.4.2"
) map addSbtPlugin

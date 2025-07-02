scalaVersion := "2.12.20"

val versions = new {
  val crossProject = "1.3.2"
}

Seq(
  "org.scala-native" % "sbt-scala-native" % "0.5.8",
  "org.portable-scala" % "sbt-scalajs-crossproject" % versions.crossProject,
  "org.portable-scala" % "sbt-scala-native-crossproject" % versions.crossProject,
  "org.scala-js" % "sbt-scalajs" % "1.19.0",
  "org.scalameta" % "sbt-scalafmt" % "2.5.4"
) map addSbtPlugin

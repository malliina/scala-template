scalaVersion := "2.12.17"

Seq(
  "org.scala-native" % "sbt-scala-native" % "0.4.11",
  "org.portable-scala" % "sbt-scalajs-crossproject" % "1.2.0",
  "org.portable-scala" % "sbt-scala-native-crossproject" % "1.2.0",
  "org.scala-js" % "sbt-scalajs" % "1.13.0"
) map addSbtPlugin

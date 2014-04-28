import sbt._
import sbt.Keys._

/**
 * A scala build file template.
 */
object TemplateBuild extends Build {

  lazy val template = Project("template", file(".")).settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    scalaVersion := "2.11.0",
    fork in Test := true,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.1.3" % "test"
    )
  )
}
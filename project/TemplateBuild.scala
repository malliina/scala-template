import sbt._
import sbt.Keys._

/**
 * A scala build file template.
 */
object TemplateBuild extends Build {

  lazy val template = Project("template", file(".")).settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    scalaVersion := "2.10.4",
    fork in Test := true,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.0" % "test"
    )
  )
}
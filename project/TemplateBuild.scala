import sbt._
import sbt.Keys._

/**
 * A scala build file template.
 */
object TemplateBuild extends Build {

  lazy val template = Project("template", file(".")).settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    scalaVersion := "2.10.3",
    fork in Test := true,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "1.9.1" % "test"
    )
  )
}
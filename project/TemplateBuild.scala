import com.mle.sbtutils.SbtUtils
import sbt._
import sbt.Keys._

/**
 * A scala build file template.
 */
object TemplateBuild extends Build {

  lazy val template = SbtUtils.testableProject("template").settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    scalaVersion := "2.11.1",
    fork in Test := true
  )
}
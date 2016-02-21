import com.malliina.sbtutils.SbtProjects
import sbt._
import sbt.Keys._

/**
  * A scala build file template.
  */
object TemplateBuild extends Build {

  lazy val template = SbtProjects.testableProject("template").settings(projectSettings: _*)

  lazy val projectSettings = Seq(
    version := "0.0.1",
    scalaVersion := "2.11.7",
    fork in Test := true,
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("malliina", "maven")
    ),
    libraryDependencies ++= Seq(
      "com.malliina" %% "util-base" % "0.9.0"
    )
  )
}

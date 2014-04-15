import sbt._

object BuildBuild extends Build {

  override lazy val settings = super.settings ++ Seq(
  ) ++ sbtPlugins

  def sbtPlugins = Seq(
    "com.timushev.sbt" % "sbt-updates" % "0.1.2"
  ) map addSbtPlugin

  override lazy val projects = Seq(root)
  lazy val root = Project("plugins", file("."))
}
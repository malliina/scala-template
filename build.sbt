val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.0.0",
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.26" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

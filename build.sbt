val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "0.23.0-RC1",
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.2" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

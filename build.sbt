val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.22" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

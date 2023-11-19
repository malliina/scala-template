val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.3.1",
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq(
      "com.malliina" %% "primitives" % "3.5.2",
      "co.fs2" %% "fs2-core" % "3.7.0",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

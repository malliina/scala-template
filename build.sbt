val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.2.2",
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq("core", "generic", "parser").map { s =>
      "io.circe" %% s"circe-$s" % "0.14.4"
    } ++ Seq(
      "com.malliina" %% "primitives" % "3.4.0",
      "co.fs2" %% "fs2-core" % "3.6.1",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

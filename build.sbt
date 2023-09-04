val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.2.2",
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq("core", "generic", "parser").map { s =>
      "io.circe" %% s"circe-$s" % "0.14.5"
    } ++ Seq(
      "com.malliina" %% "primitives" % "3.4.5",
      "co.fs2" %% "fs2-core" % "3.7.0",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

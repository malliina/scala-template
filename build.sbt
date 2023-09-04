val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.3.0",
    crossScalaVersions := Seq(scalaVersion.value),
    libraryDependencies ++= Seq("core", "generic", "parser").map { s =>
      "io.circe" %% s"circe-$s" % "0.14.6"
    } ++ Seq(
      "com.malliina" %% "primitives" % "3.4.5",
      "co.fs2" %% "fs2-io" % "3.9.1",
//      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

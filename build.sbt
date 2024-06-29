val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.4.0",
    libraryDependencies ++= Seq(
      "com.malliina" %% "primitives" % "3.6.0",
      "co.fs2" %% "fs2-core" % "3.10.2",
      "org.scalameta" %% "munit" % "1.0.0" % Test
    )
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
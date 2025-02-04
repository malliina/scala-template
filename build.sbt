val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.6.2",
    libraryDependencies ++= Seq(
      "com.malliina" %% "primitives" % "3.7.6",
      "co.fs2" %% "fs2-core" % "3.11.0",
      "org.scalameta" %% "munit" % "1.1.0" % Test
    )
  )

Global / onChangedBuildSource := ReloadOnSourceChanges

val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.4.0",
    libraryDependencies ++= Seq(
      "com.malliina" %% "primitives" % "3.7.3",
      "co.fs2" %% "fs2-core" % "3.11.0",
      "org.scalameta" %% "munit" % "1.0.1" % Test
    )
  )

Global / onChangedBuildSource := ReloadOnSourceChanges

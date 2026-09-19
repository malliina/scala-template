val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.9.0",
    libraryDependencies ++= Seq(
      "com.malliina" %% "primitives" % "6.15.4",
      "co.fs2" %% "fs2-core" % "3.14.0",
      "org.scalameta" %% "munit" % "1.3.6" % Test
    )
  )

Global / onChangedBuildSource := ReloadOnSourceChanges

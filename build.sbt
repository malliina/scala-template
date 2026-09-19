val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "3.9.0",
    libraryDependencies ++= Seq(
      "com.malliina" %% "primitives" % versions.primitives,
      "co.fs2" %% "fs2-core" % versions.fs2,
      "org.scalameta" %% "munit" % versions.munit % Test
    )
  )

Global / onChangedBuildSource := ReloadOnSourceChanges

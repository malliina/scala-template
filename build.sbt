val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    )
  )

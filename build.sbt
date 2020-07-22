val template = project
  .in(file("."))
  .settings(
    version := "0.0.1",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-async" % "1.0.0-M1",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided,
      "org.scalameta" %% "munit" % "0.7.9" % Test,
    ),
    testFrameworks += new TestFramework("munit.Framework"),
    scalacOptions += "-Xasync"
  )

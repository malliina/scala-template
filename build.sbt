inThisBuild(
  Seq(
    scalaVersion := "3.7.1"
  )
)

val app =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("."))
    .settings(
      version := "0.0.1",
      libraryDependencies ++= Seq(
        "org.scalameta" %% "munit" % "1.1.1" % Test
      )
    )

val js = app.js
val jvm = app.jvm
val native = app.native

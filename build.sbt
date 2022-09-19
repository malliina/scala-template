inThisBuild(
  Seq(
    scalaVersion := "3.1.0"
  )
)

val template =
  crossProject(JSPlatform, JVMPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("."))
    .settings(
      version := "0.0.1",
      scalaVersion := "3.1.0",
      libraryDependencies ++= Seq(
        "org.scalameta" %% "munit" % "0.7.29" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework")
    )

val js     = template.js
val jvm    = template.jvm
val native = template.native

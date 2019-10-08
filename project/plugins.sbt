resolvers += ivyResolver("malliina bintray sbt", url("https://dl.bintray.com/malliina/sbt-plugins/"))

def ivyResolver(name: String, repoUrl: sbt.URL) =
  Resolver.url(name, repoUrl)(Resolver.ivyStylePatterns)

Seq(
  "com.malliina" %% "sbt-utils-maven" % "0.14.2",
  "ch.epfl.scala" % "sbt-bloop" % "1.3.4"
) map addSbtPlugin

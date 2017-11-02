organization := "com.github.kanekotic"
name := "scala-local-toggle"
version := Option(System.getenv("TRAVIS_BUILD_NUMBER")).getOrElse("1.0-SNAPSHOT")
scalaVersion in ThisBuild := "2.12.3"

val mockitoVersion          = "2.10.0"
val scalaTestVersion        = "3.0.4"
val pureConfigVersion       = "0.8.0"

libraryDependencies in ThisBuild ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.mockito" % "mockito-core" % mockitoVersion % Test,
  "com.github.pureconfig" %% "pureconfig" % pureConfigVersion)

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

credentials += Credentials(
    realm = "Sonatype Nexus Repository Manager",
    host = "oss.sonatype.org",
    userName = System.getenv("NEXUS_DEPLOYMENT_USER"),
    passwd = System.getenv("NEXUS_DEPLOYMENT_PASSWORD")
  )

useGpg := false
pgpSecretRing := file("./deploy/my-key-sec.asc")
pgpPublicRing := file("./deploy/my-key-pub.asc")
pgpPassphrase := sys.env.get("GPG_PASS").map(_.toArray)

organization := "com.github.kanekotic"
name := "scala-local-toggle"
version := Option("0.0." + System.getenv("TRAVIS_BUILD_NUMBER")).getOrElse("1.0-SNAPSHOT")
scalaVersion in ThisBuild := "2.12.3"

val mockitoVersion          = "2.10.0"
val scalaTestVersion        = "3.0.4"
val pureConfigVersion       = "0.8.0"

libraryDependencies in ThisBuild ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.mockito" % "mockito-core" % mockitoVersion % Test,
  "com.github.pureconfig" %% "pureconfig" % pureConfigVersion)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  val profileM = sonatypeStagingRepositoryProfile.?.value

  if (isSnapshot.value) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  } else {
    val staged = profileM map { stagingRepoProfile =>
      "releases" at nexus +
        "service/local/staging/deployByRepositoryId/" +
        stagingRepoProfile.repositoryId
    }

    staged.orElse(Some("releases" at nexus + "service/local/staging/deploy/maven2"))
  }
}

publishArtifact in Test := false

credentials += Credentials(
    realm = "Sonatype Nexus Repository Manager",
    host = "oss.sonatype.org",
    userName = System.getenv("NEXUS_DEPLOYMENT_USER"),
    passwd = System.getenv("NEXUS_DEPLOYMENT_PASSWORD")
  )

releaseProcess := Seq[ReleaseStep](
  releaseStepCommand(s"""sonatypeOpen "${organization.value}" "Scala Local Feature Toggle""""),
  releaseStepCommand("publishSigned"),
  releaseStepCommand("sonatypeRelease")
)

useGpg := false
pgpSecretRing := file("./deploy/my-key-sec.asc")
pgpPublicRing := file("./deploy/my-key-pub.asc")
pgpPassphrase := sys.env.get("GPG_PASS").map(_.toArray)

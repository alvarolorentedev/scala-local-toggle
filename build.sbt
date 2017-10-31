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

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(
    realm = "Sonatype Nexus Repository Manager",
    host = "oss.sonatype.org",
    userName = System.getenv("NEXUS_DEPLOYMENT_USER"),
    passwd = System.getenv("NEXUS_DEPLOYMENT_PASSWORD")
  )

pomExtra := (
  <url>https://github.com/kanekotic/scala-local-toggle</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>https://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:kanekotic/scala-local-toggle.git</url>
      <connection>scm:git:git@github.com:kanekotic/scala-local-toggle.git</connection>
    </scm>
    <developers>
      <developer>
        <id>kanekotic</id>
        <name>Alvaro Perez</name>
        <url>https://github.com/kanekotic</url>
      </developer>
    </developers>)
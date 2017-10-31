organization := "kanekotic"
name := "scala-local-toggle"
version := Option(System.getenv("BUILD_VERSION")).getOrElse("1.0-SNAPSHOT")
scalaVersion in ThisBuild := "2.12.3"

val mockitoVersion          = "2.10.0"
val scalaTestVersion        = "3.0.4"
val pureConfigVersion       = "0.8.0"

libraryDependencies in ThisBuild ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.mockito" % "mockito-core" % mockitoVersion % Test,
  "com.github.pureconfig" %% "pureconfig" % pureConfigVersion)


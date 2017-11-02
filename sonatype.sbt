import sbt.url

publishMavenStyle := true

licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/kanekotic/scala-local-toggle"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/kanekotic/scala-local-toggle"),
    "scm:git@github.com:kanekotic/scala-local-toggle"
  ))

developers := List(
  Developer(
    id="kanekotic",
    name="Alvaro Perez",
    email="alvarojosepl@gmail.com",
    url=url("https://github.com/kanekotic")
  ),
  Developer(
    id="EmilyK",
    name="Emily Karungi",
    email="ekar45@gmail.com",
    url=url("https://github.com/EmilyK")
  )
)

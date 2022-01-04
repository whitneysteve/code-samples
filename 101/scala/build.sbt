libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

lazy val root = (project in file("."))
  .settings(
    name := "Code Samples 101",
    scalaVersion := "2.12.6"
  )
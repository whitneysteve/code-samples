resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"

lazy val root = (project in file("."))
  .settings(
    name := "Code Samples 101",
    scalaVersion := "2.13.8"
  )
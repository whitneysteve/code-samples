libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

resolvers in ThisBuild += "Artima Maven Repository" at "http://repo.artima.com/releases"

lazy val root = (project in file("."))
  .settings(
    name := "Code Samples 101",
    scalaVersion := "2.12.6"
  )
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "P1"
  )
libraryDependencies+="net.liftweb" %% "lift-json"% "3.5.0"
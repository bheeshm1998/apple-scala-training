ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .settings(
    name := "Day16Task"
  )

val sparkVersion = "3.5.1"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "mysql" % "mysql-connector-java" % "8.0.19",
)

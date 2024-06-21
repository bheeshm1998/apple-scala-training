ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "Day18and19Tasks"
  )

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.11.17"
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % "0.11.17"
libraryDependencies +=  "org.apache.spark" %% "spark-sql" % "3.5.1"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.19"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)


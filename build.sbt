name := """de.idnow.example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final"
)

libraryDependencies += "net.sf.dozer" % "dozer" % "5.5.1"

libraryDependencies += javaJdbc % Test

libraryDependencies += "org.dbunit" % "dbunit" % "2.4.9" % "test"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

name := """de.idnow.example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
libraryDependencies += scalacheck % Test

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final"
)

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test

libraryDependencies += "junit" % "junit" % "4.12" % "test"

libraryDependencies += "net.sf.dozer" % "dozer" % "5.5.1"

libraryDependencies += javaJdbc % Test

libraryDependencies += "org.dbunit" % "dbunit" % "2.4.9" % "test"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

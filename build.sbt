lazy val phdsl = (project in file(".")).settings(Seq(
  name := "phdsl",
  version := "0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"
  ),
  mainClass := Some("ru.eternity.embryo.application.Launcher")
))

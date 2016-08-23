package ru.eternity.embryo.phdsl.app

import java.io.PrintWriter

import ru.eternity.embryo.phdsl.exercises.mechanics.kinematics.basics.Simplest

import scala.xml.XML

object Launcher extends App {
  println("Physics DSL")

  import ru.eternity.embryo.phdsl._

  import Scalar._
  import Vector._
  import Measured._
  import Variable._

  val simplest = Simplest.exercise


  Some(new PrintWriter("index.html")).foreach{p => p.write(
    "<!DOCTYPE html>" + ({
      <html>
        <head>
          <meta charset="UTF-8"/>
          <title>Simplest solution</title>
          <script src='https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=MML_CHTML'></script>
          <style>{
            """
              |td {border: 1px solid black;}
              |.red:hover {background-color: pink;}
            """.stripMargin}</style>
        </head>
        <body>
          {simplest.toHTML5}
        </body>
      </html>
    } toString)
  ); p.close}
}

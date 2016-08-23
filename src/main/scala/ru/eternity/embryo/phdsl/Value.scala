package ru.eternity.embryo.phdsl

import scala.xml._

trait Value

case class Scalar(value: Double) extends Algebra[Value] {
  override def toMathML = {
    <mn class="red">{value.toString}</mn>
  }
}

object Scalar {
  implicit def doubleToScalar(d: Double): Algebra[Value] = new Scalar(d)
}

case class Vector(components: Algebra[Value]*) extends Algebra[Value] {
  override def toMathML = {
    <vector>
      {
        components.map({
          component => component.toMathML
        })
      }
    </vector>
  }
}

object Vector {
  implicit def tupleToVector(tuple2: Tuple2[Double, Double]): Algebra[Value] = Vector(new Scalar(tuple2._1), new Scalar(tuple2._2))
}

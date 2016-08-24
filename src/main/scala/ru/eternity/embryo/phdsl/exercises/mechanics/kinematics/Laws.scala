package ru.eternity.embryo.phdsl.exercises.mechanics.kinematics

object Laws {
  import ru.eternity.embryo.phdsl._

  import Function._
  import Variable._
  import Measured._
  import Scalar._

  val laws = Map(
    "x(t) if a = const" -> {
      ('x applyTo ref('t)) {
        ref('x0) + ref('v0) * ref('t) + ref('a) * (ref('t) ^ 2) / 2.0
      }
    }
  )
}

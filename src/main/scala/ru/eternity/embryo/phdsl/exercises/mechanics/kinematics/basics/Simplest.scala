package ru.eternity.embryo.phdsl.exercises.mechanics.kinematics.basics

import ru.eternity.embryo.phdsl._
import ru.eternity.embryo.phdsl.exercises.mechanics.kinematics.Laws

object Simplest {

  import Scalar._
  import Variable._
  import Measured._
  import Function._

  val names = Map[Symbol, String](
    'v -> "Body velocity",
    't1 -> "Body movement duration",
    't0 -> "Movement start time",
    'x1 -> "Distance"
  )

  def get(symbol: Symbol) = getStatement(symbol, (symbol) => {
    (Simplest.exercise.conditions(names(symbol)) match {
      case null => Simplest.exercise.targets(Simplest.exercise.conditions)(names(symbol))
      case value: Algebra[Value] => value
    }).getValue
  })

  def exercise: Exercise = Exercise(
    conditions = {
      Map(
        names('v) -> {
          'v treatAs {
            1.0(length / time)
          }
        },
        names('t1) -> {
          't1 treatAs {
            10.0(time)
          }
        },
        names('t0) -> {
          't0 treatAs {
            0.0(time)
          }
        }
      )
    },
    targets = (conditions: Map[String, Algebra[Value]]) => Map[String, Function[Value]](
      names('x1) -> {
        'x1 <= ref('t1)
      }
    ),
    solution = (conditions: Map[String, Algebra[Value]], targets: Map[String, Algebra[Value]]) => {
      Map(
        "Calculate distance" -> {
          Consequence(conditions(names('v)), conditions(names('t0)), conditions(names('t1)), targets(names('x1)), Laws.laws("x(t) if a = const"))({
            ('x1 applyTo (ref('t1))) {
              get('v) * (get('t1) - get('t0))
            }
          })
        }
      )
    }
  )
}

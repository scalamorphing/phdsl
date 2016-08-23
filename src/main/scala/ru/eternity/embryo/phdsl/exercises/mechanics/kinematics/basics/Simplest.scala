package ru.eternity.embryo.phdsl.exercises.mechanics.kinematics.basics

import ru.eternity.embryo.phdsl._
import ru.eternity.embryo.phdsl.exercises.mechanics.kinematics.Laws

object Simplest {

  import Scalar._
  import Variable._
  import Measured._
  import Function._

  val names = Map[Symbol, String](
    'v -> "Скорость тела",
    't1 -> "Время движения тела",
    't0 -> "Время начала движения тела",
    'x1 -> "Пройденное расстояние"
  )

  def get(symbol: Symbol) = @@(symbol, (symbol) => {
    (Simplest.exercise.conditions(names(symbol)) match {
      case null => Simplest.exercise.targets(Simplest.exercise.conditions)(names(symbol))
      case value: Algebra[Value] => value
    }).getValue
  })

  def exercise: Exercise = Exercise(
    conditions = {
      Map(
        names('v) -> {
          'v @= {
            1.0(length / time)
          }
        },
        names('t1) -> {
          't1 @= {
            10.0(time)
          }
        },
        names('t0) -> {
          't0 @= {
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
        "Вычисляем расстояние" -> {
          Consequence(conditions(names('v)), conditions(names('t0)), conditions(names('t1)), targets(names('x1)), Laws.laws("x(t) if a = const"))({
            ('x1 @< (ref('t1))) {
              get('v) * (get('t1) - get('t0))
            }
          })
        }
      )
    }
  )
}

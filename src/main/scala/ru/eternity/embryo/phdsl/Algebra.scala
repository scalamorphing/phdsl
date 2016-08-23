package ru.eternity.embryo.phdsl
import scala.xml._

trait Algebra[T] {
  def /(dividend: Algebra[T]): Algebra[T] = Fraction(this, dividend)
  def *(multiplier: Algebra[T]): Algebra[T] = Multiplication(this, multiplier)
  def +(summand: Algebra[T]): Algebra[T] = Sum(this, summand)
  def -(subtrahend: Algebra[T]): Algebra[T] = Subtract(this, subtrahend)
  def ^(degree: Int): Algebra[T] = Power(this, degree)
  def ==>(consequence: Algebra[T]) = Consequence(this)(consequence)
  def <=>(identity: Algebra[T]) = Identity(this, identity)
  def toMathML: Elem
  def getValue = this
}

case class Measured[T](value: Algebra[T], measure: Algebra[Measure]) extends Algebra[T] {
  override def toMathML = {
    <mrow>
      {value.toMathML}
      {measure.toMathML}
    </mrow>
  }
}

object Measured {
  implicit class MeasuredValue(value: Double) {
    def apply(measure: Algebra[Measure]) = Measured(Scalar(value), measure)
  }
}


case class Variable[T](symbol: Symbol, value: Algebra[T] = null, implicit val resolver: Symbol => Algebra[T]) extends Algebra[T] {
  override def toMathML = {
    {
      value match {
        case null =>
            {
              resolver(symbol) match {
                case null =>
                  <mrow><mi>{symbol.toString.substring(1)}</mi></mrow>
                case value: Algebra[T] => {
                  <mrow>
                    {value.toMathML}
                  </mrow>
                }
              }
            }
        case value: Algebra[T] =>
          <mrow>
            {
              if (resolver == null) {
                {value.toMathML}
              } else {
                {
                  Seq(<mi>
                    {symbol.toString.substring(1)}
                  </mi>,
                    <mo>=</mo>, {
                    value.toMathML
                  })
                }
              }
            }
          </mrow>
      }
    }
  }

  override def getValue = value match {
    case null => resolver(symbol).getValue
    case _ => value
  }
}

object Variable {

  implicit class VariableSymbol(symbol: Symbol) {
    def @=(value: Algebra[Value], resolver: Symbol => Algebra[Value] = (symbol) => null) = Variable(symbol, value, resolver)
    def @<(arguments: Algebra[Value]*)(body: => Algebra[Value]) = Function(symbol, arguments:_*)(body)
    def <=(arguments: Algebra[Value]*) = Function[Value](symbol, arguments:_*)
    def <<<(value: Algebra[Value]) = Function[Value](symbol)(value)
  }

  def @@(symbol: Symbol, resolver: Symbol => Algebra[Value]) = Variable[Value](symbol, null, resolver)

  def ref(symbol: Symbol) = Variable[Value](symbol, null, (symbol) => null)
}

case class Function[T](symbol: Symbol, arguments: Algebra[T]*) extends Algebra[T] {
  private var body: Algebra[T] = _

  def apply(body: Algebra[T]) = {
    this.body = body
    this
  }

  def ! = body

  override def toMathML = {
    <mrow>
      <mi>{symbol.toString.substring(1)}</mi>
      <mo>&af;</mo>
      <mrow>
        <mo>(</mo>
        {
          arguments.map({
            argument => argument.toMathML
          })
        }
        <mo>)</mo>
      </mrow>
      <mo>=</mo>
      {if (body == null) <mi>?</mi> else body.toMathML}
    </mrow>
  }

  override def getValue = body
}

case class Fraction[T](divider: Algebra[T], dividend: Algebra[T]) extends Algebra[T] {
  override def toMathML = {
    <mfrac>
      {divider.toMathML}
      {dividend.toMathML}
    </mfrac>
  }
}

case class Multiplication[T, U](factors: Algebra[T]*) extends Algebra[T] {
  override def toMathML = {
    <mrow>
      {
      factors.flatMap({
        factor => {
          Seq(<mo>(</mo>, {factor.toMathML}, <mo>)</mo>, <mo>·</mo>)
        }
      }).dropRight(1)
      }
    </mrow>
  }
}

case class Sum[T](summands: Algebra[T]*) extends Algebra[T] {
  override def toMathML = {
    <mrow>
      {
        summands.flatMap({
          summand => {
            Seq({summand.toMathML}, <mo>+</mo>)
          }
        }).dropRight(1)
      }
    </mrow>
  }
}

case class Subtract[T](minuend: Algebra[T], subtrahend: Algebra[T]) extends Algebra[T] {
  override def toMathML = {
    <mrow>
      {minuend.toMathML}
      <mo>+</mo>
      {subtrahend.toMathML}
    </mrow>
  }
}

case class Power[T](base: Algebra[T], degree: Int) extends Algebra[T] {
  override def toMathML = {
    <msup>
      {base.toMathML}
      <mn>{degree.toString}</mn>
    </msup>
  }
}

package ru.eternity.embryo.phdsl

import scala.xml._

case class Exercise(conditions: Map[String, Algebra[Value]], targets: Map[String, Algebra[Value]] => Map[String, Algebra[Value]],
                    solution: (Map[String, Algebra[Value]], Map[String, Algebra[Value]]) => Map[String, Statement]) {
  def toHTML5 = {
    <div>
      <div>Conditions</div>
      <table><tbody>
        {
          conditions.map({
            case (id, condition) =>
              <tr><td><span>{id}</span></td><td><math xmlns="http://www.w3.org/1998/Math/MathML">{condition.toMathML}</math></td></tr>
          })
        }
      </tbody></table>
      <div>Targets</div>
      <table><tbody>
      {
        for {
          (id, target) <- targets(conditions)
        } yield <tr><td><span>{id}</span></td><td><math xmlns="http://www.w3.org/1998/Math/MathML">{target.toMathML}</math></td></tr>
      }
      </tbody></table>
      <div>Solution</div>
      {
        for {
          (stepName, stepBody) <- solution(conditions, targets(conditions))
        }
          yield {
            <div>
              <div>{stepName}</div>
              <div><math xmlns="http://www.w3.org/1998/Math/MathML">{stepBody.toMathML}</math></div>
            </div>
          }
      }
    </div>
  }
}

trait Statement {
  def toMathML: Elem
}

case class Consequence[T](left: Algebra[T]*)(right: Algebra[T]) extends Statement {
  def toMathML = {
    <math display="block">
      <mrow>
        <mrow>
          <mo>{"{"}</mo>
          <mtable>
            {
              left.map({
                consequence =>
                  <mtr><mtd columnalign="left">
                    {consequence.toMathML}
                  </mtd></mtr>
              })
            }
          </mtable>
          <mo>⇒</mo>
          {right.toMathML}
        </mrow>
        </mrow>
      </math>
  }
}

case class Identity[T](left: Algebra[T], right: Algebra[T]) extends Statement {
  def toMathML = {
    <mrow>
      {left.toMathML}
      {right.toMathML}
    </mrow>
  }
}

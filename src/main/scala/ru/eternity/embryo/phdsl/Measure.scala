package ru.eternity.embryo.phdsl

import scala.xml._

trait Measure extends Algebra[Measure]

case object length extends Measure {
  override def toMathML = {
    <mi>length</mi>
  }
}

case object mass extends Measure {
  override def toMathML = {
    <mi>mass</mi>
  }
}

case object time extends Measure {
  override def toMathML = {
    <mi>time</mi>
  }
}

case object angle extends Measure {
  override def toMathML = {
    <mi>rad</mi>
  }
}

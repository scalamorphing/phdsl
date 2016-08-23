## Physics DSL

### Usage

1. sbt run
2. Open index.html //seriously, it will be overwritten

### How index.html is generated

* It uses special DSL for describing very simple, but not the simplest physics exercises.
* Measure package contains part of DSL describing particular measures: length, mass, time, angle.
* Algebra package contains part of DSL specifying trait for structures with multiplication, division, subtraction, and addition operations.
* Exercise package represents structure of simple exercise.
* Value package represents fundamental math objects: scalars and vectors.
* Laws package is created for managing set of fundamental laws of real world and math abstractions.
* Simplest object describes very simple exercise, which is used to generate MathML elements injected into main html DOM tree.
* Launcher object is an entry point of the application.

So this project aims to generate interactive html/mathml output for math/physics exercises specified using implemented DSL.

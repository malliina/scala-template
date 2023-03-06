package com.malliina.template

import com.malliina.template.Primitives.Token

import scala.concurrent.duration.Duration
import concurrent.duration.DurationInt

class TemplateTests extends munit.FunSuite:
  test("can run test") {
    assertEquals(1, 1)
  }

  test("can dotty!") {
    assertEquals(Dotty.hm, 42)
  }

  test("contextual") {
    assertEquals(Contextual.compute(2), 6)
    assertEquals(Contextual.compute("ab"), "ababab")
    assertEquals(Contextual.compute(Dur(5.seconds)).d, 15.seconds)
  }

  test("opaque types") {
    val t = Token("hey")
    // compiles, because extension methods
    assert(!t.isEmpty)
    assert(!t.isValid)
    // does not compile, because opaque type
    // t.nonEmpty
  }

class Dur(val d: Duration) extends AnyVal
object Dur:
  given Multiplier[Dur] with
    override def multiply(t: Dur, by: Int): Dur = Dur(t.d * by)

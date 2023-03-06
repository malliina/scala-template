package com.malliina.template

import cats.{Eq, Show}
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

  test("JSON derivation") {
    import io.circe.syntax.EncoderOps
    val p = Person("Jack", 42)
    println(p.asJson)
    val e = PlanetaryEntity.Planet(1234)
    println(e.asJson)
    val u = PlanetaryEntity.Unnatural(true)
    println(u.asJson)
  }

  test("homemade custom derivation") {
    val p = Person("Michael", 25)
    assertEquals(first(p), First(Option("name"), Option("Michael")))
  }

  def first[T](t: T)(using f: FirstReader[T]): First = f.compute(t)

class Dur(val d: Duration) extends AnyVal
object Dur:
  given Multiplier[Dur] with
    override def multiply(t: Dur, by: Int): Dur = Dur(t.d * by)

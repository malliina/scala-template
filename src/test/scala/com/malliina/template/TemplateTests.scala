package com.malliina.template

import cats.{Eq, Show}
import com.malliina.template.Primitives.Token
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
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
    val p = Person("Jack", 42)
//    println(p.asJson)
    val e = PlanetaryEntity.Planet(1234)
//    println(e.asJson)
    val u = PlanetaryEntity.Unnatural(true)
    val json = u.asJson
//    println(json)
//    val res = decode[PlanetaryEntity](json.noSpaces)
//    assert(res.isRight)
//    val res2 = decode[PlanetaryEntity.Unnatural](json.noSpaces)
//    assert(res2.isRight)
//    assert(res2.exists(u => u.isUfo))
//    val a = NamedValue.Dark
//    println(a.asJson)

    val well = Well("Hej")
//    println(well.asJson)

//    val welle = WellE.M
//    println(welle.asJson)
//    println(Bar.Batata.asJson)
//    Seq(Bar.Batata, Bar.Cenoura, Bar.Nabo) foreach println

    println(User.Jack.asJson)
    println(User.Linda.asJson)
  }

  test("homemade custom derivation") {
    val p = Person("Michael", 25)
    assertEquals(first(p), First("name", Option("Michael")))
  }

  test("easy") {
//    println(simple(EasyEnum.A))
//    println(s.extract(EasyEnum.A))
//    what(EasyEnum.A)
    opt(Opt.None)
    x(X.A("This is A"))
//    println(simple(X.A("Juu")))
//    println(simple(X.C))
  }

  test("demo") {
    val d = Human("Hejsan")
    val s = summon[NameReader[Human]]
    println(s.name(d))
  }

  def opt[T](o: Opt[T]) = "jee"
  def x(x: X) = "hej"
  def what(e: EasyEnum): Unit = println(s"Jee $e")

  def first[T](t: T)(using f: FirstReader[T]): First = f.compute(t)
  def simple[T](t: T)(using f: EasyEnumEncoder[T]): String = f.extract(t)

class Dur(val d: Duration) extends AnyVal
object Dur:
  given Multiplier[Dur] with
    override def multiply(t: Dur, by: Int): Dur = Dur(t.d * by)

enum Opt[+T]:
  case Some(x: T)
  case None

enum X derives EasyEnumEncoder:
  case A(a: String)
  case B(b: String)
  case C

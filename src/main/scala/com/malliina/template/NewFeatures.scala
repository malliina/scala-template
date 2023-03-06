package com.malliina.template

import io.circe.Codec
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}

import scala.deriving.Mirror
import scala.compiletime.{constValue, erasedValue}
object Contextual:
  def compute[T](t: T)(using m: Multiplier[T]): T = m.multiply(t, 3)

trait Multiplier[T]:
  def multiply(t: T, by: Int): T

object Multiplier:
  given Multiplier[Int] with
    override def multiply(t: Int, by: Int) = t * by
  given Multiplier[String] with
    override def multiply(t: String, by: Int) = List.fill(by)(t).mkString

object Primitives:
  opaque type Token = String
  object Token:
    def apply(s: String): Token = s
  extension (t: Token)
    def isEmpty = t.isEmpty
    def isValid = false

given Configuration =
  Configuration.default.withDiscriminator("kind")

enum PlanetaryEntity derives ConfiguredCodec:
  case Unnatural(isUfo: Boolean)
  case Planet(size: Double)

case class Unnatural(isUfo: Boolean) derives Codec.AsObject

case class Person(name: String, age: Int) derives Codec.AsObject, FirstReader

case class First(member: Option[String], value: Option[String])

trait FirstReader[T]:
  def compute(t: T): First

object FirstReader:
  inline final def derived[T](using mirror: Mirror.Of[T]): FirstReader[T] = (t: T) =>
    val product = t.asInstanceOf[Product]
    First(
      firstLabel[mirror.MirroredElemLabels],
      Option.when(product.productArity > 0)(product.productElement(0).toString)
    )

  private inline final def firstLabel[T <: Tuple]: Option[String] = inline erasedValue[T] match
    case _: (t *: ts)  => Option(constValue[t].asInstanceOf[String])
    case _: EmptyTuple => None

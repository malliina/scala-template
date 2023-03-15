package com.malliina.template

import cats.syntax.functor.toFunctorOps
import com.malliina.template
import com.malliina.values.StringEnumCompanion
import io.circe
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredDecoder, ConfiguredEncoder, ConfiguredEnumCodec}
import io.circe.generic.semiauto.deriveCodec
import io.circe.syntax.EncoderOps
import io.circe.{Codec, Decoder, DecodingFailure, Encoder, HCursor, Json}

import scala.compiletime.{byName, constValue, erasedValue, error, summonAll, summonFrom, summonInline}
import scala.deriving.Mirror

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
    given Codec[Token] =
      Codec.from(Decoder.decodeString.map(apply), Encoder.encodeString.contramap(_.trim))
  extension (t: Token)
    def isEmpty = t.isEmpty
    def isValid = false

case class AuthInfo(token: Primitives.Token) derives Codec.AsObject

trait Named:
  def name: String

trait NamedGiven[T <: Named]:
  val all: Seq[T]

  given Codec[T] = Codec.from(
    Decoder.decodeString.emap(s => all.find(_.name == s).toRight(s"Unknown name: '$s'.")),
    Encoder.encodeString.contramap(_.name)
  )

enum User(val name: String) extends Named:
  case Jack extends User("Jack")
  case Kate extends User("Kate")
  case Linda extends User("Linda")

object User extends NamedGiven[User]:
  override val all: Seq[User] = User.values

enum PlanetaryEntity:
  case Unnatural(isUfo: Boolean)
  case Planet(size: Double)

object PlanetaryEntity:
  given Configuration =
    Configuration.default.withDiscriminator("kind")
  private val Type = "type"
  private val UnnaturalKey = "unnatural"
  private val PlanetKey = "planet"
  implicit val unnaturalCodec: Codec[Unnatural] = typedCodec(UnnaturalKey, deriveCodec[Unnatural])
  implicit val planetCodec: Codec[Planet] = typedCodec(PlanetKey, deriveCodec[Planet])
  private val dec: Decoder[PlanetaryEntity] = Decoder.decodeString.at(Type).flatMap {
    case PlanetaryEntity.UnnaturalKey => Decoder[Unnatural].widen
    case PlanetaryEntity.PlanetKey    => Decoder[Planet].widen
  }
  private val enc: Encoder[PlanetaryEntity] = {
    case u @ Unnatural(_) => unnaturalCodec(u)
    case p @ Planet(_)    => planetCodec(p)
  }
  implicit val codec: Codec[PlanetaryEntity] = Codec.from(dec, enc)
  private def typedCodec[T](name: String, base: Codec[T]) = Codec.from(
    Decoder.decodeString.at(Type).flatMap { n =>
      if name == n then base else Decoder.failed(DecodingFailure(s"Expected '$name'.", Nil))
    },
    base.mapJson(json => json.deepMerge(Json.obj(Type -> name.asJson)))
  )

case class Person(name: String, age: Int) derives Codec.AsObject, FirstReader

case class First(member: String, value: Option[String])

trait FirstReader[T]:
  def compute(t: T): First

case class Human(name: String) derives NameReader

trait NameReader[T]:
  def name(t: T): String

object NameReader:
  inline final def derived[T](using mirror: Mirror.Of[T]): NameReader[T] =
    val n = constValue[mirror.MirroredLabel]
    val a = summonFrom[T] {
      case m: Mirror.Of[T] =>
        inline erasedValue[m.MirroredElemLabels] match
          case _: EmptyTuple => "No labels"
          case _: (t *: ts) =>
            summonFrom[t] {
              case am: Mirror.Of[t] =>
                summonLabels[am.MirroredElemLabels]
              case _ => "no mirror"
            }
      case _ => 32
    }
    (t: T) => s"$n a $a"

object FirstReader:
  inline final def derived[T](using mirror: Mirror.Of[T]): FirstReader[T] =
    val member = summonLabels[mirror.MirroredElemLabels].head
    (t: T) =>
      val product = t.asInstanceOf[Product]
      First(
        member,
        Option.when(product.productArity > 0)(product.productElement(0).toString)
      )

case class Well(name: String) derives SimpleValueEncoder

enum SimpleE(val name: String) extends Named derives SimpleEnumCodec:
  case Jee extends SimpleE("jee")
  case Juu extends SimpleE("juu")

trait SimpleValueEncoder[T] extends Encoder[T]

object SimpleValueEncoder:
  given Configuration = Configuration.default
  inline final def derived[T](using mirror: Mirror.Of[T]): SimpleValueEncoder[T] =
    val enc = inline erasedValue[mirror.MirroredElemTypes] match
      case _: EmptyTuple => error("Must have at least one member")
      case _: (t *: ts)  => summonEncoder[t]
    val innerEncoder = enc.asInstanceOf[Encoder[Any]]
    mirror match
      case _: Mirror.ProductOf[T] =>
        a =>
          val product = a.asInstanceOf[Product]
          innerEncoder(product.productElement(0))
      case sum: Mirror.SumOf[T] =>
        a => "".asJson

trait SimpleEnumEncoder[T] extends Encoder[T]
object SimpleEnumEncoder:
  inline final def derived[T <: Named](using mirror: Mirror.SumOf[T]): SimpleEnumEncoder[T] =
    a => a.name.asJson

trait SimpleEnumDecoder[T] extends Decoder[T]
object SimpleEnumDecoder:
  inline final def derived[T <: Named](using mirror: Mirror.SumOf[T]): SimpleEnumDecoder[T] =
    val values = summonValues[mirror.MirroredElemTypes, T]
    val decoder =
      Decoder.decodeString.emap(s => values.find(_.name == s).toRight(s"Unknown enum value: '$s'."))
    h => decoder(h)

trait SimpleEnumCodec[T] extends SimpleEnumEncoder[T], SimpleEnumDecoder[T]
object SimpleEnumCodec:
  inline final def derived[T <: Named](using mirror: Mirror.SumOf[T]): SimpleEnumCodec[T] =
    val dec = SimpleEnumDecoder.derived[T]
    val enc = SimpleEnumEncoder.derived[T]
    new SimpleEnumCodec[T]:
      override def apply(c: HCursor): Decoder.Result[T] = dec(c)
      override def apply(a: T): Json = enc(a)

enum MyEnum derives EnumEncoder, EnumDecoder:
  case HeyYou
  case AaaBoo

trait EnumEncoder[T] extends Encoder[T]
trait EnumDecoder[T] extends Decoder[T]

object EnumEncoder:
  // Serializes enum cases to camelCase
  inline final def derived[T](using mirror: Mirror.SumOf[T]): EnumEncoder[T] =
    val labels = summonLabels[mirror.MirroredElemLabels]
    (t: T) =>
      val caseName = labels(mirror.ordinal(t))
      val name = caseName.toCharArray.toList match
        case h :: t => (Seq(h.toLower) ++ t).mkString
        case other  => other.mkString
      name.asJson

object EnumDecoder:
  inline def derived[T](using mirror: Mirror.SumOf[T]): EnumDecoder[T] =
    val labels = summonLabels[mirror.MirroredElemLabels]
    val values = summonAll[Tuple.Map[mirror.MirroredElemTypes, ValueOf]].productIterator
      .map(_.asInstanceOf[ValueOf[T]].value)
    val valuesByName = (labels.map(_.toLowerCase) zip values).toMap
    val decoder = Decoder.decodeString.emap { s =>
      valuesByName.get(s.toLowerCase).toRight(s"Invalid name: '$s'.")
    }
    h => decoder(h)

enum EasyEnum derives EasyEnumEncoder:
  case A(name: String)
  case B(age: Int)

trait EasyEnumEncoder[T]:
  def extract(t: T): String

object EasyEnumEncoder:
  inline final def derived[T](using mirror: Mirror.Of[T]): EasyEnumEncoder[T] =
    val dummy: EasyEnumEncoder[T] = (t: T) => "dummy"
    inline erasedValue[mirror.MirroredElemTypes] match
      case _: (h *: t) =>
        inline summonInline[Mirror.Of[h]] match
          case m: Mirror.ProductOf[h] =>
            (t: T) =>
              val product = t.asInstanceOf[Product]
              if product.productArity > 0 then product.productElement(0).toString
              else "dummy singleton"
          case _ => dummy
      case _ => dummy

trait StringEnumEncoder[T] extends Encoder[T]

object StringEnumEncoder:
  inline def derived[T](using m: Mirror.SumOf[T]): StringEnumEncoder[T] =
    val elemInstances = summonAll[Tuple.Map[m.MirroredElemTypes, ValueOf]].productIterator
      .asInstanceOf[Iterator[ValueOf[T]]]
      .map(_.value)
    val elemNames = summonAll[Tuple.Map[m.MirroredElemLabels, ValueOf]].productIterator
      .asInstanceOf[Iterator[ValueOf[String]]]
      .map(_.value)
    val mapping = (elemInstances zip elemNames).toMap
    (t: T) => Encoder[String].contramap[T](mapping.apply)(t)

trait Computer:
  type Ret
  def compute(n: Int): Ret

class MyComputer extends Computer:
  type Ret = String
  override def compute(n: Int): Ret = List.fill(n)("hej").mkString

package com.malliina

import io.circe.Encoder
import io.circe.derivation.{Configuration, ConfiguredEncoder}

import scala.compiletime.{constValue, erasedValue, summonFrom, summonAll}
import scala.deriving.Mirror

package object template:
  inline final def summonLabels[T <: Tuple]: List[String] =
    inline erasedValue[T] match
      case _: EmptyTuple => Nil
      case _: (t *: ts)  => constValue[t].asInstanceOf[String] :: summonLabels[ts]

  inline final def summonEncoders[T <: Tuple](using Configuration): List[Encoder[?]] =
    inline erasedValue[T] match
      case _: EmptyTuple => Nil
      case _: (t *: ts)  => summonEncoder[t] :: summonEncoders[ts]

  inline final def summonEncoder[A](using Configuration): Encoder[A] =
    summonFrom[A] {
      case encodeA: Encoder[A] => encodeA
      case _: Mirror.Of[A]     => ConfiguredEncoder.derived[A]
    }

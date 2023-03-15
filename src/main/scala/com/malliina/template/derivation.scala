package com.malliina.template

import io.circe.Encoder
import io.circe.derivation.{Configuration, ConfiguredEncoder}

import scala.compiletime.{constValue, erasedValue, summonFrom, summonAll}
import scala.deriving.Mirror

inline def summonLabels[T <: Tuple]: List[String] =
  inline erasedValue[T] match
    case _: EmptyTuple => Nil
    case _: (t *: ts)  => constValue[t].asInstanceOf[String] :: summonLabels[ts]

inline def summonEncoders[T <: Tuple](using Configuration): List[Encoder[?]] =
  inline erasedValue[T] match
    case _: EmptyTuple => Nil
    case _: (t *: ts)  => summonEncoder[t] :: summonEncoders[ts]

inline def summonEncoder[A](using Configuration): Encoder[A] =
  summonFrom[A] {
    case encodeA: Encoder[A] => encodeA
    case _: Mirror.Of[A]     => ConfiguredEncoder.derived[A]
  }

inline def summonValues[T <: Tuple, A]: List[A] =
  summonAll[Tuple.Map[T, ValueOf]].productIterator
    .map(_.asInstanceOf[ValueOf[A]].value)
    .toList

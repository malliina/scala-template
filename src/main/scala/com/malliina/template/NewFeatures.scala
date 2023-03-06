package com.malliina.template

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

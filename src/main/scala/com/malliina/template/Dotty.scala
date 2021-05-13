package com.malliina.template

object Dotty:
  val hm: Int =
    if 2 > 1 then 42
    else 41

  val well =
    if 42 > 41 then
      val hm = 3
      val what = 42
      1
    else
      val uhm = 11
      val yes = 12
      2

  def what =
    val g = 1
    val sum = g + 42
    sum / 2

  def hmm =
    println("try this")
    val hm = "hello"
    s"$hm, world"

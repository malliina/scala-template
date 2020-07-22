package com.malliina.template

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import concurrent.duration.DurationInt

object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    Await.result(task(), 10.seconds)
  }

  def task() = async {
    val result = await(start())
    val result2 = await(start())
    result + result2
  }

  def start(): Future[Int] = Future.successful(42)
}

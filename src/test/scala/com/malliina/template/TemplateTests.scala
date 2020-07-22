package com.malliina.template

import scala.async.Async.{async, await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

class TemplateTests extends munit.FunSuite {
  test("can run test") {
    assert(1 == 1)
  }

  test("async-await successfully") {
    val result = wait(task())
    assert(result == 84)
  }

  test("async-await failures") {
    val recovered = failingTask().recover { case _ => 30 }
    val result = wait(recovered)
    assert(result == 30)
  }

  def wait[T](f: Future[T]) = Await.result(f, 10.seconds)

  def task(): Future[Int] = async {
    val result = await(start())
    val result2 = await(start())
    result + result2
  }

  def failingTask(): Future[Int] = async {
    val result1 = await(start())
    val result2 = await(fail())
    result1 + result2
  }

  def start(): Future[Int] = Future.successful(42)
  def fail(): Future[Int] = Future.failed(new Exception("pum"))
}

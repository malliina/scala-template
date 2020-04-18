package com.malliina.template

class TemplateTests extends munit.FunSuite {
  test("can run test") {
    assert(1 == 1)
  }

  test("can dotty!") {
    assert(Dotty.hm == 42)
  }
}

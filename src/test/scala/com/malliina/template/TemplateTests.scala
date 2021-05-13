package com.malliina.template

class TemplateTests extends munit.FunSuite:
  test("can run test") {
    assertEquals(1, 1)
  }

  test("can dotty!") {
    assertEquals(Dotty.hm, 42)
  }

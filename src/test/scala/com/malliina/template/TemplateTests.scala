package com.malliina.template

import fs2.io.file.Path

class TemplateTests extends munit.CatsEffectSuite:
  test("can run test") {
    assertEquals(1, 1)
  }

  test("remove comments") {
    CommentRemover()
      .recurse(Path("..").resolve("boattracker-ios"), p => p.extName == ".swift")
      .map { res =>
        res foreach println
        assertEquals(1, 1)
      }
  }

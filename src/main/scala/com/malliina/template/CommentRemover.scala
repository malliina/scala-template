package com.malliina.template

import cats.effect.IO
import fs2.io.file.{CopyFlag, CopyFlags, Files, Path}
import fs2.text

class CommentRemover:
  def recurse(dir: Path, p: Path => Boolean) =
    Files[IO].walk(dir).filter(p).evalMap(removeComments).compile.toList

  def removeComments(from: Path): IO[Path] =
    val tempOut = from.resolveSibling(s"${from.fileName}.temp")
    Files[IO]
      .readUtf8Lines(from)
      .groupAdjacentBy(isComment)
      .filter((comment, lines) => !comment || lines.size < 3)
      .mapChunks[String](c => c.flatMap((_, lines) => lines))
      .intersperse("\n")
      .through(text.utf8.encode)
      .through(Files[IO].writeAll(tempOut))
      .compile
      .drain >> Files[IO].move(tempOut, from, CopyFlags(CopyFlag.ReplaceExisting)).map(_ => from)

  private def isComment(s: String) = s.startsWith("//")

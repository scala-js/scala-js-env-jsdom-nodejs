package testproject

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}

import org.junit.Test
import org.junit.Assert._

class LibTest {
  @Test def dummy_library_should_append_an_element(): Unit = {
    def count = Lib.getElementsByTagName("p").length

    val oldCount = count
    Lib.appendDocument("foo")
    assertEquals(1, count - oldCount)
  }

  @Test def expose_nodejs_global(): Unit = {
    assertTrue(js.typeOf(g.global) == "object")
    assertTrue(js.typeOf(g.global.require) == "function")

    assertTrue(g.global.require("fs") != null)
  }
}

package org.scalajs.jsenv.jsdomnodejs

import scala.concurrent.duration._

import org.junit.Test

import org.scalajs.jsenv.test.kit.TestKit

class JSDOMNodeJSEnvTest {
  private val kit = new TestKit(new JSDOMNodeJSEnv, 1.minute)

  @Test
  def historyAPI: Unit = {
    kit.withRun(
        """
        |console.log(window.location.href);
        |window.history.pushState({}, "", "/foo");
        |console.log(window.location.href);
        """.stripMargin) {
      _.expectOut("http://localhost/\n")
        .expectOut("http://localhost/foo\n")
    }
  }
}

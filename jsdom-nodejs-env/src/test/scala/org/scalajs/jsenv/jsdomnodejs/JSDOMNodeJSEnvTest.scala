package org.scalajs.jsenv.jsdomnodejs

import scala.concurrent.Await

import org.junit.Test

import org.scalajs.io._

import org.scalajs.jsenv._

class JSDOMNodeJSEnvTest {

  private val TestRunConfig = {
    RunConfig()
      .withInheritOut(false)
      .withOnOutputStream((_, _) => ()) // ignore stdout
  }

  private val config = JSDOMNodeJSSuite.Config

  @Test
  def historyAPIWithoutTestKit: Unit = {
    assertRunSucceeds(
        """
        |console.log(window.location.href);
        |window.history.pushState({}, "", "/foo");
        |console.log(window.location.href);
        """.stripMargin)
  }

  private def assertRunSucceeds(inputStr: String): Unit = {
    val inputFile = MemVirtualBinaryFile.fromStringUTF8("test.js", inputStr)
    val input = Input.ScriptsToLoad(List(inputFile))
    val run = config.jsEnv.start(input, TestRunConfig)
    try {
      Await.result(run.future, config.awaitTimeout)
    } finally {
      run.close()
    }
  }

}

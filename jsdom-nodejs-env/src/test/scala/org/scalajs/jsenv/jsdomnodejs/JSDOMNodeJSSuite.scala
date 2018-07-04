package org.scalajs.jsenv.jsdomnodejs

import org.scalajs.jsenv.test._

import org.junit.runner.RunWith

@RunWith(classOf[JSEnvSuiteRunner])
class JSDOMNodeJSSuite extends JSEnvSuite(JSDOMNodeJSSuite.Config)

object JSDOMNodeJSSuite {
  val Config = {
    JSEnvSuiteConfig(new JSDOMNodeJSEnv)
      .withTerminateVMJSCode("__ScalaJSEnv.exitFunction(0)")
  }
}

package org.scalajs.jsenv.jsdomnodejs

import org.scalajs.jsenv.test._

import org.junit.runner.RunWith

@RunWith(classOf[JSEnvSuiteRunner])
class JSDOMNodeJSSuite extends JSEnvSuite(JSEnvSuiteConfig(new JSDOMNodeJSEnv))

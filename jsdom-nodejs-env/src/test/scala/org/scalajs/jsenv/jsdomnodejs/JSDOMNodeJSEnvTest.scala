package org.scalajs.jsenv.jsdomnodejs

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}

import scala.concurrent.duration._

import com.google.common.jimfs.Jimfs

import org.junit.Test

import org.scalajs.jsenv.Input
import org.scalajs.jsenv.test.kit.TestKit

class JSDOMNodeJSEnvTest {
  import JSDOMNodeJSEnvTest._

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

  @Test
  def reactUnhandledExceptionHack_issue42: Unit = {
    val code =
      """
      |const rootElement = document.createElement("div");
      |document.body.appendChild(rootElement);
      |
      |class ThrowingComponent extends React.Component {
      |  render() {
      |    throw new Error("boom");
      |  }
      |}
      |
      |class ErrorBoundary extends React.Component {
      |  constructor(props) {
      |    super(props);
      |    this.state = { hasError: false };
      |  }
      |
      |  componentDidCatch(error, info) {
      |    this.setState({error: error.message, hasError: true});
      |  }
      |
      |  render() {
      |    if (this.state.hasError) {
      |      console.log("render-error");
      |      return React.createElement("p", null,
      |        `Caught error: ${this.state.error}`);
      |    } else {
      |      return this.props.children;
      |    }
      |  }
      |}
      |
      |class MyMainComponent extends React.Component {
      |  render() {
      |    console.log("two");
      |    return React.createElement(ErrorBoundary, null,
      |      React.createElement(ThrowingComponent)
      |    );
      |  }
      |}
      |
      |console.log("begin");
      |
      |const mounted = ReactDOM.render(
      |  React.createElement(ErrorBoundary, null,
      |    React.createElement(ThrowingComponent, null)
      |  ),
      |  rootElement
      |);
      |
      |console.log(document.querySelector("p").textContent);
      |
      |console.log("end");
      """.stripMargin

    kit.withRun(ReactJSFiles :+ codeToInput(code)) {
      _.expectOut("begin\nrender-error\nCaught error: boom\nend\n")
        .succeeds()
    }
  }
}

object JSDOMNodeJSEnvTest {
  private lazy val ReactJSFiles: List[Input] = {
    val fs = Jimfs.newFileSystem()
    val reactFile = copyResource(
        "/META-INF/resources/webjars/react/16.13.1/umd/react.development.js",
        fs.getPath("react.development.js"))
    val reactDOMFile = copyResource(
        "/META-INF/resources/webjars/react-dom/16.13.1/umd/react-dom.development.js",
        fs.getPath("react-dom.development.js"))
    List(reactFile, reactDOMFile).map(Input.Script(_))
  }

  private def copyResource(name: String, out: Path): out.type = {
    val inputStream = getClass().getResourceAsStream(name)
    assert(inputStream != null, s"couldn't load $name from resources")
    try {
      Files.copy(inputStream, out)
    } finally {
      inputStream.close()
    }
    out
  }

  private def codeToInput(code: String): Input = {
    val p = Files.write(
        Jimfs.newFileSystem().getPath("testScript.js"),
        code.getBytes(StandardCharsets.UTF_8))
    Input.Script(p)
  }
}

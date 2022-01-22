# scalajs-env-jsdom-nodejs

`scalajs-env-jsdom-nodejs` is a JavaScript environment for Scala.js (a `JSEnv`)
running [Node.js](https://nodejs.org/) with
[jsdom](https://github.com/jsdom/jsdom).

This repository contains `scalajs-env-jsdom-nodejs` for Scala.js 1.x. In
Scala.js 0.6.x, the Node.js with jsdom environment is part of the core
distribution.

## Setup

These instructions setup your test environment on Node.js such that you can write tests as if they were running on an HTML page.

Add the following line to `project/plugins.sbt`:

```scala
libraryDependencies += "org.scala-js" %% "scalajs-env-jsdom-nodejs" % "1.1.0"
```

and the following line to `build.sbt` (possibly in the `settings`/`jsSettings` of Scala.js projects):

```scala
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
```

Finally, make sure that [jsdom](https://github.com/jsdom/jsdom) 10.0.0 or later is avilable in your project.
You can install it with

```bash
$ npm install jsdom --save-dev
```

Or with yarn if you prefer

```bash
$ yarn add jsdom --dev
```

See [the Scaladoc](https://javadoc.io/doc/org.scala-js/scalajs-env-jsdom-nodejs_2.13/latest/org/scalajs/jsenv/jsdomnodejs/index.html) for other configuration options.

## Usage - Writing a test

To access the dom, you will need to install [`scala-js-dom`](https://github.com/scala-js/scala-js-dom):

```scala
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0"
```

Create a test in your favourite test framework, here is an example using [MUnit](https://scalameta.org/munit/).

```scala
import org.scalajs.dom.document

class ExampleJsDomTests extends munit.FunSuite {

  test("Example jsdom test") {
    val id = "my-fancy-element"
    val content = "Hi there and greetings!"

    // Create a new div element
    val newDiv = document.createElement("div")

    // Create an id attribute and assign it to the div
    val a = document.createAttribute("id")
    a.value = id
    newDiv.setAttributeNode(a)

    // Create some text content
    val newContent = document.createTextNode(content)

    // Add the text node to the newly created div
    newDiv.appendChild(newContent)

    // Add the newly created element and its content into the DOM
    document.body.appendChild(newDiv)

    // Find the element by id on the page, and compare the contents
    assertEquals(document.getElementById(id).innerHTML, content)
  }

}
```

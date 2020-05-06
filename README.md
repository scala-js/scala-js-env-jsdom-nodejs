# scalajs-env-jsdom-nodejs

`scalajs-env-jsdom-nodejs` is a JavaScript environment for Scala.js (a `JSEnv`)
running [Node.js](https://nodejs.org/) with
[jsdom](https://github.com/jsdom/jsdom).

This repository contains `scalajs-env-jsdom-nodejs` for Scala.js 1.x. In
Scala.js 0.6.x, the Node.js with jsdom environment is part of the core
distribution.

## Usage

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
$ npm install jsdom
```

See [the Scaladoc](https://javadoc.io/doc/org.scala-js/scalajs-env-jsdom-nodejs_2.13/latest/org/scalajs/jsenv/jsdomnodejs/index.html) for other configuration options.

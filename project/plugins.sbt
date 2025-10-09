addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.0")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.11.2")

libraryDependencies += "org.scala-js" %% "scalajs-env-nodejs" % "1.0.0"

Compile / unmanagedSourceDirectories +=
  baseDirectory.value.getParentFile / "jsdom-nodejs-env/src/main/scala"

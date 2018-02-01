addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.0-M3")

libraryDependencies += "org.scala-js" %% "scalajs-env-nodejs" % "1.0.0-M3"

unmanagedSourceDirectories in Compile +=
  baseDirectory.value.getParentFile / "jsdom-nodejs-env/src/main/scala"

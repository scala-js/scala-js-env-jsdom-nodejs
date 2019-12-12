addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.0-RC2")

libraryDependencies += "org.scala-js" %% "scalajs-env-nodejs" % "1.0.0-SNAPSHOT"

unmanagedSourceDirectories in Compile +=
  baseDirectory.value.getParentFile / "jsdom-nodejs-env/src/main/scala"

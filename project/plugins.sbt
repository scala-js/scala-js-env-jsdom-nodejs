addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.0-SNAPSHOT")

libraryDependencies += "org.scala-js" %% "scalajs-nodejs-env" % "1.0.0-SNAPSHOT"

unmanagedSourceDirectories in Compile +=
  baseDirectory.value.getParentFile / "jsdom-nodejs-env/src/main/scala"

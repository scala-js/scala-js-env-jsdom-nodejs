inThisBuild(Seq(
  organization := "org.scala-js",

  crossScalaVersions := Seq("2.12.10", "2.11.12", "2.13.1"),
  scalaVersion := crossScalaVersions.value.head,
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),

  homepage := Some(url("https://www.scala-js.org/")),
  licenses += ("BSD New",
      url("https://github.com/scala-js/scala-js-env-jsdom-nodejs/blob/main/LICENSE")),
  scmInfo := Some(ScmInfo(
      url("https://github.com/scala-js/scala-js-env-jsdom-nodejs"),
      "scm:git:git@github.com:scala-js/scala-js-env-jsdom-nodejs.git",
      Some("scm:git:git@github.com:scala-js/scala-js-env-jsdom-nodejs.git"))),
  versionScheme := Some("semver-spec"),
))

val commonSettings = Def.settings(
  // Scaladoc linking
  apiURL := {
    val name = moduleName.value
    val v = version.value
    Some(url(s"https://www.scala-js.org/api/$name/$v/"))
  },
  autoAPIMappings := true,

  pomExtra := (
    <developers>
      <developer>
        <id>sjrd</id>
        <name>Sébastien Doeraene</name>
        <url>https://github.com/sjrd/</url>
      </developer>
      <developer>
        <id>gzm0</id>
        <name>Tobias Schlatter</name>
        <url>https://github.com/gzm0/</url>
      </developer>
      <developer>
        <id>nicolasstucki</id>
        <name>Nicolas Stucki</name>
        <url>https://github.com/nicolasstucki/</url>
      </developer>
    </developers>
  ),
  pomIncludeRepository := { _ => false }
)

lazy val root: Project = project.in(file(".")).
  aggregate(`scalajs-env-jsdom-nodejs`, `test-project`).
  settings(
    publish / skip := true,
  )

lazy val `scalajs-env-jsdom-nodejs`: Project = project.in(file("jsdom-nodejs-env")).
  settings(
    commonSettings,

    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-js-envs" % scalaJSVersion,
      "org.scala-js" %% "scalajs-env-nodejs" % scalaJSVersion,

      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.scala-js" %% "scalajs-js-envs-test-kit" % scalaJSVersion % "test",

      /* See JSDOMNodeJSEnvTest.reactUnhandledExceptionHack.
       * We use intransitive() because we do not need the transitive
       * dependencies of these webjars, and one of them actually fails to
       * resolve (see https://github.com/webjars/webjars/issues/1789).
       */
      "org.webjars.npm" % "react" % "16.13.1" % "test" intransitive(),
      "org.webjars.npm" % "react-dom" % "16.13.1" % "test" intransitive(),
    )
  )

lazy val `test-project`: Project = project.
  enablePlugins(ScalaJSPlugin).
  enablePlugins(ScalaJSJUnitPlugin).
  settings(
    publish / skip := true,
    scalaJSUseMainModuleInitializer := true,
    jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
  )

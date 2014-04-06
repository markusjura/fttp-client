import PlayKeys._

name := "log-client"

version := "1.1"

libraryDependencies ++= Seq(
  ws
)

lazy val root = (project in file("."))
  .addPlugins(PlayScala)
  .settings(
    JsTaskKeys.jsOptions in (Assets, JshintKeys.jshint) :=
      """
      {
        "predef": [ "angular", "EventSource", "console" ],
        "asi": true,
        "globalstrict": true,
        "sub": true
      }""")
  .settings(
    LessKeys.compress := true)

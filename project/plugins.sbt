// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Less asset support
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0-M2a")

// JSHint support
addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.0-M2a")

// Require JS support
addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.0-M2a")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3-M1")
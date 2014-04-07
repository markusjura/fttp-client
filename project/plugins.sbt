// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"

// Less asset support
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0-M2")

// JSHint support
addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.0-M2")

// Require JS support
addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.0-M2")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3-M1")
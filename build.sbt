name := "play-spike"

version := "1.0.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq("org.webjars" %% "webjars-play" % "2.3.0-2",
                            "org.webjars" % "bootstrap" % "3.1.1",
                            "org.webjars" % "angularjs" % "1.3.14",
                            "com.typesafe.akka" %% "akka-cluster" % "2.3.4")

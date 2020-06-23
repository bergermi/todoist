resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += Resolver.jcenterRepo

addSbtPlugin("com.typesafe.play" % "sbt-plugin"       % "2.8.2")
addSbtPlugin("com.iheart"        % "sbt-play-swagger" % "0.9.1-PLAY2.8")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"     % "2.4.0")

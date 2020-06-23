name := "todoist"
version := "1.0"
lazy val `todoist` = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

resolvers += Resolver.jcenterRepo
scalaVersion := "2.12.10"
swaggerDomainNameSpaces := Seq("domain")

val playJsonVersion = "2.8.1"
val postgresSqlVersion = "42.2.12"
val playJsonExtensionsVersion = "0.40.2"
val scalikeJdbcVersion = "3.4.2"
val scalikeJdbcPlayVersion = "2.8.0-scalikejdbc-3.4"
val flywayVersion = "6.0.0"
val hikariVersion = "3.4.2"
val ficusVersion = "1.4.7"
val swaggerVersion = "3.25.5"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikeJdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-config" % scalikeJdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikeJdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % scalikeJdbcPlayVersion,
  "org.flywaydb" %% "flyway-play" % flywayVersion,
  "com.zaxxer" % "HikariCP" % hikariVersion,
  "com.typesafe.play" %% "play-json" % playJsonVersion,
  "org.postgresql" % "postgresql" % postgresSqlVersion,
  "ai.x" %% "play-json-extensions" % playJsonExtensionsVersion,
  "com.iheart" %% "ficus" % ficusVersion,
  "org.webjars" % "swagger-ui" % swaggerVersion,
  guice,
  ehcache,
  ws
)

val testContainersVersion = "0.37.0"
val scalaTestVersion = "5.1.0"
val mockitoVersion = "1.14.4"

libraryDependencies ++= Seq(
  "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersVersion % Test,
  "com.dimafeng" %% "testcontainers-scala-postgresql" % testContainersVersion % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestVersion % Test,
  "org.mockito" % "mockito-scala_2.12" % mockitoVersion % Test,
  "org.scalikejdbc" %% "scalikejdbc-test" % scalikeJdbcVersion % Test
)

scalacOptions ++= Seq(
  "-Xfatal-warnings"
)

      
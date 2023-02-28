val scala3Version = "3.3.0-RC3"

organizationName := "Nigel Eke"
organization     := "nigeleke"

val bsd3License = Some(HeaderLicense.BSD3Clause("2023", "Nigel Eke"))

val log4jVersion     = "2.20.0"
val poiVersion       = "5.2.3"
val scalatestVersion = "3.2.15"
val squantsVersion   = "1.8.3"

lazy val root = project
  .in(file("."))
  .disablePlugins(HeaderPlugin)
  .settings(
    name           := "ohmic",
    publish / skip := true
  )
  .aggregate(core, app)

lazy val core = project
  .settings(
    name           := "ohmic-core",
    scalaVersion   := scala3Version,
    headerLicense  := bsd3License,
    publish / skip := true,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic"    % scalatestVersion,
      "org.typelevel"  % "squants_2.13" % squantsVersion,
      "org.scalatest" %% "scalatest"    % scalatestVersion % "test"
    )
  )

lazy val app = project
  .settings(
    name           := "ohmic-app",
    scalaVersion   := scala3Version,
    headerLicense  := bsd3License,
    publish / skip := true,
    libraryDependencies ++= Seq(
      "org.scalactic"           %% "scalactic"  % scalatestVersion,
      "org.apache.poi"           % "poi"        % poiVersion,
      "org.apache.poi"           % "poi-ooxml"  % poiVersion,
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
      "org.scalatest"           %% "scalatest"  % scalatestVersion % "test"
    )
  )
  .dependsOn(core)

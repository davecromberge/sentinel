import sbt._
import Keys._

object ApplicationBuild extends Build {
  override lazy val settings = super.settings ++
    Seq(
      name := "sentinel",
      version := "0.7.5.1",
      organization := "nl.gideondk",
      scalaVersion := "2.11.1",
      parallelExecution in Test := false,
      resolvers ++= Seq(Resolver.mavenLocal,
        "gideondk-repo" at "https://raw.github.com/gideondk/gideondk-mvn-repo/master",

        "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
        "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",

        "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"),

      publishTo := Some(Resolver.file("file", new File("/Users/gideondk/Development/gideondk-mvn-repo")))
    )

  val appDependencies = Seq(
    "org.scalatest" %% "scalatest" % "2.2.0" % "test",
    "com.typesafe.play" %% "play-iteratees" % "2.4.4",
    "com.typesafe.akka" %% "akka-actor" % "2.4.4",
    "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % "test"
  )

  lazy val root = Project(id = "sentinel",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      libraryDependencies ++= appDependencies,
      mainClass := Some("Main")
    ) ++ Format.settings
  )
}

object Format {
  import com.typesafe.sbt.SbtScalariform._

  lazy val settings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := formattingPreferences
  )

  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences().
      setPreference(AlignParameters, true).
      setPreference(AlignSingleLineCaseStatements, true).
      setPreference(DoubleIndentClassDeclaration, true).
      setPreference(IndentLocalDefs, true).
      setPreference(IndentPackageBlocks, true).
      setPreference(IndentSpaces, 2).
      setPreference(MultilineScaladocCommentsStartOnFirstLine, true).
      setPreference(PreserveSpaceBeforeArguments, false).
      setPreference(PreserveDanglingCloseParenthesis, false).
      setPreference(RewriteArrowSymbols, true).
      setPreference(SpaceBeforeColon, false).
      setPreference(SpaceInsideBrackets, false).
      setPreference(SpacesWithinPatternBinders, true)
  }
}

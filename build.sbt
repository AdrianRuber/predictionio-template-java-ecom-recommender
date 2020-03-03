name := "template-java-parallel-ecommercerecommendation"

scalaVersion := "2.11.12"
libraryDependencies ++= Seq(
  "org.apache.predictionio" %% "apache-predictionio-core" % "0.14.0" % "provided",
  "org.apache.spark"        %% "spark-mllib" % "2.4.0"    % "provided",
  "org.jblas"                % "jblas" % "1.2.4")

val stage = taskKey[Unit]("Stage task")

val Stage = config("stage")

stage := {
  (packageWar in Compile).value
  (update in Stage).value.allFiles.foreach { f =>
    if (f.getName.matches("webapp-runner-[0-9\\.]+.jar")) {
      println("copying " + f.getName)
      IO.copyFile(f, baseDirectory.value / "target" / "webapp-runner.jar")
    }
  }
}

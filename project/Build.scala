import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "ScalaDroid1",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-10"
  )

  val proguardSettings = Seq (
    useProguard in Android := true,
    proguardOption in Android :=  "-libraryjars /Users/Prashanth/opt/android-sdk-macosx-2/platforms/android-16/android.jar " +
                                  "-keep public class * extends android.app.Activity " +
                                  "-keep public class * extends android.app.Application " +
                                  "-keep public class * extends android.app.Service " +
                                  "-keep public class * extends android.content.BroadcastReceiver " +
                                  "-keep public class * extends android.content.ContentProvider " +
                                  "-keep public class * extends android.view.View {    public <init>(android.content.Context);    public <init>(android.content.Context, android.util.AttributeSet);    public <init>(android.content.Context, android.util.AttributeSet, int);    public void set*(...);} " +
                                  "-keepclasseswithmembers class * {    public <init>(android.content.Context, android.util.AttributeSet);} " +
                                  "-keepclasseswithmembers class * {    public <init>(android.content.Context, android.util.AttributeSet, int);} " +
                                  "-keepclassmembers class * extends android.content.Context {   public void *(android.view.View);   public void *(android.view.MenuItem);} " +
                                  "-keepclassmembers class * implements android.os.Parcelable {    static android.os.Parcelable$Creator CREATOR;} " +
                                  "-keepclassmembers class **.R$* { public static <fields>;} ",
    proguardOptimizations in Android := Seq(
      "-optimizationpasses 8",
      "-dontpreverify",
      "-repackageclasses ''",
      "-allowaccessmodification",
      "-optimizations !code/simplification/arithmetic",
      "-keepattributes *Annotation*"
    )
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++
    Seq (
      keyalias in Android := "change-me",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.8" % "test",
        "net.liftweb" % "lift-webkit_2.9.1" % "2.4" % "compile->default",
        "net.liftweb" % "lift-mapper_2.9.1" % "2.4" % "compile->default",
        "net.liftweb" % "lift-wizard_2.9.1" % "2.4" % "compile->default"
        //other libraries that can also be useful that work nicely
        //"net.databinder.dispatch" %% "core" % "0.9.0",
        //"com.codahale" % "jerkson_2.9.1" % "0.5.0"
        //"org.codehaus.jackson" % "jackson-mapper-asl" % "2.0.2",
        //"org.codehaus.jackson" % "jackson-core-asl" % "2.0.2",
        //"com.fasterxml" % "jackson-scala" % "1.9.1"
      ),
      resolvers ++= Seq(
        "Codahale Repo" at "http://repo.codahale.com",
         "Scala Tools Releases" at "https://oss.sonatype.org/content/groups/scala-tools/"
         //"Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
      )
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "ScalaDroid1",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               AndroidManifestGenerator.settings ++
               General.proguardSettings ++ Seq (
      name := "ScalaDroid1Tests",
      libraryDependencies += "junit" % "junit" % "4.10"
    )
  ) dependsOn main
}

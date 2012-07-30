import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "ScalaDroid1",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.2",
    platformName in Android := "android-16"
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
        "net.databinder.dispatch" %% "core" % "0.9.0",
        "com.codahale" % "jerkson_2.9.1" % "0.5.0"
      ),
      resolvers ++= Seq(
        "Codahale Repo" at "http://repo.codahale.com"
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

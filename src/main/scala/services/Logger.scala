package services

import android.util.Log
import java.text.{SimpleDateFormat, DateFormat}
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 3:29 AM
 * To change this template use File | Settings | File Templates.
 */

trait Logger {
  lazy val callingMethod = new RuntimeException().getStackTrace.apply(2).toString
  val dateFormat  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")


  def warn(msg : String) = {
    //TODO get TAG field from object because we can use logcat to filter the logs based on the tag for any calling class and priority level
    //then again we could always grep in which case the date are more useful, could use a combination of both by splitting the tag
    val loggerString = " : [" + (dateFormat format (new Date())) + "]" +" : " + callingMethod + " :  "
    Log.w(Logger.WARN + loggerString, msg)
  }
}

object Logger {
  val WARN = "WARN"
  val DEBUG = "DEBUG"
  val INFO = "INFO"
  val ERROR = "ERROR"
}

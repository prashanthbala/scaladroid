package services

import android.util.Log

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 3:29 AM
 * To change this template use File | Settings | File Templates.
 */

trait Logger {
  lazy val callingMethod = new RuntimeException().getStackTrace.apply(0).toString

  def warn(msg : String) = {
    Log.w(Logger.WARN + " : Class["+getClass.getCanonicalName+"] at method : [" + callingMethod+"] with message : ", msg)
  }
}

object Logger {
  val WARN = "WARN"
  val DEBUG = "DEBUG"
  val INFO = "INFO"
  val ERROR = "ERROR"
}

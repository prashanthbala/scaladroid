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
  def warn(msg : String) = {
    Log.w(getClass.getCanonicalName, msg)
  }
}

package com.prashanthbala.personal.androidscala.view

import android.app.{Activity, ProgressDialog}
import android.content.Context
import actors.Future
import scala.concurrent.ops.spawn
import com.prashanthbala.personal.androidscala.common.Threadify
import android.os.{SystemClock, AsyncTask}
import android.widget.{ProgressBar, Button}

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */

trait Loading {
  def showProgressBar[T <: Context] (that: T, msg : String, cancellable : Boolean = true) (block : => Unit) : Unit = {
    val progressDialog : ProgressDialog = new ProgressDialog(that)
    progressDialog setMessage msg
    progressDialog setProgressStyle ProgressDialog.STYLE_SPINNER
    progressDialog setCancelable cancellable
    progressDialog setIndeterminate true
    progressDialog setTitle ""
    progressDialog.show

    spawn(block)

    //progress Dialog.dismiss  - gets dismissed to soon to be displayed, which is fine usually since the delay is nothing
    //but for this app it's too small to even see the loading bar
  }

  //TODO implement a method that uses horizontal progress bar, takes a cancellation callback, and gives progress updates
  //maybe need to use async task for that http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/


  /*def asyncProgressBar[T <: Context, U] (that: T, msg: String, cancellable: Boolean = true) (block : => U) : U {

  }
  class ProgressAsyncTask(button : Option[Button], progressBar: ProgressBar) extends AsyncTask[String, Int, String] {

    var progress = 0

    override def onPostExecute(result : String) : Unit = {
      button match {
        case Some(button) => button.setClickable(true)
        case None => //do nothing
      }
    }

    override def onPreExecute() : Unit = {
      progress = 0
      button match {
        case Some(button) => button.setClickable(false)
        case None => //do nothing
      }
    }

    override def doInBackground(params : String*) : String = {
      while(progress<100){
        progress+=1
        publishProgress(progress)
        SystemClock.sleep(100)
      }
      "done"
    }

    override def onProgressUpdate(values : Int*) : Unit = {
      progressBar.setProgress(values)
    }
  }  */
}

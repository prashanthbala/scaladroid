package com.prashanthbala.personal.androidscala.view

import android.app.ProgressDialog
import android.content.Context
import actors.Future
import com.prashanthbala.personal.androidscala.services.Logger
import scala.actors.Futures.synchronized

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */

trait Loading extends Logger{
  def showProgressBar[T <: Context, U] (that: T, msg : String, cancellable : Boolean = true) (block : => Future[U]) : U = {
    val progressDialog : ProgressDialog = new ProgressDialog(that)
    progressDialog setMessage msg
    progressDialog setProgressStyle ProgressDialog.STYLE_SPINNER
    progressDialog setCancelable cancellable
    progressDialog setIndeterminate true
    progressDialog setTitle ""
    progressDialog.show

    val result = block.apply

    //progressDialog.dismiss  - gets dismissed to soon to be displayed, which is fine usually since the delay is nothing
    //but for this app it's too small to even see the loading bar
    result
  }

  //TODO implement a method that uses horizontal progress bar, takes a cancellation callback, and gives progress updates
  //maybe need to use async task for that http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/
}

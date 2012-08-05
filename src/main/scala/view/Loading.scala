package view

import android.app.ProgressDialog
import android.content.Context
import actors.Future
import services.Logger

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */

trait Loading extends Logger{
  def showProgressBar[T <: Context, U] (that: T, msg : String, cancellable : Boolean = false) (block : => Future[U]) : U = {
//    val dialog : ProgressDialog = ProgressDialog.show(that, "",
//      msg, true, cancellable)
    val progressDialog : ProgressDialog = new ProgressDialog(that)
    progressDialog setMessage msg
    progressDialog setProgressStyle ProgressDialog.STYLE_HORIZONTAL
    progressDialog setCancelable cancellable
    progressDialog setIndeterminate true
    progressDialog setTitle ""
    progressDialog.show

    val result = block.apply
//    progressDialog.dismiss
    result
  }

  //TODO implement a method that takes a cancellation callback and implement show using that
}

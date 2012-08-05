package view

import android.app.ProgressDialog
import android.content.Context
import actors.Future

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */

trait Loading {
  def showProgressBar[T <: Context, U] (that: T, msg : String) (block : => Future[U]) : U = {
    val progressDialog : ProgressDialog = new ProgressDialog(that)
    progressDialog setMessage msg
    progressDialog setProgressStyle ProgressDialog.STYLE_SPINNER
    progressDialog setCancelable false
    progressDialog.show
    val result = block.apply
    progressDialog.dismiss
    result
  }
}

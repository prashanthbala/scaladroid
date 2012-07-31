package com.prashanthbala.personal.androidscala.test1

import android.os.{Parcelable, Bundle}
import android.widget.EditText
import android.view.View
import android.content.Intent
import android.app.Activity
import dispatch._
import com.codahale.jerkson.Json._

class MainActivity extends Activity with TypedActivity with ApacheHttpClient{
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    findView(TR.editTextField) setHint "type something here"
  }

  case class Message(message: String)

  implicit def toRunnable[F](f: => F): Runnable =
    new Runnable() { def run() = f }

  def sendMessage(view: View): Unit = {
    val intent: Intent = new Intent(this, classOf[DisplayMessageActivity])
    //val message : String = findView[EditText](TR.editTextField).getText.toString

    val url = R.conf.Symbol("default_api_base_url")

    val message = get(url)

    intent.putExtra(MainActivity.EXTRA_MESSAGE, message())
    startActivity(intent)
  }

  override def onStop() {
    super.onStop()

    Http.shutdown()
  }

}

object  MainActivity {
  val EXTRA_MESSAGE : String = "com.prashanthbala.personal.androidscala.MESSAGE"
}

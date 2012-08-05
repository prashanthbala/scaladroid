package com.prashanthbala.personal.androidscala.activities

import android.os.{Parcelable, Bundle}
import android.widget.{Toast, EditText}
import android.view.View
import android.content.Intent
import android.app.{ProgressDialog, Activity}
import actors.Future
import actors.Futures._
import com.prashanthbala.personal.androidscala.{TR, R, TypedActivity}
import com.prashanthbala.personal.androidscala.services.{Logger, ApacheHttpClient}
import net.liftweb.json._
import com.prashanthbala.personal.androidscala.view.Loading

class MainActivity extends TypedActivity with ApacheHttpClient with Logger with Loading {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    findView(TR.editTextField) setHint "type something here"
  }

  case class Message(message: String)

  implicit def toRunnable[F](f: => F): Runnable =
    new Runnable() {
      def run() = f
    }

  def sendMessage(view: View): Unit = {
    val intent: Intent = new Intent(this, classOf[DisplayMessageActivity])
    val customUrl: String = findView[EditText](TR.editTextField).getText.toString

    val url: String = customUrl.isEmpty match {
      case true => MainActivity.DEFAULT_URL
      case false => customUrl
    }

    val message: String = showProgressBar[this.type, Option[String]](MainActivity.this, "Fetching Data...") {
      get(url)
    }.getOrElse("""{"message" : "Something went wrong while getting message from server"}""")

    implicit val formats = DefaultFormats

    debug ("This is the message : " + message)

    val maybeMsg = (JsonParser.parse(message) \ ("message")).extractOpt[String].getOrElse("Error occured while parsing message recieved from server")
    Toast.makeText(MainActivity.this, maybeMsg, Toast.LENGTH_SHORT).show

    intent.putExtra(MainActivity.EXTRA_MESSAGE, maybeMsg)
    debug ("This is the parsed message : " + maybeMsg)
    startActivity(intent)
  }

  override def onStop() {
    super.onStop()
  }

}

object MainActivity {
  val EXTRA_MESSAGE: String = "com.prashanthbala.personal.androidscala.MESSAGE"
  val DEFAULT_URL: String = "http://10.0.2.2:9000/json"
}

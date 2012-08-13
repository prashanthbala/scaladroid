package com.prashanthbala.personal.androidscala.activities

import android.os.Bundle
import android.widget.{Toast, EditText}
import android.view.View
import android.content.Intent
import android.app.Activity
import com.prashanthbala.personal.androidscala.services.{Logger, ApacheHttpClient}
import com.prashanthbala.personal.androidscala.view.Loading
import com.prashanthbala.personal.androidscala.R._
import org.json._
import com.prashanthbala.personal.androidscala.common.Threadify

class MainActivity extends Activity with ApacheHttpClient with Logger with Loading with Threadify{
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(layout.main)
    (findViewById(id.editTextField).asInstanceOf[EditText]) setHint "type something here"
  }

  case class Message(message: String)

  def sendMessage(view: View): Unit = {
    val intent: Intent = new Intent(this, classOf[DisplayMessageActivity])
    val customUrl: String = (findViewById(id.editTextField).asInstanceOf[EditText]).getText.toString

    val url: String = customUrl.isEmpty match {
      case true => MainActivity.DEFAULT_URL
      case false => customUrl
    }

    showProgressBar(MainActivity.this, "Loading bro ... ", true) {
      val message = get(url).apply().getOrElse("""{"message" : "Something went wrong while getting message from server"}""")
      debug ("This is the message : " + message)

      val msg = new JSONObject(message).optString("message", "yo")

      runOnUiThread (Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show)

      intent.putExtra(MainActivity.EXTRA_MESSAGE, msg)
      debug ("This is the parsed message : " + msg)
      runOnUiThread (startActivity(intent))
    }
  }

  override def onStop() {
    super.onStop()
  }

}

object MainActivity {
  val EXTRA_MESSAGE: String = "com.prashanthbala.personal.androidscala.MESSAGE"
  val DEFAULT_URL: String = "http://10.0.2.2:9000/json"
}

package com.prashanthbala.personal.androidscala.test1

import _root_.android.os.Bundle
import android.widget.EditText
import android.view.View
import android.content.Intent
import android.app.Activity
import dispatch._
import com.codahale.jerkson.Json._
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import scala.concurrent.ops._
import org.apache.http.params.HttpConnectionParams

class MainActivity extends Activity with TypedActivity {
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
    val ip : String = findView[EditText](TR.editTextField).getText.toString


    /*val svc = url("http://localhost:9000/json")
    val json : Promise[Option[String]] = Http(svc OK as.String).option

    val message = json() match {
      case Some(_json) => parse[Message](_json).message
      case None => ""
    }*/

    val connectionTimeOutSec = 50
    val socketTimeoutSec = 50

    spawn {
      val url = "http://"+ip+":9000/json"
      val httpGet = new HttpGet(url)
      val httpParams = httpGet.getParams
      HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeOutSec * 1000)
      HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutSec * 1000)
      val httpClient = new DefaultHttpClient(httpParams) //params are optional, but prevent timeouts

      val message = try {
        val response = httpClient.execute(httpGet)
        val entity = response.getEntity
        var content = ""
        if (entity != null) {
          val inputStream = entity.getContent
          content = io.Source.fromInputStream(inputStream).getLines.mkString
          inputStream.close
        }
        httpClient.getConnectionManager.shutdown
        parse[Message](content).message
      }
      catch {
        case e : Exception => "Something went wrong. getting - "+ e.getStackTrace.toString + e.getMessage + " while connecting to url - " + url
      }

      runOnUiThread {
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message)
        startActivity(intent)
      }
    }
  }

  override def onStop() {
    super.onStop()

    Http.shutdown()
  }

}

object  MainActivity {
  val EXTRA_MESSAGE : String = "com.prashanthbala.personal.androidscala.MESSAGE"
}

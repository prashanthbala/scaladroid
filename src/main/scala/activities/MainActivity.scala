package activities

import android.os.{Parcelable, Bundle}
import android.widget.EditText
import android.view.View
import android.content.Intent
import android.app.Activity
import com.codahale.jerkson.Json._
import actors.Future
import actors.Futures._
import com.prashanthbala.personal.androidscala.test1.{TR, R, TypedActivity}
import services.ApacheHttpClient


class MainActivity extends Activity with TypedActivity with ApacheHttpClient {
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
    val customUrl : String = findView[EditText](TR.editTextField).getText.toString

    val url: String = customUrl.isEmpty match {
      case true => MainActivity.DEFAULT_URL
      case false => customUrl
    }

    val message: Future[Option[String]] = get(url)

    val _message = message.apply()

    intent.putExtra(MainActivity.EXTRA_MESSAGE, _message.getOrElse("Something went wrong while getting message from server"))
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

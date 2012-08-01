package activities

import android.os.{Parcelable, Bundle}
import android.widget.EditText
import android.view.View
import android.content.Intent
import android.app.Activity
import com.codahale.jerkson.Json._
import actors.Future
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
    //val message : String = findView[EditText](TR.editTextField).getText.toString

    val url: String = MainActivity.DEFAULT_URL

    val message: Future[Option[String]] = get(url)

    val sendMessage = message map {
      msg =>
        intent.putExtra(MainActivity.EXTRA_MESSAGE, msg.getOrElse("Nothing to do here"))
        startActivity(intent)
    }
    sendMessage.run
  }

  override def onStop() {
    super.onStop()
  }

}

object MainActivity {
  val EXTRA_MESSAGE: String = "com.prashanthbala.personal.androidscala.MESSAGE"
  val DEFAULT_URL: String = "http://10.0.2.2:9000/"
}

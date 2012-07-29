package com.prashanthbala.personal.androidscala.test1

import _root_.android.os.Bundle
import android.widget.EditText
import android.view.View
import android.content.Intent
import android.app.Activity

class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
    findView(TR.editTextField) setHint "type something here"
  }

  def sendMessage(view: View): Unit = {
    val intent: Intent = new Intent(this, classOf[DisplayMessageActivity])
    val message : String = findView[EditText](TR.editTextField).getText.toString
    intent.putExtra(MainActivity.EXTRA_MESSAGE, message)
    startActivity(intent)
  }

}

object  MainActivity {
  val EXTRA_MESSAGE : String = "com.prashanthbala.personal.androidscala.MESSAGE"
}

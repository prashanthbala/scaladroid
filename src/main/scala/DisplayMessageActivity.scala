package com.prashanthbala.personal.androidscala.test1

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.widget.TextView

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 7/29/12
 * Time: 2:07 AM
 * To change this template use File | Settings | File Templates.
 */

class DisplayMessageActivity extends Activity with TypedActivity {
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(TR.layout.activity_display_message.id)
    val intent: Intent = getIntent
    val msg : String = intent getStringExtra MainActivity.EXTRA_MESSAGE

    val text: TextView = new TextView(this)
    text setTextSize 10
    text setText msg

    setContentView(text)
  }
}

package com.prashanthbala.personal.androidscala.activities

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.widget._
import android.widget.AdapterView.OnItemClickListener
import android.view.View
import com.prashanthbala.personal.androidscala.view.ImageAdapter
import com.prashanthbala.personal.androidscala.R._

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 7/29/12
 * Time: 2:07 AM
 * To change this template use File | Settings | File Templates.
 */

class DisplayMessageActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(layout.main)
    val intent: Intent = getIntent
    val msg: String = intent getStringExtra MainActivity.EXTRA_MESSAGE

    val text: TextView = new TextView(this)
    text setTextSize 10
    text setText msg

    setContentView(text)

    setContentView(layout.activity_display_message)

    val strings: Array[String] = Array[String]("yo", "wattup", "how does it")
    val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1, strings)
    val gridView: GridView = findViewById(id.gridview).asInstanceOf[GridView]
    gridView setAdapter (new ImageAdapter(this))

    // Create a message handling object as an anonymous class.
    gridView.setOnItemClickListener(new OnItemClickListener {
      def onItemClick(adapterView: AdapterView[_], v: View, position: Int, id: Long) {
        Toast.makeText(DisplayMessageActivity.this, "" + position, Toast.LENGTH_SHORT).show
      }
    })
  }

  def sendMessage(view: View): Unit = {
    //do nothing
  }
}

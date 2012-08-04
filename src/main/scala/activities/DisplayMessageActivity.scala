package activities

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.widget._
import com.prashanthbala.personal.androidscala.test1.{TR, TypedActivity}
import android.widget.AdapterView.OnItemClickListener
import android.view.View
import view.ImageAdapter

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 7/29/12
 * Time: 2:07 AM
 * To change this template use File | Settings | File Templates.
 */

class DisplayMessageActivity extends TypedActivity {
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(TR.layout.activity_display_message.id)
    val intent: Intent = getIntent
    val msg: String = intent getStringExtra MainActivity.EXTRA_MESSAGE

    val text: TextView = new TextView(this)
    text setTextSize 10
    text setText msg

    setContentView(text)

    setContentView(TR.layout.activity_display_message.id)

    val strings : Array[String] = Array[String]("yo", "wattup", "how does it")
    val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1, strings)
    val gridView : GridView = findView(TR.gridview)
    gridView setAdapter(new ImageAdapter(this))

//    object x extends OnItemClickListener {
//      implicit def adapterViewWrapper[T <: AdapterView](adpt: T) = new AdapterView(adpt.getContext)
//      override def onItemClick(adapterView : T, v : View, position : Int, id : Long) {
//        Toast.makeText(DisplayMessageActivity.this, "" + position, Toast.LENGTH_SHORT).show();
//      }
//    }
//
//    // Create a message handling object as an anonymous class.
//    gridView.setOnItemClickListener(x)
  }

  def sendMessage(view: View): Unit = {
    //do nothing
  }
}

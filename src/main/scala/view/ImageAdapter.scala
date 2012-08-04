package view

import android.widget.{GridView, ImageView, BaseAdapter}
import android.view.{ViewGroup, View}
import android.content.Context
import com.prashanthbala.personal.androidscala.test1.R

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/4/12
 * Time: 1:35 AM
 * To change this template use File | Settings | File Templates.
 */

class ImageAdapter(mContext : Context) extends BaseAdapter{
  def getCount : Int = {
    mThumbIds.length
  }
  def getItem (postition : Int) : AnyRef = {
    null
  }

  def getItemId (postition : Int) : Long = {
    0L
  }

  def getView(position : Int, convertView : View, parent : ViewGroup) : View = {
    val imageView = convertView match {
      case null =>  // if it's not recycled, initialize some attributes
        val imageView : ImageView = new ImageView(mContext)
        imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85))
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        imageView.setPadding(8, 8, 8, 8)
        imageView

      case _ => convertView.asInstanceOf[ImageView]
    }
    imageView.setImageResource(mThumbIds(position))
    imageView
  }

  private val mThumbIds : Array[Int] = Array(
    R.drawable.sample_2, R.drawable.sample_3,
    R.drawable.sample_4, R.drawable.sample_5,
    R.drawable.sample_6, R.drawable.sample_7,
    R.drawable.sample_0, R.drawable.sample_1,
    R.drawable.sample_2, R.drawable.sample_3,
    R.drawable.sample_4, R.drawable.sample_5,
    R.drawable.sample_6, R.drawable.sample_7,
    R.drawable.sample_0, R.drawable.sample_1,
    R.drawable.sample_2, R.drawable.sample_3,
    R.drawable.sample_4, R.drawable.sample_5,
    R.drawable.sample_6, R.drawable.sample_7
  )
}

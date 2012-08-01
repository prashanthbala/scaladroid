package com.prashanthbala.personal.androidscala.test1.tests

import com.prashanthbala.personal.androidscala.test1._
import junit.framework.Assert._
import _root_.android.test.AndroidTestCase
import _root_.android.test.ActivityInstrumentationTestCase2
import activities.MainActivity

class AndroidTests extends AndroidTestCase {
  def testPackageIsCorrect() {
    assertEquals("com.prashanthbala.personal.androidscala.test1", getContext.getPackageName)
  }
}

class ActivityTests extends ActivityInstrumentationTestCase2(classOf[MainActivity]) {
   def testHelloWorldIsShown() {
      val activity = getActivity
      val textview = activity.findView(TR.textview)
      assertEquals(textview.getText, "hello, world!")
    }
}

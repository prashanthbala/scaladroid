package com.prashanthbala.personal.androidscala.common

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 8/10/12
 * Time: 12:57 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.concurrent.Callable
import android.app.Activity
import concurrent.ops._

trait Threadify {

  implicit def runnable[F](f: => F): Runnable =
    new Runnable() { def run() = f }


  implicit def callable[T](f: () => T): Callable[T] =
    new Callable[T]() { def call() = f() }

  def async[T <: Activity, ResultType](that : T)(operations: () => ResultType)(deliverResult: ResultType => Unit) = {
    spawn {
      val res = operations()
      that.runOnUiThread(deliverResult)
    }
  }
}

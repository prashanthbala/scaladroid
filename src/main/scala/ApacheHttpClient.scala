package com.prashanthbala.personal.androidscala.test1

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 7/31/12
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import scala.concurrent.ops._
import org.apache.http.params.HttpConnectionParams
import android.util.Log
import parallel.Future


trait ApacheHttpClient {

  def get(url: String): () => Option[String] = {
    val connectionTimeOutSec = R.conf.Symbol("Apache_connection_TimeOutSec")
    val socketTimeoutSec = R.conf.Symbol("Apache_connection_socket_TimeoutSec")

    future[Option[String]] {
      val httpGet = new HttpGet(url)
      val httpParams = httpGet.getParams
      HttpConnectionParams setConnectionTimeout(httpParams, connectionTimeOutSec * 1000)
      HttpConnectionParams setSoTimeout(httpParams, socketTimeoutSec * 1000)
      val httpClient = new DefaultHttpClient(httpParams) //params are optional, but prevent timeouts

      val message = try {
        val response = httpClient execute (httpGet)
        val entity = response getEntity
        var content = ""
        entity match {
          case null => //do nothing
          case _ => val inputStream = entity.getContent
          content = io.Source.fromInputStream(inputStream).getLines.mkString
          inputStream.close
        }
        httpClient.getConnectionManager.shutdown

        content != "" match {
          case true => None
          case false => Some(content)
        }
      }
      catch {
        case e: Exception => Log.w(ApacheHttpClient.TAG, ("Something went wrong. getting while connecting to url" + url), e)
                             None
      }
    }
  }
}

object ApacheHttpClient {
  val TAG = "ApacheHttpClient.scala"
}

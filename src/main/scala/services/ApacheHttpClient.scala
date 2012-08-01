package services


import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.{ClientProtocolException, HttpClient}
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import scala.concurrent.ops._
import org.apache.http.params.HttpConnectionParams
import android.util.Log
import scala.actors.Futures.future
import scala.actors.Future
import org.apache.http.{HttpResponse, NameValuePair}
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import java.io.IOException
import org.json.JSONObject

/**
 * Created with IntelliJ IDEA.
 * User: Prashanth
 * Date: 7/31/12
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */

trait ApacheHttpClient {

  def get(url: String): Future[Option[String]] = {
    val connectionTimeOutSec = ApacheHttpClient.DEFAULT_CONNECTION_SOCKET_TIMEOUT_SECONDS
    val socketTimeoutSec = ApacheHttpClient.DEFAULT_CONNECTION_SOCKET_TIMEOUT_SECONDS

    future[Option[String]] {
      val httpGet = new HttpGet(url)
      val httpParams = httpGet.getParams
      HttpConnectionParams setConnectionTimeout(httpParams, connectionTimeOutSec * 1000)
      HttpConnectionParams setSoTimeout(httpParams, socketTimeoutSec * 1000)
      val httpClient = new DefaultHttpClient(httpParams) //params are optional, but prevent timeouts

      val message = try {
        var content=""
        val response = httpClient execute (httpGet)
        val entity = Option(response getEntity)
        entity match {
          case None  => //do nothing
          case Some(_entity) =>
            val inputStream = _entity.getContent
            content = io.Source.fromInputStream(inputStream).getLines.mkString
            inputStream.close
        }
        httpClient.getConnectionManager.shutdown

        content.isEmpty match {
          case true => None
          case false => Some(content)
        }
      }
      catch {
        // Expected types of exceptions - ClientProtocolException, IOException
        case e: Exception => Log.w(ApacheHttpClient.TAG, ("Something went wrong. getting while GET connecting to url" + url), e)
        None
      }
      message
    }
  }

  def post(url: String, body: Map[String, String]): Future[Option[String]] = {
    future[Option[String]] {
      // Create a new HttpClient and Post Header
      val httpclient: HttpClient = new DefaultHttpClient()
      val httppost: HttpPost = new HttpPost(url)

      val keyValuePairs : List[BasicNameValuePair] = body.keys map {
        key =>
          val value = body(key)
          new BasicNameValuePair(key, value)
      } toList

      httppost.setEntity(new UrlEncodedFormEntity(keyValuePairs.asInstanceOf[java.util.List[BasicNameValuePair]]))

      try {
        // Execute HTTP Post Request
        val response: HttpResponse = httpclient.execute(httppost)
        val reason = response.getStatusLine.getReasonPhrase
        Some(reason)
      }
      catch {
        // Expected types of exceptions - ClientProtocolException, IOException
        case e: Exception => Log.w(ApacheHttpClient.TAG, ("Something went wrong. getting while POST connecting to url" + url), e)
        None
      }
    }
  }

  def jsonPost(url : String, json : JSONObject) : Future[JSONObject] = {
    future[JSONObject] {
      JsonPostClient.SendHttpPost(url, json)
    }
  }

}

object ApacheHttpClient {
  val TAG = "ApacheHttpClient.scala"
  val DEFAULT_CONNECTION_TIMEOUT_SECONDS = 50
  val DEFAULT_CONNECTION_SOCKET_TIMEOUT_SECONDS = 50
}

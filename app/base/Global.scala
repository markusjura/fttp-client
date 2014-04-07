package base

import play.api._
import play.api.libs.ws.WS
import play.api.libs.json._
import play.api.Play._
import scala.collection.JavaConverters._

object Global extends GlobalSettings {
  // Client Url
  private val clientUrl: JsValue = Json.obj("url" -> "http://localhost:9000/logs/receive")

  // Log server URLs
  private def logServers(implicit app: Application): Seq[String] =
    app.configuration.getStringList("service.log.urls").get.asScala.toSeq

  override def onStart(app: Application): Unit =
    logServers.foreach(url => WS.url(s"$url/logs/subscribe").post(clientUrl))

  override def onStop(app: Application): Unit =
    logServers.foreach(url => WS.url(s"$url/logs/unsubscribe").post(clientUrl))
}

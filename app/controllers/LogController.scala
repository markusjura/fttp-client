package controllers

import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._
import play.api.Play.current
import play.api.Logger
import play.api.libs.json._
import play.api.libs.iteratee._
import play.api.libs.EventSource
import play.api.libs.concurrent.Akka
import scala.concurrent.duration._
import play.api.mvc.Security._

object LogController extends Controller {

  val (logOut, logChannel) = Concurrent.broadcast[JsValue]

  def receive = Action(parse.json) { request =>
    logChannel.push(request.body)
    Ok(Json.obj("success" -> true))
  }

  def logFeed = Action { request =>
    Logger.debug(request.remoteAddress + " - SSE connected")
    Ok.chunked(
      logOut
      &> EventSource()
    ).as("text/event-stream")
  }
}

package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Security._
import play.api.Play.configuration
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import models._
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import play.api.libs.json._

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}
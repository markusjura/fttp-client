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

  val usernameSessionKey = "username"
  def requestUsername(request: RequestHeader) = request.session.get(usernameSessionKey)
  def onUnauthorized(request: RequestHeader) = Redirect(routes.Application.login)

  def index = Authenticated(requestUsername, onUnauthorized) { username =>
    Action { implicit request =>
      Ok(views.html.index())
    }
  }

  val loginForm: Form[LoginCredentials] = Form {
    mapping(
      "username" -> nonEmptyText(minLength = 4),
      "password" -> nonEmptyText(minLength = 4)
    )(LoginCredentials.apply)(LoginCredentials.unapply)
  }

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  private lazy val authServiceUrl = configuration.getString("service.auth.url").get

  def submit = Action.async { implicit request =>
    def onError(formWithErrors: Form[LoginCredentials]) =
      Future.successful(BadRequest(views.html.login(formWithErrors)))

    def onSuccess(credentials: LoginCredentials) = {
      WS.url(s"$authServiceUrl/auth").post(Json.toJson(credentials)).map { response =>
        response.status match {
          case OK => Redirect(routes.Application.index)
            .withSession(usernameSessionKey -> credentials.username)

          case _ =>
            val errorForm = loginForm.fill(credentials).withGlobalError((response.json \ "error").as[String])
            BadRequest(views.html.login(errorForm))
        }
      }
    }

    loginForm.bindFromRequest().fold(onError, onSuccess)
  }

  def logout = Action { implicit request =>
    Redirect(routes.Application.login).withNewSession
  }
}
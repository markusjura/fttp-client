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
  def onUnauthorized(request : RequestHeader) = Redirect(routes.Application.login)

  def index = Authenticated(requestUsername, onUnauthorized) { username =>
    Action { implicit request =>
      Ok(views.html.index())
    }
  }

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def logout = Action { implicit request =>
    Redirect(routes.Application.login).withNewSession
  }

  val loginForm : Form[LoginCredentials] = Form {
    mapping(
      "username" -> nonEmptyText(minLength = 4), // verify the username
      "password" -> nonEmptyText(minLength = 4) // verify the password
    )(LoginCredentials.apply)(LoginCredentials.unapply)
  }

  // Bid service URL
  private lazy val authServiceUrl = configuration.getString("service.auth.url").get

  def submit = Action.async { implicit request =>
    //methods for handling a form. Notice that the return type has to be the same
    def onError(formWithErrors: Form[LoginCredentials]) =
      Future.successful(BadRequest(views.html.login(formWithErrors)))
    def onSuccess(credentials: LoginCredentials) = {
      // credentials passed requirements. Check if the user and password exist on the server side.
      WS.url(s"$authServiceUrl/auth").post(Json.toJson(credentials)).map { response =>
        response.status match {
          case OK => Redirect(routes.Application.index).withSession(usernameSessionKey -> credentials.username)
          case _  => {
            val errorForm = loginForm.fill(credentials).withGlobalError((response.json \ "error").as[String])
            BadRequest(views.html.login(errorForm))
          }
        }
      }
    }

    loginForm.bindFromRequest.fold(onError, onSuccess)
  }

}
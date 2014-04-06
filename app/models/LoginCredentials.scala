package models

import play.api.libs.json.Json

case class LoginCredentials(username: String, password: String)
object LoginCredentials { implicit val loginCredentialsFormat = Json.format[LoginCredentials] }
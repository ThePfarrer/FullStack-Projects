package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.User

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {
  def signIn() = Action { implicit request =>
    try {
      var user = User
      if (!user) {
        val message = Map("error" -> "User not found")
        val json: JsValue = Json.toJson(message)
        Unauthorized(json)
      }
      if (!user) {
        val message = Map("error" -> "Email and password don't match.")
        val json: JsValue = Json.toJson(message)
        Unauthorized(json)
      }
    } catch (err) {
      val message = Map("error" -> "Could not sign in")
      val json: JsValue = Json.toJson(message)
      Unauthorized(json)
    }
  }

  def signOut() = Action { implicit request =>
    val message = Map("message" -> "signed out")
    val json = Json.toJson(message)
    Ok(json)
  }
}

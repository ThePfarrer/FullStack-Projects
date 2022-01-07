package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
    extends BaseController {
  def create() = Action {
    try {
      val message = Map("message" -> "Successfully signed up!")
        val json: JsValue = Json.toJson(message)
        Ok(json)
      } catch(err) {
        val message = Map("error" -> "User not found")
        val json: JsValue = Json.toJson(message)
        Unauthorized(json)

      }
  }

  def userByID() = Action{
    try {
      val message = Map("message" -> "Successfully signed up!")
        val json: JsValue = Json.toJson(message)
        Ok(json)
      } catch(err) {
        val message = Map("error" -> "User not found")
        val json: JsValue = Json.toJson(message)
        Unauthorized(json)

      }
  }
 
  def read() = Action{

  }

  def list() = Action{

  }

  def update() = Action{

  }

  def remove() = Action{
  }

  
}


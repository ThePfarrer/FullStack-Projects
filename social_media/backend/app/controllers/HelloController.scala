package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

@Singleton
class HelloController @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
    extends BaseController {
  def api = Action {
    val message = Map("test" -> "Hello world2")
    val json = Json.toJson(message)
    Ok(json)
  }
}

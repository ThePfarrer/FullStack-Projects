package models
import play.api.libs.json.Json

case class SignInRequest(email: String, password: String)

object SignInRequest {
  implicit val signInRequestFormat = Json.format[SignInRequest]
}

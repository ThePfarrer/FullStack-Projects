package models
import play.api.libs.json.Json

case class SignInRequest(email: String, password: String)

case class PersonAuth(id: Long, name: String, email: String)

case class Auth(token: String, person: PersonAuth)

object SignInRequest {
  implicit val signInRequestFormat = Json.format[SignInRequest]
}

object PersonAuth {
  implicit val PersonAuthFormat = Json.format[PersonAuth]
}

object Auth {
  implicit val AuthFormat = Json.format[Auth]
}

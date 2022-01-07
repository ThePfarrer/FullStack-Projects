package models

import play.api.libs.json._
import java.time.LocalDate

case class User(name: String, email: String, password: String, created: LocalDate, updated: LocalDate)

object User {
  implicit val userFormat = Json.format[User]
}

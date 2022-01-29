package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Person, SignInRequest}
import services.PersonService
import org.mindrot.jbcrypt.BCrypt
import pdi.jwt.{JwtJson, JwtAlgorithm}

import scala.concurrent.{ExecutionContext, Future}

// @Singleton
@Singleton
class AuthController @Inject() (
    val controllerComponents: ControllerComponents,
    personService: PersonService
)(implicit ec: ExecutionContext)
    extends BaseController {
// class AuthController @Inject() (val controllerComponents: ControllerComponents)
//     extends BaseController {
  
  val key = "secretKey"
  val algo = JwtAlgorithm.HS256

  def signIn() = Action(parse.json).async { implicit request =>
    request.body
      .validate[SignInRequest]
      .fold(
        errors =>
          Future {
            Unauthorized(Json.toJson(Map("error" -> "Could not sign in")))
          },
        data => {
          personService.getPerson(data.email).flatMap {
            case None =>
              Future.successful(
                Unauthorized(Json.toJson(Map("error" -> "Person not found")))
              )
            case Some(person) =>
              if (BCrypt.checkpw(data.password, person.password)) {
                val claim = Json.toJsObject(Map("id" -> person.id))
                val token = JwtJson.encode(claim, key, algo)
                
                println(token)
                val responseBody = Json.toJson(
                  token,
                  Map(
                    "person" -> Map(
                      "id" -> person.id.toString,
                      "name" -> person.name,
                      "email" -> person.email
                    )
                  )
                )
                Future {
                  Ok(responseBody).withCookies(Cookie("t", token, Some(100)))
                }
              } else {
                Future {
                  Unauthorized(
                    Json
                      .toJson(Map("error" -> "Email and password don't match"))
                  )
                }
              }

          }
        }
      )
  }

  def signOut() = Action { implicit request =>
    Ok(Json.toJson(Map("message" -> "signed out")))
      .discardingCookies(DiscardingCookie("t"))
  }

  def hasAuthorization = TODO

  def requireSignIn = TODO

}

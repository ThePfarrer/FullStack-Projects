package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Person
import services.PersonService
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PersonController @Inject() (
    val controllerComponents: ControllerComponents,
    personService: PersonService
)(implicit ec: ExecutionContext)
    extends BaseController {

  def index() = TODO

  def create() = Action(parse.json).async { implicit request =>
    val emailPattern = "\\w+@\\w+.\\w{2,10}".r
    val json = request.body
    json
      .validate[Person]
      .fold(
        errors =>
          Future {
            BadRequest(Json.toJson(Map("error" -> "Request body is invalid")))
          },
        data => {
          if (
            emailPattern.matches(
              data.email
            ) && data.name.trim.length != 0 && data.password.trim.length >= 6
          ) {
            personService.getPerson(data.email).flatMap {
              case Some(_) =>
                Future.successful(
                  BadRequest(Json.toJson(Map("error" -> "User already exist!")))
                )
              case None =>
                val newPerson = Person(
                  0,
                  data.name.trim,
                  data.email,
                  BCrypt.hashpw(data.password.trim, BCrypt.gensalt())
                )
                personService
                  .addPerson(newPerson)
                  .map(res =>
                    Ok(Json.toJson(Map("message" -> "Successfully signed up!")))
                  )

            }

          } else {
            Future { BadRequest(Json.toJson(Map("error" -> "Invalid input"))) }
          }

        }
      )
  }

  def list() = Action.async { implicit request =>
    personService.listAllPersons.map { res =>
      Ok(Json.toJson(res))
    }
  }

  def read(id: Long) = Action.async { implicit request =>
    personService.getPerson(id).map {
      case Some(person) => Ok(Json.toJson(person))
      case None         => NotFound
    }
  }

  def update(id: Long) = Action.async { implicit request =>
    personService.updatePerson(id) map { res =>
      Redirect(routes.PersonController.index)
    }
  }

  def remove(id: Long) = Action.async { implicit request =>
    personService.deletePerson(id) map { res =>
      Ok("User deleted!!!")
    }
  }

}

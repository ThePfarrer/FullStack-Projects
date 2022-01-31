package models

import play.api.libs.json._
import java.time._
import java.time.format._
import slick.jdbc.PostgresProfile.api._
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future}

case class Person(
    id: Long,
    name: String,
    email: String,
    password: String,
    created: String =
      OffsetDateTime.now.format(DateTimeFormatter.ISO_DATE_TIME),
    updated: String = OffsetDateTime.now.format(DateTimeFormatter.ISO_DATE_TIME)
)

case class PersonList(
    id: Long,
    name: String,
    email: String,
    created: String,
    updated: String
)

object Person {
  implicit val PersonFormat = Json.format[Person]
}

object PersonList {
  implicit val PersonListFormat = Json.format[PersonList]
}

class PersonTableDef(tag: Tag) extends Table[Person](tag, "person") {

  def id = column[Long]("id", O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email", O.PrimaryKey)
  def password = column[String]("password")
  def created = column[String]("created")
  def updated = column[String]("updated")

  override def * =
    (
      id,
      name,
      email,
      password,
      created,
      updated
    ) <> ((Person.apply _).tupled, Person.unapply)
}

class Persons @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  val persons = TableQuery[PersonTableDef]

  def add(person: Person): Future[String] = {
    dbConfig.db
      .run(persons += person)
      .map(res => "person successfully added")
      .recover { case ex: Exception =>
        ex.getCause.getMessage
      }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(persons.filter(_.id === id).delete)
  }

  def update(id: Long, person: Person): Future[Option[Person]] = {
    dbConfig.db.run(
      persons
        .filter(_.id === id)
        .map(p => (p.name, p.email, p.password, p.updated))
        .update(
          (
            person.name,
            person.email,
            BCrypt.hashpw(person.password, BCrypt.gensalt()),
            OffsetDateTime.now.format(DateTimeFormatter.ISO_DATE_TIME)
          )
        )
    )
    get(id)
  }

  def get(id: Long): Future[Option[Person]] = {
    dbConfig.db.run(persons.filter(_.id === id).result.headOption)
  }

  def get(email: String): Future[Option[Person]] = {
    dbConfig.db.run(persons.filter(_.email === email).result.headOption)
  }

  def listAll: Future[Seq[PersonList]] = {
    dbConfig.db
      .run(persons.result)
      .map(items =>
        items.map(item =>
          PersonList(item.id, item.name, item.email, item.created, item.updated)
        )
      )
  }

}

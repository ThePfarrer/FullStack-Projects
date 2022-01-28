package services

import com.google.inject.Inject
import models.{Person, Persons, PersonList}

import scala.concurrent.Future

class PersonService @Inject() (persons: Persons) {

  def addPerson(person: Person): Future[String] = {
    persons.add(person)
  }

  def deletePerson(id: Long): Future[Int] = {
    persons.delete(id)
  }

  def getPerson(email: String): Future[Option[Person]] = {
    persons.get(email)
  }

  def updatePerson(id: Long): Future[Int] = {
    persons.update(id)
  }

  def listAllPersons: Future[Seq[PersonList]] = {
    persons.listAll
  }
}

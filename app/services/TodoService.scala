package services

import domain.Todo

import scala.concurrent.Future

trait TodoService {
  def getAll: Future[Seq[Todo]]
  def get(id: String): Future[Option[Todo]]
}

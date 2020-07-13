package services

import domain.{Todo, TodoContent}

import scala.concurrent.Future

trait TodoService {
  def create(todo: TodoContent): Future[Todo]

  def getAll: Future[Seq[Todo]]

  def get(id: String): Future[Option[Todo]]

  def update(todo: Todo): Future[Int]

  def delete(id: String): Future[Int]
}

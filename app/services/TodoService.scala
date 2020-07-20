package services

import domain.{TodoBody, Todo}
import domain.api.request.TodoRequest

import scala.concurrent.Future

trait TodoService {
  def create(createTodo: TodoBody): Future[Todo]

  def getAll: Future[Seq[Todo]]

  def get(id: String): Future[Option[Todo]]

  def update(id: String, createTodo: TodoBody): Future[Int]

  def delete(id: String): Future[Int]
}

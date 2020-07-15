package services

import domain.{CreateTodo, Todo}
import domain.api.request.TodoRequest

import scala.concurrent.Future

trait TodoService {
  def create(createTodo: CreateTodo): Future[Todo]

  def getAll: Future[Seq[Todo]]

  def get(id: String): Future[Option[Todo]]

  def update(id: String, createTodo: CreateTodo): Future[Int]

  def delete(id: String): Future[Int]
}

package services.impl

import com.google.inject.Inject
import domain.{TodoBody, Todo}
import domain.api.request.TodoRequest
import repositories.TodoRepository
import services.TodoService
import shared.Contexts

import scala.concurrent.Future

class TodoServiceImpl @Inject() (todoRepository: TodoRepository) extends TodoService {
  override def create(createTodo: TodoBody): Future[Todo] = Future(todoRepository.create(createTodo))(Contexts.blocking)

  override def getAll: Future[Seq[Todo]] = Future(todoRepository.getAll)(Contexts.blocking)

  override def get(id: String): Future[Option[Todo]] = Future(todoRepository.get(id))(Contexts.blocking)

  override def update(id: String, createTodo: TodoBody): Future[Int] = Future(todoRepository.update(id, createTodo))(Contexts.blocking)

  override def delete(id: String): Future[Int] = Future(todoRepository.delete(id))(Contexts.blocking)
}

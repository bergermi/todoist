package services.impl

import com.google.inject.Inject
import domain.{Todo, TodoContent}
import repositories.TodoRepository
import services.TodoService
import shared.Contexts

import scala.concurrent.Future

class TodoServiceImpl @Inject() (todoRepository: TodoRepository) extends TodoService {
  override def create(todoContent: TodoContent): Future[Todo] = Future(todoRepository.create(todoContent))(Contexts.blocking)

  override def getAll: Future[Seq[Todo]] = Future(todoRepository.getAll)(Contexts.blocking)

  override def get(id: String): Future[Option[Todo]] = Future(todoRepository.get(id))(Contexts.blocking) // TODO context?

  override def update(todo: Todo): Future[Int] = Future(todoRepository.update(todo: Todo))(Contexts.blocking)

  override def delete(id: String): Future[Int] = Future(todoRepository.delete(id))(Contexts.blocking)
}

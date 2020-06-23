package services.impl

import com.google.inject.Inject
import domain.Todo
import repositories.TodoRepository
import services.TodoService
import shared.Contexts

import scala.concurrent.Future

class TodoServiceImpl @Inject() (todoRepository: TodoRepository) extends TodoService {
  override def getAll: Future[Seq[Todo]] = Future(todoRepository.getAll)(Contexts.blocking)
}

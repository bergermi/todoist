package controllers

import com.google.inject.Inject
import domain.api.response.{TodoResponse, TodosResponse}
import javax.inject.Singleton
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.TodoService

import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

@Singleton
class TodoController @Inject() (
    cc: ControllerComponents,
    todoService: TodoService
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def getTodos: Action[AnyContent] = Action.async { implicit request =>
    todoService.getAll map { todos =>
      Ok(Json.toJson(TodosResponse(todos = todos)))
    } recover {
      case NonFatal(e) =>
        logger.error("An error occurred while fetching todos", e)
        InternalServerError(e.getMessage)
    }
  }

  def getTodo(id: String): Action[AnyContent] = Action.async { implicit request =>
    todoService.get(id) map { todo =>
      Ok(Json.toJson(TodoResponse(todo = todo)))
    } recover {
      case NonFatal(e) =>
      logger.error(s"An error occurred while fetching todo with id = '%s'", e)
      InternalServerError(e.getMessage)
    }
  }
}

package controllers

import com.google.inject.Inject
import domain.api.response.TodosResponse
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
        logger.error("An error occured while fetching todos", e)
        InternalServerError(e.getMessage)
    }
  }
}

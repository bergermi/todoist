package controllers

import com.google.inject.Inject
import domain.{Todo, TodoContent}
import domain.api.response.{TodoResponse, TodosResponse}
import javax.inject.Singleton
import play.api.Logging
import play.api.libs.json
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.TodoService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

@Singleton
class TodoController @Inject() (cc: ControllerComponents, todoService: TodoService)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def createTodo() = Action.async(parse.json) { implicit request =>
    request.body
      .validate[TodoContent]
      .fold(
        errors => Future(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
        todoContent => {
          todoService.create(todoContent) map { todo =>
            Ok(Json.toJson(TodoResponse(todo = Some(todo))))
          } recover {
            case NonFatal(e) =>
              logger.error("An error occurred while creating todo", e)
              InternalServerError(e.getMessage)
          }
        }
      )
  }

  def getTodos: Action[AnyContent] = Action.async { implicit request =>
    todoService.getAll map { todos =>
      Ok(Json.toJson(TodosResponse(todos = todos))) // TODO explanation?
    } recover {
      case NonFatal(e) =>
        logger.error("An error occurred while fetching todos", e)
        InternalServerError(e.getMessage)
    }
  }

  def getTodo(id: String): Action[AnyContent] = Action.async { implicit request =>
    todoService.get(id) map { todo =>            // TODO what does map exactly do here?
      Ok(Json.toJson(TodoResponse(todo = todo))) // TODO explanation?
    } recover {                                  // TODO recover?
      case NonFatal(e) =>
        logger.error(s"An error occurred while fetching todo with id = '$id'", e)
        InternalServerError(e.getMessage)
    }
  }

  def updateTodo(): Action[AnyContent] = Action.async { implicit request =>
    val json       = request.body.asJson
    val todo: Todo = json.get("todo").as[Todo]
    todoService.update(todo) map { i =>
      if (i > 0) Ok
      else NotFound
    } recover {
      case NonFatal(e) =>
        logger.error(s"An error occurred while updating todo with id = '${todo.id}'", e)
        InternalServerError(e.getMessage)
    }
  }

  // TODO why do I get "You must be authenticated to access this page." in Swagger from "/docs"?
  def deleteTodo(id: String): Action[AnyContent] = Action.async { implicit request =>
    todoService.delete(id) map { i => // TODO correct and naming? rowsAffected und eine Schicht tiefer im Service.
      if (i > 0) Ok                   // TODO why without ok()?
      else NotFound
    } recover {
      case NonFatal(e) =>
        logger.error(s"An error occurred while deleting todo with id = '$id'", e)
        InternalServerError(e.getMessage)
    }
  }

}

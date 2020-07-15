package controllers

import com.google.inject.Inject
import domain.{CreateTodo, Todo}
import domain.api.request.TodoRequest
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

  def createTodo(): Action[AnyContent] = Action.async { implicit request =>
    val json = request.body.asJson

    json match {
      case Some(value) =>
        value
          .validate[CreateTodo]
          .fold(
            errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
            createTodo => {
              todoService.create(createTodo) map { todo =>
                Ok(Json.toJson(TodoResponse(todo = Some(todo))))
              } recover {
                case NonFatal(e) =>
                  logger.error("An error occurred while creating todo", e)
                  InternalServerError(e.getMessage)
              }
            }
          )
      case None => Future.successful(BadRequest("Invalid JSON format"))
    }
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

  def updateTodo(id: String): Action[AnyContent] = Action.async { implicit request =>
    val json = request.body.asJson

    json match {
      case Some(value) =>
        value
          .validate[CreateTodo]
          .fold(
            errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
            createTodo => {
              todoService.update(id: String, createTodo) map { rowsAffected =>
                if (rowsAffected > 0) Ok
                else NotFound
              } recover {
                case NonFatal(e) =>
                  logger.error(s"An error occurred while updating todo with id = '${id}'", e)
                  InternalServerError(e.getMessage)
              }
            }
          )
      case None => Future.successful(BadRequest("Invalid JSON format"))
    }
  }

  def deleteTodo(id: String): Action[AnyContent] = Action.async { implicit request =>
    todoService.delete(id) map { rowsAffected =>
      if (rowsAffected > 0) Ok
      else NotFound
    } recover {
      case NonFatal(e) =>
        logger.error(s"An error occurred while deleting todo with id = '$id'", e)
        InternalServerError(e.getMessage)
    }
  }

}

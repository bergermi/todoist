package domain.api.request

import domain.TodoBody
import play.api.libs.json.{Json, OFormat}

case class TodoRequest(createTodo: TodoBody)

object TodoRequest {
  implicit val format: OFormat[TodoRequest] = Json.format[TodoRequest]
}

package domain.api.request

import domain.CreateTodo
import play.api.libs.json.{Json, OFormat}

case class TodoRequest(createTodo: CreateTodo)

object TodoRequest {
  implicit val format: OFormat[TodoRequest] = Json.format[TodoRequest]
}

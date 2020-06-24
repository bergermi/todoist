package domain.api.response

import domain.Todo
import play.api.libs.json.{Json, OFormat}

case class TodoResponse(todo: Option[Todo] = None)

object TodoResponse {
  implicit val format: OFormat[TodoResponse] = Json.format[TodoResponse]
}

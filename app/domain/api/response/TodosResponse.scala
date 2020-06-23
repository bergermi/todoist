package domain.api.response

import domain.Todo
import play.api.libs.json.{Json, OFormat}

case class TodosResponse(todos: Seq[Todo] = Seq.empty)

object TodosResponse {
  implicit val format: OFormat[TodosResponse] = Json.format[TodosResponse]
}

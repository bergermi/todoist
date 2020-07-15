package domain

import play.api.libs.json.{Json, OFormat}

case class CreateTodo(description: String)

object CreateTodo {
  implicit val format: OFormat[CreateTodo] = Json.format[CreateTodo]

}

package domain

import java.time.Instant

import play.api.libs.json.{Json, OFormat}

case class Todo(id: String, description: String, createdAt: Option[Instant] = None)

object Todo {
  implicit val format: OFormat[Todo] = Json.format[Todo]
}

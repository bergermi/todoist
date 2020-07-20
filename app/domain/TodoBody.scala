package domain

import play.api.libs.json.{Json, OFormat}

case class TodoBody(description: String)

object TodoBody {
  implicit val format: OFormat[TodoBody] = Json.format[TodoBody]

}

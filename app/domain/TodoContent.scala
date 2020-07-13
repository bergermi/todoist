package domain

import play.api.libs.json.{JsPath, Reads}

case class TodoContent(description: String = "")

object TodoContent {

  implicit def todoContentReads: Reads[TodoContent] =
    (JsPath \ "description").read[String].map(TodoContent(_))
}

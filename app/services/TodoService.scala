package services

import domain.Todo

import scala.concurrent.Future

trait TodoService {
  def getAll: Future[Seq[Todo]]
}

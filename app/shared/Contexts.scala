package shared

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

object Contexts {
  implicit val system: ActorSystem        = ActorSystem()
  implicit val blocking: ExecutionContext = system.dispatchers.lookup("blocking-context")
}

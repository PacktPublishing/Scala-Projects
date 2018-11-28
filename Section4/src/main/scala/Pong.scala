import akka.actor.Actor

class Pong extends Actor {
  override def receive: Receive = {
    case PingMessage =>
      println(" pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
      context.system terminate()
  }
}

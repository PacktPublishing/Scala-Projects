import akka.actor.{Actor, ActorRef}

class Ping(pong: ActorRef) extends Actor{
  var count = 0
  def incrementAndPrint = {
    count+=1
    println(" Ping")
  }
  override def receive: Receive = {
    case StartMessage =>
      println("StartMessage Received")
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      incrementAndPrint
      if(count > 99) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        sender ! PingMessage
      }
  }
}

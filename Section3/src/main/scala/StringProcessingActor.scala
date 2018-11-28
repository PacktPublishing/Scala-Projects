import akka.actor.Actor

class StringProcessingActor extends Actor {
  override def receive: Receive = {
    case ProcessStringMsg(string) => {
      val wordsInLine = string.split(" ").length
      sender ! StringProcessedMsg(wordsInLine)
    }
    case _ => println("ERROR: message not recognised")
  }
}

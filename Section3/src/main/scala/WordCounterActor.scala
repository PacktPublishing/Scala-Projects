import akka.actor.{Actor, ActorRef, Props}
import scala.io.Source._

class WordCounterActor(filename: String) extends Actor {
  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None
  override def receive: Receive = {
    case StartProcessFileMsg() =>
      if(running) {
        println("Warning: duplicate start message received")
      } else {
        running = true
        fileSender = Some(sender)
        fromFile(filename).getLines.foreach{
          line =>
            println(line)
            context.actorOf(Props[StringProcessingActor]) ! ProcessStringMsg(line)
            totalLines+=1
        }
      }

    case StringProcessedMsg(words) =>
      result+=words
      linesProcessed+=1
      if(linesProcessed == totalLines) {
        fileSender.get ! result
      }
    case _ => println("message not recognised")
  }
}

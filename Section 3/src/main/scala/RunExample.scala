import akka.actor.{ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._
import akka.pattern.ask

import scala.concurrent.Await

object RunExample extends App {
  val system = ActorSystem("StringProcessing")
  val actor = system.actorOf(Props(new WordCounterActor(args(0))))
  implicit val timeout = Timeout(25 seconds)
  val future = actor ? StartProcessFileMsg()
  val result = Await.result(future, timeout.duration).asInstanceOf[Int]
  println(result)
  system terminate
}

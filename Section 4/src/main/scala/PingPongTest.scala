import akka.actor.{ActorSystem, Props}

object PingPongTest extends App{
 val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")

  //Let's start them going
  ping ! StartMessage
}

import akka.actor.{Actor, ActorRef}

class Fork extends Actor {
  import context._
  def receive: Receive = available

  def takenBy(philosopher: ActorRef): Receive = {
    case Take(otherPhilosopher) =>
      otherPhilosopher ! Busy(self)
    case Put(`philosopher`) =>
      become(available)
  }

  def available: Receive = {
    case Take(philosopher) =>
      become(takenBy(philosopher))
      philosopher ! Taken(self)
  }
}

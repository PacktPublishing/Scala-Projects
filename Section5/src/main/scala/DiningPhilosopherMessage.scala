import akka.actor.ActorRef

sealed trait DiningPhilosopherMessage

final case class Busy(fork: ActorRef) extends DiningPhilosopherMessage

final case class Taken(fork: ActorRef) extends DiningPhilosopherMessage

final case class Put(philosopher: ActorRef) extends DiningPhilosopherMessage

final case class Take(philosopher: ActorRef) extends DiningPhilosopherMessage

object Eat extends DiningPhilosopherMessage

object Think extends DiningPhilosopherMessage

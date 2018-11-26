import akka.actor.{Actor, ActorRef, _}

import scala.concurrent.duration._

class Philosopher(name: String, left: ActorRef, right: ActorRef) extends Actor {
  import context._
  override def receive: Receive = {
    case Think =>
      println("%s starts to think".format(name))
      startThinking(0.5 seconds)
  }

  def hungry: Receive = {
    case Taken(`left`) => become(waiting_For(right, left))
    case Taken(`right`) => become(waiting_For(left, right))
    case Busy(fork) => become(denied_a_fork)
  }

  def waiting_For(forkToWaitFor: ActorRef, otherFork: ActorRef): Receive = {
    case Taken(`forkToWaitFor`) => println("%s has picked up %s and %s and starts to eat".format(name, left.path.name, right.path.name))
      become(eating)
      system.scheduler.scheduleOnce(0.5 seconds, self, Think)
    case Busy(fork) => otherFork ! Put(self)
      startThinking(10 milliseconds)
  }

  def denied_a_fork: Receive = {
    case Taken(fork) =>
      fork ! Put(self)
      startThinking(10 milliseconds)
    case Busy(fork) =>
      startThinking(10 milliseconds)
  }

  def thinking: Receive = {
    case Eat =>
      become(hungry)
      left ! Take(self)
      right ! Take(self)
  }

  def eating: Receive = {
    case Think =>
      left ! Put(self)
      right ! Put(self)
      println("%s puts down his fork and starts to think".format(name))
      startThinking(0.5 seconds)
  }

  private def startThinking(duration: FiniteDuration) = {
    become(thinking)
    system.scheduler.scheduleOnce(duration, self, Eat)
  }
}
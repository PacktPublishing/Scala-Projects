import akka.actor._

object DiningPhilosopherInHumus {
  val system = ActorSystem()

  def main(args: Array[String]): Unit = run()

  def run(): Unit = {
    //create 5 forks
    val forks = for (i<- 1 to 5) yield system.actorOf(Props[Fork], "Fork"+i)

    val philosophers = for {
      (name, i) <- List("A", "B", "C", "D", "E").zipWithIndex
    } yield {
      system.actorOf(Props(classOf[Philosopher], name, forks(i), forks((i+1)%5)))
    }

    philosophers.foreach(_ ! Think)
  }
}

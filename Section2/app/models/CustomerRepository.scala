package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class CustomerRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class CustomerTable(tag: Tag) extends Table[Customer](tag, "customer") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def age = column[Int]("age")

    def * = (id,name,age)<>((Customer.apply _).tupled, Customer.unapply)
  }

  private val customer = TableQuery[CustomerTable]

  def insert(name: String, age: Int): Future[Customer] = db.run {
    (customer.map(c => (c.name, c.age))
      returning customer.map(_.id)
      into((nameAge, id) => Customer(id, nameAge._1, nameAge._2))
      )+=(name,age)
  }

  def list(): Future[Seq[Customer]]= db.run{
    customer.result
  }
}

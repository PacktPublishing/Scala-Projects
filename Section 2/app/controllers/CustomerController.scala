package controllers

import javax.inject._
import models.CustomerRepository
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class CustomerController @Inject()(repo: CustomerRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  val customerForm: Form[CustomerForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "age" -> number.verifying(min(1), max(100))
    )(CustomerForm.apply)(CustomerForm.unapply)
  }

  def index = Action { implicit request =>
    Ok(views.html.index(customerForm))
  }

  def addCustomerData = Action.async { implicit request =>
    customerForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },
      successForm => {
        repo.insert(successForm.name, successForm.age).map { _ =>
          Redirect(routes.CustomerController.index).flashing("success" -> "user.created")
        }
      }
    )
  }

  def getCustomerData = Action.async { implicit request =>
    repo.list.map { seqOfCustomer =>
      Ok(views.html.customerdata(seqOfCustomer))
    }
  }
}

case class CustomerForm(name: String, age: Int)

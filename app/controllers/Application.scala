package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current

object Application extends Controller {
  
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def projects = Action { implicit request =>
    Ok(views.html.projects())
  }

  def contact = Action { implicit request =>
    Ok(views.html.contact())
  }

  val sendForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "email" -> email,
      "message" -> nonEmptyText
    )
  )

  def sendMessage = Action { implicit request =>
    sendForm.bindFromRequest.fold(
      formWithErrors => BadRequest(formWithErrors.errorsAsJson),
      value => value match {
        case (name: String, email: String, message: String) => {
          current.configuration.getString("mailer.recipient") match {
            case Some(recipient) => { 
              import com.typesafe.plugin._
              val mail = use[MailerPlugin].email

              mail.setSubject("Message from " + name +" <"+ email +">")
              mail.addRecipient(recipient)
              mail.addFrom(name +" <"+ email +">")
              mail.send(message)

              Ok
            }
            case _ => throw new RuntimeException("Mailer recipient is not properly configured in application.conf")
          }
        }
      }
    )
  }
}

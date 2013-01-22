package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

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
      value => Ok
    ) 
  }
}

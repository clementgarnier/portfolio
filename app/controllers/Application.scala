package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }

  def projects = Action {
    Ok(views.html.projects())
  }

  def contact = Action {
    Ok(views.html.contact())
  }
  
}

package controllers

import akka.cluster.Cluster
import play.api.Play.current
import play.api.mvc.{Action, Controller, WebSocket}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

object WeatherController extends Controller {

  def index = Action {
    Redirect("/assets/index.html")
  }

  def stations = Action {
    println("Stations")
    Ok(
      """
        [  {
          "name": "BOGUS NORWAY",
          "id": "408930:99999",
          "callSign":"OKIU",
          "countryCode":"GB"}

          ]
      """.stripMargin)
  }

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    WebSocketActor.props(out)
  }

  import akka.actor._

  object WebSocketActor {
    def props(out: ActorRef) = Props(new WebSocketActor(out))
  }

  class WebSocketActor(out: ActorRef) extends Actor {

    val guardian = context.actorSelection(Cluster(context.system).selfAddress.copy(port = Some(2550)) + "/user/node-guardian")
    guardian ! "hello"

    context.system.scheduler.schedule(1 seconds, 1 seconds, self, ScheduledMsg("send me baby"))
    var count = 0

    def receive = {
      case msg: String =>
        println(s"Received message $msg")
        out ! msg
      case ScheduledMsg(msg) =>
        val s: String = s""" { "event":"pong", "data":"$msg $count" } """
        count = count + 1
        println(s"sending $s")
        out ! s
    }


    override def postStop() = {
      println("Web socket closed")
    }
  }

  case class ScheduledMsg(msg: String)

}

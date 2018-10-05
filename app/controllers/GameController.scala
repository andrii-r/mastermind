package controllers

import play.api.mvc._
import play.api.libs.streams.ActorFlow
import javax.inject.Inject
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Attributes.Name
import akka.stream.Materializer
import clients.actors.WebSocketClientActor
import com.google.inject.{Singleton}

@Singleton
class GameController @Inject()(@Name("pimp-actor") pimpActor: ActorRef, cc: ControllerComponents)
                              (implicit system: ActorSystem, mat: Materializer) extends InjectedController() {

  def socket: WebSocket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      WebSocketClientActor.props(pimpActor, out)
    }
  }
}

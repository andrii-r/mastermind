package controllers
import akka.actor._
import play.api.mvc._
import play.api.libs.streams.ActorFlow
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Singleton

@Singleton
class GameController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends InjectedController() {

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      WebSocketClientActor.props(out)
    }
  }
}

object WebSocketClientActor {
  def props(out: ActorRef) = Props(new WebSocketClientActor(out))
}

class WebSocketClientActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}
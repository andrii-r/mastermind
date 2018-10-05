package clients.actors

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json.{Json, OFormat}

object WebSocketClientActor {
  def props(pimpActor: ActorRef, out: ActorRef) = Props(new WebSocketClientActor(pimpActor, out))
}

class WebSocketClientActor (pimpActor: ActorRef, out: ActorRef) extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    case wsa: WsAttachClient =>
      pimpActor ! AttachClient(wsa.playerName)
    case msg: String =>
      out ! ("I received your message, but don't know what to do: " + msg)
  }
}

sealed trait WsInbound {
  val name: String
}

case class WsAttachClient(playerName: String, name: String = "attach")
object WsAttachClient {
  implicit val format: OFormat[WsAttachClient] = Json.format[WsAttachClient]
}

case class AttachClient(playerName: String)


case class ClientAttached()
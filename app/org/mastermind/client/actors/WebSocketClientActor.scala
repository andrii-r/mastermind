package org.mastermind.client.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.mastermind.web._

object WebSocketClientActor {
  def props(pimpActor: ActorRef, out: ActorRef, wsId: String) = Props(new WebSocketClientActor(pimpActor, out, wsId))
}

class WebSocketClientActor(pimpActor: ActorRef, out: ActorRef, wsId: String) extends Actor with ActorLogging {

  override def preStart(): Unit = {
    log.info(s"WebSocketClientActor started with $wsId")
  }

  var gameActor: Option[ActorRef] = None

  def receive: PartialFunction[Any, Unit] = {
    case wsa: WsAttach =>
      pimpActor ! AttachClient(wsa.playerName)
    case wsa: WsRequestGame =>
      pimpActor ! RequestGame(wsa.playerName)

    case ca: ClientAttached =>
      out ! WsAttached(ca.playerName)
    case wfp: WaitingForPartner =>
      out ! WsWaitingForPartner(wfp.playerName)
    case gs: GameStarted =>
      gameActor = Some(sender())
      out ! WsGameStarted(gs.playerName)
    case msg: Any =>
      out ! s"I received your message, but don't know what to do: $msg"
  }
}

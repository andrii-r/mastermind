package org.mastermind.client.actors

import akka.actor.{Actor, ActorRef, Props}
import org.mastermind.web._

object WebSocketClientActor {
  def props(pimpActor: ActorRef, out: ActorRef) = Props(new WebSocketClientActor(pimpActor, out))
}

class WebSocketClientActor(pimpActor: ActorRef, out: ActorRef) extends Actor {

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
    case msg: String =>
      out ! ("I received your message, but don't know what to do: " + msg)
  }
}

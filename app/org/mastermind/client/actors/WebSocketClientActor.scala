package org.mastermind.client.actors

import akka.actor.{Actor, ActorRef, Props}
import org.mastermind.web.{AttachClient, ClientAttached, WsAttach, WsAttached}

object WebSocketClientActor {
  def props(pimpActor: ActorRef, out: ActorRef) = Props(new WebSocketClientActor(pimpActor, out))
}

class WebSocketClientActor(pimpActor: ActorRef, out: ActorRef) extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    case wsa: WsAttach =>
      pimpActor ! AttachClient(wsa.playerName)
    case ca: ClientAttached =>
      out ! WsAttached(ca.playerName)
    case msg: String =>
      out ! ("I received your message, but don't know what to do: " + msg)
  }
}

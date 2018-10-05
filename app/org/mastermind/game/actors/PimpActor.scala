package org.mastermind.game.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import org.mastermind.client.actors.{AttachClient, ClientAttached}

class PimpActor extends Actor with ActorLogging {

  private var clientPairs: List[(ActorRef, ActorRef)] = List.empty
  private var awaytingClient: Option[ActorRef] = None

  override def receive: Receive = {
    case ac: AttachClient =>
      if(awaytingClient.isEmpty) {
        awaytingClient = Some(sender())
      }

      sender() ! ClientAttached()
    case _ =>
  }
}
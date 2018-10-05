package game.actor

import akka.actor.{Actor, ActorLogging, ActorRef}
import clients.actors.{AttachClient, ClientAttached}

class PimpActor extends Actor with ActorLogging {

  private var clientPairs: List[(ActorRef, ActorRef)] = List.empty
  private var awaytingClient: Option[ActorRef] = _

  override def receive: Receive = {
    case ac: AttachClient =>
      if(awaytingClient.isEmpty) {
        awaytingClient = Some(sender())
      }

      sender() ! ClientAttached()
    case _ =>
  }
}
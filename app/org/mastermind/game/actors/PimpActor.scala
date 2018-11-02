package org.mastermind.game.actors

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.mastermind.web._

object PimpActor {

}

class PimpActor extends Actor with ActorLogging {

  import GameActor._

  private var clientPairs: List[(ActorRef, ActorRef)] = List.empty
  private var awaytingClient: Option[ActorRef] = None

  override def receive: Receive = {
    case ac: AttachClient =>
      sender() ! ClientAttached(ac.playerName)
    case rg: RequestGame =>
      if (awaytingClient.isEmpty) {
        awaytingClient = Some(sender())
        sender() ! WaitingForPartner(rg.playerName)
      } else {
        val pair = (sender(), awaytingClient.get)
        clientPairs = pair :: clientPairs
        awaytingClient = None

        val gameActor: ActorRef = context.actorOf(Props[GameActor], "game-actor-" + UUID.randomUUID().toString)
        gameActor ! StartGame(pair)
      }


    case _ =>
  }
}


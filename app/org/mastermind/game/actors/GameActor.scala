package org.mastermind.game.actors

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import org.mastermind.game.actors.GameActor.StartGame
import org.mastermind.web.GameStarted

object GameActor {

  case class StartGame(pair: (ActorRef, ActorRef))


}

class GameActor extends Actor with ActorLogging {

  var firstPlayer: ActorRef = _
  var secondPlayer: ActorRef = _


  override def receive: Receive = {

    case StartGame((first, second)) =>
      firstPlayer = first
      secondPlayer = second

      firstPlayer ! GameStarted
      secondPlayer ! GameStarted

    case _ =>
  }
}

package org.mastermind.web

import play.api.libs.json._


object WsInbound {
  val w: OWrites[WsInbound] = {
    case c: WsAttach => WsAttach.format.writes(c)
    case c: WsRequestGame => WsRequestGame.format.writes(c)
  }
  val r: Reads[WsInbound] = (json: JsValue) => {
    (json \ "name").as[String] match {
      case "attach" => WsAttach.format.reads(json)
      case "request-game" => WsRequestGame.format.reads(json)
      case _ => {
        WsRequestGame.format.reads(json)
      }
    }
  }
  implicit val format: OFormat[WsInbound] = OFormat(r, w)
}

sealed trait WsInbound {
  val name: String
}

object WsOutbound {
  val w: OWrites[WsOutbound] = {
    case wso: WsAttached => WsAttached.format.writes(wso)
    case wso: WsWaitingForPartner => WsWaitingForPartner.format.writes(wso)
    case wso: WsGameStarted => WsGameStarted.format.writes(wso)
  }

  val r: Reads[WsOutbound] = (json: JsValue) => {
    (json \ "name").as[String] match {
      case "attached" => WsAttached.format.reads(json)
      case "waiting-for-partner" => WsWaitingForPartner.format.reads(json)
      case "ws-game-started" => WsGameStarted.format.reads(json)
    }
  }
  implicit val format: OFormat[WsOutbound] = OFormat(r, w)
}

sealed trait WsOutbound {
  val name: String
}

case class WsAttach(playerName: String, name: String = "attach") extends WsInbound

case class WsRequestGame(playerName: String, name: String = "request-game") extends WsInbound


case class WsAttached(playerName: String, name: String = "attached") extends WsOutbound

case class WsWaitingForPartner(playerName: String, name: String = "waiting-for-partner") extends WsOutbound

case class WsGameStarted(playerName: String, name: String = "ws-game-started") extends WsOutbound



object WsAttach {
  implicit val format: OFormat[WsAttach] = Json.format[WsAttach]
}

object WsRequestGame {
  implicit val format: OFormat[WsRequestGame] = Json.format[WsRequestGame]
}




object WsAttached {
  implicit val format: OFormat[WsAttached] = Json.format[WsAttached]
}

object WsWaitingForPartner {
  implicit val format: OFormat[WsWaitingForPartner] = Json.format[WsWaitingForPartner]
}

object WsGameStarted {
  implicit val format: OFormat[WsGameStarted] = Json.format[WsGameStarted]
}

case class AttachClient(playerName: String)

case class RequestGame(playerName: String)


case class ClientAttached(playerName: String)

case class WaitingForPartner(playerName: String)

case class GameStarted(playerName: String)
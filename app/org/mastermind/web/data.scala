package org.mastermind.web

import play.api.libs.json._


object WsInbound {
  val w: OWrites[WsInbound] = {
    case c: WsAttach => WsAttach.format.writes(c)
  }
  val r: Reads[WsInbound] = (json: JsValue) => {
    (json \ "name").as[String] match {
      case "attach" => WsAttach.format.reads(json)
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
  }

  val r: Reads[WsOutbound] = (json: JsValue) => {
    (json \ "name").as[String] match {
      case "attached" => WsAttached.format.reads(json)
    }
  }
  implicit val format: OFormat[WsOutbound] = OFormat(r, w)
}

sealed trait WsOutbound {
  val name: String
}

case class WsAttach(playerName: String, name: String = "attach") extends WsInbound

case class WsAttached(playerName: String, name: String = "attached") extends WsOutbound

object WsAttach {
  implicit val format: OFormat[WsAttach] = Json.format[WsAttach]
}

object WsAttached {
  implicit val format: OFormat[WsAttached] = Json.format[WsAttached]
}

case class AttachClient(playerName: String)

case class ClientAttached(playerName: String)
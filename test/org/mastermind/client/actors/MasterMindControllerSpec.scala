package org.mastermind.client.actors

import org.mastermind.web.WsAttach
import org.scalatestplus.play.PlaySpec

class MasterMindControllerSpec extends PlaySpec with WsSpec {

  "MasterMindController" should {
    "attach" in {
      sendMessage(WsAttach("jessika"))
      receiveMessage.name mustBe "attached"
    }
  }
}

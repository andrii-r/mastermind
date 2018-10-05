package clients.actors

import org.scalatestplus.play.PlaySpec

class MasterMindControllerSpec extends PlaySpec with WsSpec {

  "MasterMindController" should {
    "attach" in {
      sendMessage(WsAttach("jessika"))
      receiveMessage.name mustBe "attached"
    }
  }
}

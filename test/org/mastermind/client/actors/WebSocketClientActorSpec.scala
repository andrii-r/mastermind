package org.mastermind.client.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.mastermind.web.{AttachClient, ClientAttached, WsAttach, WsAttached}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import org.scalatest.mockito.MockitoSugar

class WebSocketClientActorSpec extends TestKit(ActorSystem("MasterMindActorTest")) with FlatSpecLike
  with Matchers
  with BeforeAndAfterAll
  with ImplicitSender
  with MockitoSugar {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val pimpActor = TestProbe("pimpActor")
  val out = TestProbe("out")

  "WebSocketClientActor" should "attach" in {
    val actor = system.actorOf(WebSocketClientActor.props(pimpActor.ref, out.ref))

    actor ! WsAttach("jessica")
    pimpActor.expectMsg(AttachClient("jessica"))
    pimpActor.reply(ClientAttached("jessica"))
    out.expectMsg(WsAttached("jessica"))
  }

}

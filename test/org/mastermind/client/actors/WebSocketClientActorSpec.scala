package org.mastermind.client.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
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
}

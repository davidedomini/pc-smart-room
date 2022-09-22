package it.unibo.pc.smart_room.decentralized.light

import io.vertx.core.{AbstractVerticle, Promise, Vertx}
import io.vertx.mqtt.MqttClient
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer

class LightAgent extends AbstractVerticle:

  val gui = LightGui()
  gui.display
  var lastL = 0.0
  var isPresent = false

  override def start(): Unit =
    val client: MqttClient = MqttClient.create(vertx)
    client.connect(
      1883,
      "broker.mqtt-dashboard.com",
      c =>
        println("Connected...")
        client
          .publishHandler(s =>
            s.payload().toString match {
              case "Enter" =>
                isPresent = true
                gui.setOn
              case "Exit" =>
                isPresent = false
                if lastL >= 0.2 then gui.setOff
              case msg: String if msg.contains("Luminosity") =>
                val l = msg.split(" ")(1).toDouble
                lastL = l
                if l < 0.2 then gui.setOn
                else if isPresent then gui.setOn
                else gui.setOff
              case _ =>
            }
          )
          .subscribe("light", 2)
    )

object TestLighAgent extends App:
  val vertx: Vertx = Vertx.vertx
  val agent = new LightAgent
  vertx.deployVerticle(agent)

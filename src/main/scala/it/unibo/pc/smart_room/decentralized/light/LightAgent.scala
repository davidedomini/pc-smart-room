package it.unibo.pc.smart_room.decentralized.light

import io.vertx.core.{AbstractVerticle, Promise, Vertx}
import io.vertx.mqtt.MqttClient
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer

class LightAgent extends AbstractVerticle:

  override def start(): Unit =
    val client: MqttClient = MqttClient.create(vertx)
    client.connect(
      1883,
      "broker.mqtt-dashboard.com",
      c =>
        println("Connected...")
        client
          .publishHandler(s =>
            println(s"There is a new message in the topic ${s.topicName}")
            println(s"Content of the message: ${s.payload().toString()}")
            println(s"QoS: ${s.qosLevel}")
          )
          .subscribe("light", 2)

      //client.publish("light", Buffer.buffer("hello"), MqttQoS.AT_LEAST_ONCE, false, false);
    )

object TestAgent extends App:
  val vertx: Vertx = Vertx.vertx
  val agent = new LightAgent
  vertx.deployVerticle(agent)

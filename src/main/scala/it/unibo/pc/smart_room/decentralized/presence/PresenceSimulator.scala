package it.unibo.pc.smart_room.decentralized.presence

import io.vertx.mqtt.MqttClient

import java.awt.FlowLayout
import javax.swing.{BoxLayout, JButton, JFrame, JLabel, JPanel, SwingUtilities}
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer
import io.vertx.core.Vertx

class PresenceSimulator extends JFrame:

  //mqtt connection
  val client: MqttClient = MqttClient.create(Vertx.vertx)
  client.connect(1883, "broker.mqtt-dashboard.com", c => println("Connected..."))

  //gui
  val presDetControlPanel: JPanel = new JPanel
  presDetControlPanel.setLayout(new BoxLayout(presDetControlPanel, BoxLayout.Y_AXIS))
  val presTitlePanel: JPanel = new JPanel
  presTitlePanel.setLayout(new FlowLayout())
  presTitlePanel.add(new JLabel("Presence Detection Control Panel"))
  presDetControlPanel.add(presTitlePanel)
  val buttonsPanel: JPanel = new JPanel
  buttonsPanel.setLayout(new FlowLayout())
  val enter = new JButton("Enter")
  val exit = new JButton("Exit")
  enter.setEnabled(true)
  exit.setEnabled(false)
  enter.addActionListener(e =>
    enter.setEnabled(false)
    exit.setEnabled(true)
    sendMsg("Enter")
  )
  exit.addActionListener(e =>
    enter.setEnabled(true)
    exit.setEnabled(false)
    sendMsg("Exit")
  )
  buttonsPanel.add(enter)
  buttonsPanel.add(exit)
  presDetControlPanel.add(buttonsPanel)
  setContentPane(presDetControlPanel)
  setBounds(600, 200, 300, 200)

  def display: Unit = SwingUtilities.invokeLater { () =>
    setVisible(true)
  }

  private def sendMsg(msg: String): Unit =
    client.publish("light", Buffer.buffer(msg), MqttQoS.AT_LEAST_ONCE, false, false);

object TestPresenceSimulator extends App:
  new PresenceSimulator().display

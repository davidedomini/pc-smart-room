package it.unibo.pc.smart_room.decentralized.luminosity

import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttClient
import java.awt.FlowLayout
import javax.swing.{BoxLayout, JFrame, JLabel, JPanel, JSlider, JTextField, SwingConstants, SwingUtilities}

class LuminositySimulator extends JFrame:

  //mqtt connection
  val client: MqttClient = MqttClient.create(Vertx.vertx)
  client.connect(1883, "broker.mqtt-dashboard.com", c => println("Connected..."))

  //gui
  val currentLumValue = 0
  val lumControlPanel: JPanel = new JPanel
  lumControlPanel.setLayout(new BoxLayout(lumControlPanel, BoxLayout.Y_AXIS))
  val lumTitlePanel: JPanel = new JPanel
  lumTitlePanel.setLayout(new FlowLayout())
  lumTitlePanel.add(new JLabel("Luminosity Sensor Control Panel"))
  lumControlPanel.add(lumTitlePanel)
  val lumValue = new JTextField(3)
  lumValue.setText("" + currentLumValue)
  lumValue.setSize(50, 30)
  lumValue.setMinimumSize(lumValue.getSize)
  lumValue.setMaximumSize(lumValue.getSize)
  lumValue.setEditable(false)
  val lumSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, currentLumValue)
  lumSlider.setSize(300, 60)
  lumSlider.setMinimumSize(lumSlider.getSize)
  lumSlider.setMaximumSize(lumSlider.getSize)
  lumSlider.setMajorTickSpacing(10)
  lumSlider.setMinorTickSpacing(1)
  lumSlider.setPaintTicks(true)
  lumSlider.setPaintLabels(true)
  lumSlider.addChangeListener(_ =>
    val l: Int = lumSlider.getValue
    sendMsg(s"Luminosity ${l.toDouble / 100.0}")
    lumValue.setText(s"$l")
  )
  lumControlPanel.add(lumValue)
  lumControlPanel.add(lumSlider)
  setContentPane(lumControlPanel)
  setBounds(800, 400, 300, 200)

  def display: Unit = SwingUtilities.invokeLater { () =>
    setVisible(true)
  }

  private def sendMsg(msg: String): Unit =
    client.publish("light", Buffer.buffer(msg), MqttQoS.AT_LEAST_ONCE, false, false);

object TestLuminositySimulator extends App:
  new LuminositySimulator().display

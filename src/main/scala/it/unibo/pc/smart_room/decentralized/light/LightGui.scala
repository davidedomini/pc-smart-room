package it.unibo.pc.smart_room.decentralized.light

import javax.swing.{
  BoxLayout,
  JButton,
  JFrame,
  JLabel,
  JPanel,
  JSeparator,
  JSlider,
  JTextField,
  SwingConstants,
  SwingUtilities
}
import javax.swing.JSlider
import javax.swing.event.ChangeEvent
import java.awt.{Color, FlowLayout, Graphics}

class LightGui extends JFrame:
  self =>
  val lightControlPanel = new JPanel
  setSize(500, 380)
  lightControlPanel.setLayout(new BoxLayout(lightControlPanel, BoxLayout.Y_AXIS))
  val lightTitlePanel: JPanel = new JPanel
  lightTitlePanel.setLayout(new FlowLayout())
  lightTitlePanel.add(new JLabel("Light State"))
  lightControlPanel.add(lightTitlePanel)
  val lightPanel = new LightPanel(200, 160)
  lightControlPanel.add(lightPanel)

  setContentPane(lightControlPanel)

  def display: Unit = SwingUtilities.invokeLater { () =>
    setVisible(true)
  }

  def setOn: Unit =
    lightPanel.isOn = true
    lightPanel.invalidate()
    lightPanel.repaint()

  def setOff: Unit =
    lightPanel.isOn = false
    lightPanel.invalidate()
    lightPanel.repaint()

  class LightPanel(width: Int, height: Int) extends JPanel:
    var isOn: Boolean = true
    override def paint(g: Graphics): Unit =
      if isOn then g.setColor(Color.YELLOW)
      else g.setColor(Color.BLACK)
      g.fillRect(20, 20, width - 20, height - 20)

//Just a test
object L extends App:
  val lg = new LightGui()
  lg.display
  Thread.sleep(1000)
  lg.setOff
  Thread.sleep(1000)
  lg.setOn

package it.unibo.pc.smart_room

import it.unibo.pc.smart_room.centralized.BoardSimulator

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

class SimulatorGUI(simulator: BoardSimulator) extends JFrame:
  self =>
  private var lumValue: JTextField = null
  private var lightPanel: LightPanel = null
  private var lumSlider: JSlider = null
  private var currentLumValue: Int = 0
  private var enter: JButton = null
  private var exit: JButton = null
  private var isOn: Boolean = false

  setTitle("Board Simulator")
  setSize(400, 600)
  val mainPanel: JPanel = new JPanel
  mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS))
  setContentPane(mainPanel)
  val lightControlPanel = new JPanel
  lightControlPanel.setLayout(new BoxLayout(lightControlPanel, BoxLayout.Y_AXIS))
  val lightTitlePanel: JPanel = new JPanel
  lightTitlePanel.setLayout(new FlowLayout())
  lightTitlePanel.add(new JLabel("Light State"))
  lightControlPanel.add(lightTitlePanel)
  lightPanel = new LightPanel(200, 160)
  lightControlPanel.add(lightPanel)
  mainPanel.add(lightControlPanel)
  val s: JSeparator = new JSeparator
  s.setOrientation(SwingConstants.HORIZONTAL)
  val lumControlPanel: JPanel = new JPanel
  lumControlPanel.setLayout(new BoxLayout(lumControlPanel, BoxLayout.Y_AXIS))
  val lumTitlePanel: JPanel = new JPanel
  lumTitlePanel.setLayout(new FlowLayout())
  lumTitlePanel.add(new JLabel("Luminosity Sensor Control Panel"))
  lumControlPanel.add(lumTitlePanel)
  lumValue = new JTextField(3)
  lumValue.setText("" + currentLumValue)
  lumValue.setSize(50, 30)
  lumValue.setMinimumSize(lumValue.getSize)
  lumValue.setMaximumSize(lumValue.getSize)
  lumValue.setEditable(false)
  lumSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, currentLumValue)
  lumSlider.setSize(300, 60)
  lumSlider.setMinimumSize(lumSlider.getSize)
  lumSlider.setMaximumSize(lumSlider.getSize)
  lumSlider.setMajorTickSpacing(10)
  lumSlider.setMinorTickSpacing(1)
  lumSlider.setPaintTicks(true)
  lumSlider.setPaintLabels(true)
  lumSlider.addChangeListener(_ =>
    val newTargetTemp: Int = lumSlider.getValue
    updateLum(newTargetTemp)
    lumValue.setText("" + newTargetTemp)
  )
  lumControlPanel.add(lumValue)
  lumControlPanel.add(lumSlider)
  mainPanel.add(lumControlPanel)
  val s1: JSeparator = new JSeparator
  s1.setOrientation(SwingConstants.HORIZONTAL)
  val presDetControlPanel: JPanel = new JPanel
  presDetControlPanel.setLayout(new BoxLayout(presDetControlPanel, BoxLayout.Y_AXIS))
  val presTitlePanel: JPanel = new JPanel
  presTitlePanel.setLayout(new FlowLayout())
  presTitlePanel.add(new JLabel("Presence Detection Control Panel"))
  presDetControlPanel.add(presTitlePanel)
  val buttonsPanel: JPanel = new JPanel
  buttonsPanel.setLayout(new FlowLayout())
  enter = new JButton("Enter")
  exit = new JButton("Exit")
  enter.setEnabled(true)
  exit.setEnabled(false)
  simulator.updatePresence(false)
  enter.addActionListener(e =>
    enter.setEnabled(false)
    exit.setEnabled(true)
    simulator.updatePresence(true)
  )
  exit.addActionListener(e =>
    enter.setEnabled(true)
    exit.setEnabled(false)
    simulator.updatePresence(false)
  )

  buttonsPanel.add(enter)
  buttonsPanel.add(exit)
  presDetControlPanel.add(buttonsPanel)
  mainPanel.add(presDetControlPanel)

  def updateLum(i: Int): Unit =
    currentLumValue = i
    simulator.updateLuminosity(i.toDouble / 100.0)

  def setOn: Unit =
    lightPanel.isOn = true
    lightPanel.invalidate()
    lightPanel.repaint()

  def setOff: Unit =
    lightPanel.isOn = false
    lightPanel.invalidate()
    lightPanel.repaint()

  def display: Unit = SwingUtilities.invokeLater { () =>
    self.setVisible(true)
  }

  class LightPanel(width: Int, height: Int) extends JPanel:
    var isOn: Boolean = true
    override def paint(g: Graphics): Unit =
      if isOn then g.setColor(Color.YELLOW)
      else g.setColor(Color.BLACK)
      g.fillRect(20, 20, width - 20, height - 20)

package it.unibo.pc.smart_room.centralized

import it.unibo.pc.smart_room.{LightDevice, LuminositySensorDevice, PresenceDetectionDevice}

class BoardSimulator extends LightDevice with LuminositySensorDevice with PresenceDetectionDevice:
  self =>
  private var _luminosity = 0.0
  private var isPresenceDetected = false
  private val gui = new SimulatorGUI(self)
  gui.display

  override def on: Unit = gui.setOn

  override def off: Unit = gui.setOff

  override def luminosity: Double = _luminosity

  override def presenceDetected: Boolean = isPresenceDetected

  def updateLuminosity(l: Double): Unit = _luminosity = l

  def updatePresence(isPresent: Boolean): Unit = isPresenceDetected = isPresent

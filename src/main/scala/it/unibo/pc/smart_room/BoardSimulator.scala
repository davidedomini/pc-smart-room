package it.unibo.pc.smart_room

class BoardSimulator extends LightDevice with LuminositySensorDevice with PresenceDetectionDevice:

  private val _luminosity = 0.0
  private val isPresenceDetected = false

  override def on: Unit = ???

  override def off: Unit = ???

  override def luminosity: Double = _luminosity

  override def presenceDetected: Boolean = isPresenceDetected

  def updateLuminosity(luminosity: Double): Unit = ???

  def updatePresence(isPresent: Boolean): Unit = ???

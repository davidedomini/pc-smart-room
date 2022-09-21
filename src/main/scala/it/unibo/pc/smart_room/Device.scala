package it.unibo.pc.smart_room

sealed trait Device

trait LightDevice extends Device:
  def on: Unit
  def off: Unit

trait LuminositySensorDevice extends Device:
  def luminosity: Double

trait PresenceDetectionDevice extends Device:
  def presenceDetected: Boolean
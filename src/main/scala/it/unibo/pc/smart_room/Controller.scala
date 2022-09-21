package it.unibo.pc.smart_room

class Controller(simulator: BoardSimulator) extends Thread:
  override def run(): Unit =
    while true do
      // Sense
      val presence = simulator.presenceDetected
      val luminosity = simulator.luminosity

      // Plan
      val state = presence || luminosity < 0.2

      //Act
      if state then simulator.on else simulator.off

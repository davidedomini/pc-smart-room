package it.unibo.pc.smart_room.centralized

object Launcher extends App:
  val s = new BoardSimulator
  new Controller(s).run()

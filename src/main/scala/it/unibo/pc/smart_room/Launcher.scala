package it.unibo.pc.smart_room

object Launcher extends App:
  val s = new BoardSimulator
  new Controller(s).run()

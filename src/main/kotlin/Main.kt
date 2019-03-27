package com.teddyheinen

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl.*
import cpu.Cpu
import java.io.File

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
//    val filename = "roms/cpu_instrs/individual/06-ld r,r.gb"
//    val cpu: Cpu = Cpu()
//    cpu.mmu.loadFile(File(filename))
//    while (true) {
//        cpu.tick()
////        Thread.sleep(100)
//    }
    val application = LwjglApplicationConfiguration()
    application.title = "Gameboy"
    LwjglApplication(Main(), application)
}

class Main : Game() {
    override fun create() {
        println("")
    }

}
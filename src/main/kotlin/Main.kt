package com.teddyheinen

import com.badlogic.gdx.Game
import cpu.Cpu

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    val filename = "roms/"
    val cpu: Cpu = Cpu()
    while (true) {
        cpu.tick()
//        Thread.sleep(100)
    }
}

class Main : Game() {



    override fun create() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
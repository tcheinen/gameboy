package com.teddyheinen

import cpu.Cpu

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    val filename = "roms/"
    val cpu: Cpu = Cpu()
    while (true) {
        cpu.tick()
    }
}
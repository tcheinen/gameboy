package com.teddyheinen.gameboy

import cpu.Cpu

@ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    val cpu: Cpu = Cpu()
    while (true) {
        cpu.tick()
    }
}
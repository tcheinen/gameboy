package com.teddyheinen.gameboy
import libretro.*
import kotlin.native.CName

@CName(externName = "hello", shortName = "hello")
fun hello(): String = "Hello, Kotlin/Native!"
@CName(externName = "helloInt", shortName = "helloInt")
fun helloInt(): Int = 123

@CName(externName = "helloPrint", shortName = "helloPrint")
fun helloPrint() {
    println("Hello, World!  ")
}

fun main(args: Array<String>) {
    println(hello())
}

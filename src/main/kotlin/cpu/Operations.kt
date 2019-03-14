package cpu


private var registers: Registers = Registers()

/**
 * 0xC3
 * Set program counter equal to [address]
 */
fun jpnn(address: UShort) {
    registers.pc = address
}

/**
 * 0xE9
 * Set program counter equal to the contents of register HL
 */
fun jphl() {

    registers.pc = registers.hl
}

/**
 * 0xC2, 0xD2, 0xCA, 0xDA
 * Set program counter to [address] depending on the condition cc
 * might split this out into four functions
 */
fun jpccnn(address: UShort) {

}

/**
 * 0x18
 * Increase program counter by [address]
 */
fun jrr(address: UByte) {
    registers.pc = (registers.pc + address).toUShort()
}

/**
 * 0x20, 0x30, 0x28, 0x38
 * Increase program counter by [address] depending on the condition cc
 * might split this out into four functions
 */
fun jrccr(address: UByte) {

}

/**
 * 0xCD
 * Call function at [address]
 */
fun callnn(address: UShort) {

}

/**
 * 0xC4, 0xD4, 0xCC, 0XDC
 * Call function at [address] depending on condition cc
 */
fun callccnn(address: UShort) {

}

/**
 * 0xC9
 * Return from function
 */
fun ret() {

}

/**
 * 0xC0, 0xD0, 0xC8, 0xD8
 * Return from function depending on condition cc
 */
fun retcc() {

}

/**
 * 0xD9
 * Return from function and set IME = 1
 */
fun reti() {

}

/**
 * 0xC7, 0xD7, 0xE7, 0xF7, 0xCF, 0xDF, 0xEF, 0xFF
 * Call function defined by opcode
 */
fun rst() {

}

/**
 * 0x76
 * Halt CPU Execution
 */
fun halt() {

}
/**
 * 0x10
 * Stop CPU Execution
 */
fun stop() {

}

/**
 * 0xF3
 * Disable interrupt handling and cancel EI
 */
fun di() {

}

/**
 * 0xFB
 * Schedule interrupt after next machine cycle
 */
fun ei() {

}

/**
 * 0x3F
 * Flip Carry flag and clear N and H flag
 */
fun ccf() {

}

/**
 * 0x37
 * Set carry flag and clear N and H flag
 */
fun scf() {

}




/**
 * A Sharp LR35902 CPU emulator
 */
class Cpu {

    private var state: CPUState = CPUState()
    var clock: Clock = Clock()

    /**
     * 0xC3
     * Set program counter equal to [address]
     */
    fun jpnn(address: UShort) {
        state.pc = address
    }

    /**
     * 0xE9
     * Set program counter equal to the contents of register HL
     */
    fun jphl() {
        val h: UByte = state.registers[Register.H.ordinal]
        val l: UByte = state.registers[Register.L.ordinal]
        val combined: UShort = ((h.toInt() shl 8) or l.toInt()).toUShort()
        state.pc = combined
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
        state.pc = (state.pc + address).toUShort()
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



}


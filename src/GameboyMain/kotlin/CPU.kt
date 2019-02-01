import platform.windows.byte

/**
 * A Sharp LR35902 CPU emulator
 */
class Cpu {


    /**
     * 0xC3
     * Set program counter equal to [address]
     */
    fun jpnn(address: UShort) {

    }

    /**
     * 0xE9
     * Set program counter equal to the contents of register HL
     */
    fun jphl() {

    }

    /**
     * 0xC2 0xD2 0xCA 0xDA
     * Set program counter to [address] depending on the condition cc
     * might split this out into four functions
     */
    fun jpccnn(address: UShort) {

    }

    /**
     * 0x18
     * Increase program counter by [address]
     */
    fun jrr(address: Byte) {

    }

    /**
     * 0x20 0x30 0x28 0x38
     * Increase program counter by [address] depending on the condition cc
     * might split this out into four functions
     */
    fun jrccr(address: Byte) {

    }
}
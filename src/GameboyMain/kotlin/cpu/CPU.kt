package cpu

import combine
import high
import low

/**
 * A Sharp LR35902 CPU emulator
 */
class Cpu {

    var state: CPUState = CPUState()
    var clock: Clock = Clock()


    /**
     * Name: LD r16, u16
     * Description: Set a 16 bit register to two bytes read from the program coutner
     */
    fun ld_r16_u16(reg: Register) {
        val value: UShort = 0u
        when(reg) {
            Register.SP -> state.reg.sp = value
            Register.PC -> state.reg.pc = value
            Register.AF -> state.reg.af = value
            Register.BC -> state.reg.bc = value
            Register.DE -> state.reg.de = value
            Register.HL -> state.reg.hl = value
            else -> 0u // should never happen
        }
    }

    /**
     * Increase the program counter by [num]
     */
    fun cycle(num: Int) {
        state.reg.pc = (state.reg.pc + num.toUInt()).toUShort()
    }

    /**
     * Write 8-bit [value] to the memory at address [address]
     */
    fun writeByte(address: UShort, value: UByte) {
        cycle(4)
        when (address.toInt()) {
            in 0x0000..0x3FFF -> {
            } // rom bank 0
            in 0x4000..0x7FFF -> {
            } // rom bank 1
            in 0x8000..0x9FFF -> {
            } // vram
            in 0xA000..0xBFFF -> {
            } // external ram
            in 0xC000..0xCFFF -> {
            } // wram 0
            in 0xD000..0xDFFF -> {
            } // wram 1
            in 0xFE00..0xFE9F -> {
            } // Sprite Table (OAM)
            in 0xFF00..0xFF7F -> {
            } // IO
            in 0xFF80..0xFFFE -> {
            } // HRAM
        }
    }

    /**
     * Read 8-bit [value] from memory at address [address]
     * // TODO complete this
     */
    fun readByte(address: UShort): UByte {
        cycle(4)
        when (address.toInt()) {
            in 0x0000..0x3FFF -> {
            } // rom bank 0
            in 0x4000..0x7FFF -> {
            } // rom bank 1
            in 0x8000..0x9FFF -> {
            } // vram
            in 0xA000..0xBFFF -> {
            } // external ram
            in 0xC000..0xCFFF -> {
            } // wram 0
            in 0xD000..0xDFFF -> {
            } // wram 1
            in 0xFE00..0xFE9F -> {
            } // Sprite Table (OAM)
            in 0xFF00..0xFF7F -> {
            } // IO
            in 0xFF80..0xFFFE -> {
            } // HRAM
        }
        return 0u
    }

    /**
     * Write 16-bit [value] to the memory at address [address]
     */
    fun writeShort(address: UShort, value: UShort) {
        writeByte(address, value.high)
        writeByte(address, value.low)
    }
    /**
     * Read 16-bit [value] from memory at address [address]
     */
    fun readShort(address: UShort): UShort {
        return readByte(address).combine(readByte(address))
    }
}

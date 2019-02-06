package cpu

import combine
import high
import low
import shl
import shr

/**
 * A Sharp LR35902 CPU emulator
 */
@ExperimentalUnsignedTypes
class Cpu {

    var state: CPUState = CPUState()
    var clock: Clock = Clock()


    /**
     * Name: LD r16, u16
     * Description: Set a 16 bit register to two bytes read from memory at the program counter
     */
    fun ld_r16_u16(reg: Register) {
        val value: UShort = readShort(state.reg.pc)
        when (reg) {
            Register.SP -> state.reg.sp = value
            Register.PC -> state.reg.pc = value
            Register.AF -> state.reg.af = value
            Register.BC -> state.reg.bc = value
            Register.DE -> state.reg.de = value
            Register.HL -> state.reg.hl = value
            else -> {} // should never happen
        }
    }

    /**
     * Name: LD (r16), r8
     * Description: Set the memory address stored in [reg16] to [reg8]
     * Increment HL if [reg16] is HL and decrement HL if [reg16] is SP
     */
    fun ld_r16_r8(reg16: Register, reg8: Register) {
        val address: UShort = when (reg16) {
            Register.BC -> state.reg.bc
            Register.DE -> state.reg.de
            Register.HL -> {
                state.reg.hl;
                state.reg.hl++
            }
            Register.SP -> {
                state.reg.hl;
                state.reg.hl--
            }
            else -> 0u // should never happen
        }
        writeByte(address,  state.reg.getr8(reg8))
    }

    /**
     * Name: INC r16
     * Description: Increment 16-bit register [reg]
     */
    fun inc_r16(reg: Register) {
        state.reg.setr16(reg, (state.reg.getr16(reg)+1u).toUShort())
    }
    /**
     * Name: INC r8
     * Description: Increment 16-bit register [reg]
     */
    fun inc_r8(reg: Register) {
        val currentValue: UByte = state.reg.getr8(reg)
        state.reg.addsub = false
        state.reg.zero = (currentValue == (0xffu).toUByte())
        state.reg.halfcarry = (((currentValue and 0xfu) + 1u) and 0x10u) == 0x10u
        state.reg.setr8(reg, (state.reg.getr8(reg)+1u).toUByte())
    }

    /**
     * Name: DEC r16
     * Description: Decrement 16-bit register [reg]
     */
    fun dec_r16(reg: Register) {
        state.reg.setr16(reg, (state.reg.getr16(reg)-1u).toUShort())
    }
    /**
     * Name: DEC r8
     * Description: Decrement 16-bit register [reg]
     */
    fun dec_r8(reg: Register) {
        state.reg.setr8(reg, (state.reg.getr8(reg)-1u).toUByte())
    }
    /**
     * Name: LD r8, u8
     * Description: Set an 8 bit register to a bytes read from memory at the program counter
     */
    fun ld_r8_u8(reg: Register) {
        val value: UByte = readByte(state.reg.pc)
        when (reg) {
            Register.A -> state.reg.a = value
            Register.B -> state.reg.b = value
            Register.C -> state.reg.c = value
            Register.D -> state.reg.d = value
            Register.E -> state.reg.e = value
            Register.H -> state.reg.h = value
            Register.L -> state.reg.l = value
            else -> {} // should never happen
        }
    }

    /**
     * Name: RLC r8
     * Description: Rotate Left Circular for register [reg]
     * Sets [reg] to (A shl 1) or (A shr 7)
     * If bit 7 is set, set carry.  Otherwise reset.  All other flags are reset
     */
    fun rlc(reg: Register) {
        state.reg.f.clear()
        state.reg.carry = state.reg.a >= 0x80u
        state.reg.setr8(reg, (state.reg.getr8(reg) shl 1) or (state.reg.getr8(reg) shr 7))
    }

    /**
     * Name: LD r8,r8
     * Description: Copy 8-bit register [src] to [dest]
     */
    fun ld_r8_r8(dest: Register, src: Register) {
        state.reg.setr8(dest, state.reg.getr8(src))
    }

    /**
     * Name: ADD A,r8
     * Description: Add [reg] to A and then store result in A
     *
     */
    fun add_a_r8(reg: Register) {
        val prev = state.reg.a
        state.reg.a = (state.reg.a + state.reg.getr8(reg)).toUByte()
        state.reg.addsub = false
        state.reg.zero = state.reg.a == 0.toUByte()
        state.reg.halfcarry = (((state.reg.a and 0xfu) + 1u) and 0x10u) == 0x10u
        state.reg.carry = state.reg.a < prev
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

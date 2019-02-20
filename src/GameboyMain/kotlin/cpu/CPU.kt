package cpu

import combine
import high
import kotlinx.cinterop.toByte
import low
import shl
import shr

/**
 * A Sharp LR35902 CPU emulator
 */
@ExperimentalUnsignedTypes
class Cpu {

    private var registers: Registers = Registers()

    var status: State = State.Okay
    var ime: Boolean = false
    /**
     * Name: LD r16, u16
     * Description: Set a 16 bit register to two bytes read from memory at the program counter
     */
    fun ld_r16_u16(reg: Register) {
        val value: UShort = readShort(registers.pc)
        when (reg) {
            Register.SP -> registers.sp = value
            Register.PC -> registers.pc = value
            Register.AF -> registers.af = value
            Register.BC -> registers.bc = value
            Register.DE -> registers.de = value
            Register.HL -> registers.hl = value
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
            Register.BC -> registers.bc
            Register.DE -> registers.de
            Register.HL -> {
                registers.hl
                registers.hl++
            }
            Register.SP -> {
                registers.hl
                registers.hl--
            }
            else -> 0u // should never happen
        }
        writeByte(address,  registers.getr8(reg8))
    }
    /**
     * Name: LD r8,(r16)
     * Description: Set [reg8] to the memory referenced by [reg16]
     * Increment HL if [reg16] is HL and decrement HL if [reg16] is SP
     */
    fun ld_r8_r16(reg8: Register, reg16: Register) {
        val address: UShort = when (reg16) {
            Register.BC -> registers.bc
            Register.DE -> registers.de
            Register.HL -> {
                registers.hl
                registers.hl++
            }
            Register.SP -> {
                registers.hl
                registers.hl--
            }
            else -> 0u // should never happen
        }
        registers.setr8(reg8, readByte(address))
    }

    /**
     * Name: INC r16
     * Description: Increment 16-bit register [reg]
     */
    fun inc_r16(reg: Register) {
        registers.setr16(reg, (registers.getr16(reg)+1u).toUShort())
    }
    /**
     * Name: INC r8
     * Description: Increment 16-bit register [reg]
     */
    fun inc_r8(reg: Register) {
        val currentValue: UByte = registers.getr8(reg)
        registers.addsub = false
        registers.zero = (currentValue == (0xffu).toUByte())
        registers.halfcarry = (((currentValue and 0xfu) + 1u) and 0x10u) == 0x10u
        registers.setr8(reg, (registers.getr8(reg)+1u).toUByte())
    }

    /**
     * Name: DEC r16
     * Description: Decrement 16-bit register [reg]
     */
    fun dec_r16(reg: Register) {
        registers.setr16(reg, (registers.getr16(reg)-1u).toUShort())
    }
    /**
     * Name: DEC r8
     * Description: Decrement 16-bit register [reg]
     */
    fun dec_r8(reg: Register) {
        registers.setr8(reg, (registers.getr8(reg)-1u).toUByte())
    }
    /**
     * Name: LD r8, u8
     * Description: Set an 8 bit register to a bytes read from memory at the program counter
     */
    fun ld_r8_u8(reg: Register) {
        val value: UByte = readByte(registers.pc)
        when(reg) {
            Register.HL -> writeByte(registers.hl, value)
            else -> registers.setr8(reg,value)
        }
    }

    /**
     * Name: RLC r8
     * Description: Rotate Left Circular for register [reg]
     * Sets [reg] to ([reg] shl 1) or ([reg] shr 7)
     * If bit 7 is set, set carry.  Otherwise reset.  All other flags are reset
     */
    fun rlc(reg: Register) {
        registers.f.clear()
        registers.setr8(reg, (registers.getr8(reg) shl 1) or (registers.getr8(reg) shr 7))
        registers.carry = registers.a >= 0x80u
    }

    /**
     * Name: RRC r8
     * Description: Rotate Right Circular for register [reg]
     * Sets [reg] to ([reg] shr 1) or ([reg] shl 7)
     * If bit 0 is set, set carry.  Otherwise reset.  All other flags are reset
     */
    fun rrc(reg: Register) {
        registers.f.clear()
        registers.setr8(reg, (registers.getr8(reg) shr 1) or (registers.getr8(reg) shl 7))
        registers.carry = (registers.a and 0x1u) == 0x1u.toUByte()
    }

    /**
     * Name: RL r8
     * Description: Rotate Left for register [reg]
     * Sets [reg] to ([reg] shl 1) or c
     * If bit 0 is set, set carry.  Otherwise reset.  All other flags are reset
     */
    fun rl(reg: Register) {
        registers.setr8(reg, (registers.getr8(reg) shl 1) or registers.carry.toByte().toUByte())
        registers.f.clear()
        registers.carry = (registers.a and 0x80u) == 0x80.toUByte()
    }

    /**
     * Name: RR r8
     * Description: Rotate Right for register [reg]
     * Sets [reg] to ([reg] shr 1) or c
     * If bit 0 is set, set carry.  Otherwise reset.  All other flags are reset
     */
    fun rr(reg: Register) {
        registers.setr8(reg, (registers.getr8(reg) shr 1) or registers.carry.toByte().toUByte())
        registers.f.clear()
        registers.carry = (registers.a and 0x01u) == 0x01u.toUByte()
    }


    /**
     * Name: CPL
     * Description: One's Complement of [reg]
     */
    fun cpl(reg: Register) {
        registers.setr8(reg,registers.getr8(reg).inv())
        registers.addsub = true
        registers.halfcarry = true
    }


    /**
     * Name: JR
     * Description: Jump r8 forward if [condition] is true
     */
    fun jr(condition: Condition) {
        val value: UByte = readByte(registers.pc)
        if(registers.checkCondition(condition)) {
            registers.pc = (registers.pc + value).toUShort()
        }
    }

    /**
     * Name: JP CC
     * Description: Jump to r16 if [condition] is true
     */
    fun jpc(condition: Condition) {
        val value: UShort = readShort(registers.pc)
        if(registers.checkCondition(condition)) {
            registers.pc = value
        }
    }

    /**
     * Name: RET
     * Description: Pop short from stack and then jump to it
     * Interrupt if [reti] is true
     */
    fun ret(reti: Boolean) {
        val addr: UShort = popShort()
        ime = ime or reti
        registers.pc = addr
    }

    /**
     * Name: LD (u16),SP
     * Description: Write SP into the address pointed to by 16-bit value pointed to by PC
     */
    fun ld_u16_sp() {
        val address: UShort = readShort(registers.pc)
        writeShort(address, registers.sp)
    }

    /**
     * Name: ADD HL, r16
     * Description: Add 16 bit register [reg] to HL and store the result in HL
     * Half Carry is set if there is a carry between bits 11 and 12
     */
    fun add_hl_r16(reg: Register) {
        val src: UShort = registers.getr16(reg)
        val result: UShort = (registers.hl + src).toUShort()

        registers.addsub = false
        registers.halfcarry = ((registers.hl and 0xFFFu) + (src and 0xFFFu)) and 0x1000u == 0x1000u
        registers.carry = result < registers.hl
        registers.hl = result
    }

    /**
     * Name: LD r8,r8
     * Description: Copy 8-bit register [src] to [dest]
     */
    fun ld_r8_r8(dest: Register, src: Register) {
        val value: UByte = get_u8_register(src)
        registers.setr8(dest, value)
    }

    /**
     * Name: SCF
     * Description: Set Carry Flag
     */
    fun scf() {
        registers.addsub = false
        registers.halfcarry = false
        registers.carry = true
    }

    /**
     * Name: CCF
     * Description: Invert (compliment) Carry Flag
     */
    fun ccf() {
        registers.addsub = false
        registers.halfcarry = false
        registers.carry = !registers.carry
    }


    /**
     * Name: POP r16
     * Description: Read two bytes from stack onto [reg] and increment stack pointer twice
     */
    fun pop(reg: Register) {
        val value: UShort = popShort()
        registers.setr16(reg, value)
    }

    /**
     * Name: PUSH r16
     * Description: Write from [reg] onto stack and decrement stack pointer twice
     */
    fun push(reg: Register) {
        val value: UShort = registers.getr16(reg)
        pushShort(value)
    }
    /**
     * Name: ADD A,r8
     * Description: Add [reg] to A and then store result in A
     */
    fun add_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val result = (registers.a + src).toUByte()
        registers.addsub = false
        registers.zero = result == 0.toUByte()
        registers.halfcarry = (((registers.a and 0xfu) + 1u) and 0x10u) == 0x10u
        registers.carry = result < registers.a
        registers.a = result
    }

    /**
     * Name: ADC A,r8
     * Description: Add [reg] and carry to A and then store result in A
     *
     */
    fun addc_a_r8(reg: Register) {
        val carry: UInt = if(registers.carry) 1u else 0u
        val src = get_u8_register(reg)
        val result = (registers.a + src + carry).toUByte()
        registers.addsub = false
        registers.zero = result == 0.toUByte()
        registers.halfcarry = (registers.a and 0xfu) + (src and 0xfu) + carry > 0xfu
        registers.carry = (registers.a.toUShort()) + (src.toUShort()) + carry > 0xffu
        registers.a = result
    }

    /**
     * Name: SUB A,r8
     * Description: Subtract [reg] from A and then store result in A
     */
    fun sub_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val result = (registers.a - src).toUByte()
        registers.addsub = true
        registers.zero = result == 0.toUByte()
        registers.halfcarry = result and 0xfu < src and 0xfu
        registers.carry = src > result
        registers.a = result
    }

    /**
     * Name: SBC A,r8
     * Description: Subtract [reg] and carry from A and then store result in A
     */
    fun subc_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val carry: UInt = if(registers.carry) 1u else 0u
        val result = (registers.a - src - carry).toUByte()
        registers.addsub = true
        registers.zero = result == 0.toUByte()
        registers.halfcarry = result and 0xfu < (src and 0xfu) + carry
        registers.carry = result > 0xffu
        registers.a = result
    }

    /**
     * Name: AND A,r8
     * Description: Bitwise AND between [reg] with A and then store in A
     */
    fun and_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val result = (registers.a and src)
        registers.addsub = false
        registers.carry = false
        registers.zero = result == 0.toUByte()
        registers.halfcarry = true
        registers.a = result
    }

    /**
     * Name: XOR A,r8
     * Description: Bitwise XOR between [reg] with A and then store in A
     */
    fun xor_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val result = (registers.a xor src)
        registers.addsub = false
        registers.carry = false
        registers.zero = result == 0.toUByte()
        registers.halfcarry = false
        registers.a = result
    }
    /**
     * Name: OR A,r8
     * Description: Bitwise OR between [reg] with A and then store in A
     */
    fun or_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val result = (registers.a or src)
        registers.addsub = false
        registers.carry = false
        registers.zero = result == 0.toUByte()
        registers.halfcarry = false
        registers.a = result
    }

    /**
     * Name: CP A,r8
     * Description: Subtract [reg] from A and discard result
     */
    fun cp_a_r8(reg: Register) {
        val src = get_u8_register(reg)
        val a = registers.a
        registers.addsub = true
        registers.carry = a < src
        registers.zero = src == a
        registers.halfcarry = a and 0xfu < src and 0xfu
    }

    /**
     * Name: RET C
     * Description: Return conditionally
     */
    fun retc(cond: Condition) {
        if(registers.checkCondition(cond)) {
            val address: UShort = popShort()
            registers.pc = address
        }
    }


    /**
     * Name: HALT
     * Description: Halts execution
     */
    fun halt() {
        // TODO im pretty sure theres another condition
        if(ime) {
            status = State.Halt
        }
    }
    /**
     * Name: STOP
     * Description: Stops execution
     */
    fun stop() {
        status = State.Stop
    }


    /**
     * Name: CALL CC,n
     * Description: Call addr at pc if [cond] is true
     */
    fun call(cond: Condition) {
        val addr: UShort = readShort(registers.pc)
        if(registers.checkCondition(cond)) {
            val pc: UShort = registers.pc
            pushShort(pc)
            registers.pc = addr
        }
    }

    /**
     * Get an 8-bit value from register
     * Value will be the contents of an 8-bit register or a byte at the memory address stored in a 16-bit register
     */
    fun get_u8_register(reg: Register): UByte {
        return when(reg) {
            Register.A -> registers.a
            Register.B -> registers.b
            Register.C -> registers.c
            Register.D -> registers.d
            Register.E -> registers.e
            Register.H -> registers.h
            Register.L -> registers.l
            Register.HL -> readByte(registers.hl)
            Register.PC -> readByte(registers.pc)
            Register.u8 -> readByte(registers.pc)
            else -> 0u // should not happen
        }

    }

    /**
     * Increase the program counter by [num]
     */
    fun increase_pc(num: Int) {
        registers.pc = (registers.pc + num.toUInt()).toUShort()
    }


    /**
     * Read 8-bit value from memory at stack pointer and increment
     */
    fun popByte(): UByte {
        val output = readByte(registers.sp)
        registers.sp++
        return output
    }

    /**
     * Decrement stack pointer and write [value] at stack pointer
     */
    fun pushByte(value: UByte) {
        registers.sp--
        writeByte(registers.sp, value)
    }

    /**
     * Read 16-bit value from memory at stack pointer and increment
     */
    fun popShort(): UShort {
        val out1 = popByte()
        val out2 = popByte()
        return out1.combine(out2)
    }

    /**
     * Decrement stack pointer and write 16-bit [value] at stack pointer
     */
    fun pushShort(value: UShort) {
        pushByte(value.high)
        pushByte(value.low)
    }

    /**
     * Write 8-bit [value] to the memory at address [address]
     */
    fun writeByte(address: UShort, value: UByte) {
        increase_pc(4)
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
     * Read 8-bit value from memory at address [address]
     * // TODO complete this
     */
    fun readByte(address: UShort): UByte {
        increase_pc(4)
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
     * Write 16-bit value to the memory at address [address]
     */
    fun writeShort(address: UShort, value: UShort) {
        writeByte(address, value.high)
        writeByte(address, value.low)
    }

    /**
     * Read 16-bit value from memory at address [address]
     */
    fun readShort(address: UShort): UShort {
        return readByte(address).combine(readByte(address))
    }
}

package cpu

import com.teddyheinen.MMU
import combine
import high
import low
import shl
import shr
import toUByte
import java.lang.Exception

/**
 * A Sharp LR35902 CPU emulator
 */
@ExperimentalUnsignedTypes
class Cpu {

    private var registers: Registers = Registers()
    public var mmu: MMU = MMU()
    var status: State = State.Okay
    var ime: Boolean = false

    fun tick() {
        val code = readByteCycle().toInt()
        val instruction = Opcodes.op[code]
        instruction.operation(this)
        println("${instruction.name} - ${code.toString(16)} - ${registers.pc}")
//        registers.pc = (registers.pc + instruction.length.toUInt()).toUShort()

    }

    /**
     * Name: LD r16, u16
     * Description: Set a 16 bit register to two bytes read from memory at the program counter
     */
    fun ld_r16_u16(reg: Register) {
        val value: UShort = readShortCycle()
        when (reg) {
            Register.SP -> registers.sp = value
            Register.PC -> registers.pc = value
            Register.AF -> registers.af = value
            Register.BC -> registers.bc = value
            Register.DE -> registers.de = value
            Register.HL -> registers.hl = value
            else -> {
            } // should never happen
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
        writeByte(address, registers.getr8(reg8))
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
        registers.setr16(reg, (registers.getr16(reg) + 1u).toUShort())
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
        registers.setr8(reg, (registers.getr8(reg) + 1u).toUByte())
    }

    /**
     * Name: DEC r16
     * Description: Decrement 16-bit register [reg]
     */
    fun dec_r16(reg: Register) {
        registers.setr16(reg, (registers.getr16(reg) - 1u).toUShort())
    }

    /**
     * Name: DEC r8
     * Description: Decrement 16-bit register [reg]
     */
    fun dec_r8(reg: Register) {
        registers.setr8(reg, (registers.getr8(reg) - 1u).toUByte())
    }

    /**
     * Name: LD r8, u8
     * Description: Set an 8 bit register to a bytes read from memory at the program counter
     */
    fun ld_r8_u8(reg: Register) {
        val value: UByte = readByte(registers.pc)
        when (reg) {
            Register.HL -> writeByte(registers.hl, value)
            else -> registers.setr8(reg, value)
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
        registers.setr8(reg, (registers.getr8(reg) shl 1) or registers.carry.toUByte())
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
        registers.setr8(reg, (registers.getr8(reg) shr 1) or registers.carry.toUByte())
        registers.f.clear()
        registers.carry = (registers.a and 0x01u) == 0x01u.toUByte()
    }

    /**
     * Name: SLA
     * Description: Left shift [reg] by one
     */
    fun sla(reg: Register) {
        val result: UByte = registers.getr8(reg) shl 1

        registers.zero = result.toUInt() == 0u
        registers.addsub = false
        registers.halfcarry = false
        registers.carry = (registers.getr8(reg) and 0x80u).toUInt() == 0x80u

        registers.setr8(reg, result)
    }

    /**
     * Name: SRA
     * Description: Right shift [reg] by one and AND with 0x80
     */
    fun sra(reg: Register) {
        val result: UByte = (registers.getr8(reg) shr 1) and 0x80u

        registers.zero = result.toUInt() == 0u
        registers.addsub = false
        registers.halfcarry = false
        registers.carry = (registers.getr8(reg) and 0x01u).toUInt() == 0x01u

        registers.setr8(reg, result)
    }

    /**
     * Name: SWAP
     * Description: Swap upper and lower parts of [reg] by one
     */
    fun swap(reg: Register) {
        val source = registers.getr8(reg)
        registers.setr8(reg, source.low or source.high)
        registers.carry = false
        registers.halfcarry = false
        registers.addsub = false
        registers.zero = source.toUInt() == 0u
    }

    /**
     * Name: SRL
     * Description: Right shift [reg] by one
     */
    fun srl(reg: Register) {
        val result: UByte = registers.getr8(reg) shr 1

        registers.zero = result.toUInt() == 0u
        registers.addsub = false
        registers.halfcarry = false
        registers.carry = (registers.getr8(reg) and 0x01u).toUInt() == 0x01u

        registers.setr8(reg, result)
    }

    /**
     * Name: BIT
     * Description: Test [reg] after masking with [mask]
     */
    fun bit(reg: Register, mask: UByte) {
        registers.addsub = false
        registers.halfcarry = true
        val t = (registers.getr8(reg) and mask) == (-128).toUByte()
        registers.zero = t
    }

    /**
     * Name: RES
     * Description: AND [reg] with negation of provided mask
     */
    fun res(reg: Register, mask: UByte) {
        registers.setr8(reg, registers.getr8(reg) and mask.inv())
    }

    /**
     * Name: SET
     * Description: OR [reg] with provided mask
     */
    fun set(reg: Register, mask: UByte) {
        registers.setr8(reg, registers.getr8(reg) or mask)
    }

    /**
     * Name: CPL
     * Description: One's Complement of [reg]
     */
    fun cpl(reg: Register) {
        registers.setr8(reg, registers.getr8(reg).inv())
        registers.addsub = true
        registers.halfcarry = true
    }


    /**
     * Name: JR
     * Description: Jump r8 forward if [condition] is true
     */
    fun jr(condition: Condition) {
        val value: UByte = readByte(registers.pc)
        if (registers.checkCondition(condition)) {
            registers.pc = (registers.pc + value).toUShort()
        }
    }

    /**
     * Name: JP CC
     * Description: Jump to r16 if [condition] is true
     */
    fun jpc(condition: Condition) {
        val value: UShort = readShort(registers.pc)
        if (registers.checkCondition(condition)) {
            registers.pc = value
        }
    }

    /**
     * Name: JP HL
     * Description: Jump to HL
     */
    fun jphl() {
        registers.pc = registers.hl
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
     * Name: RST
     * Description: Push PC onto stack and then jump to [addr]
     */
    fun rst(addr: UShort) {
        pushShort(registers.pc)
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
     * Name: LDH n,r8
     * Description: Write [reg] to FF00 + u8
     */
    fun ldh_u8_r8(reg: Register) {
        val address: UShort = (0xFF00u or readByte(registers.pc).toUInt()).toUShort()
        writeByte(address, registers.getr8(reg))
    }

    /**
     * Name: LDH c,r8
     * Description: Write [reg] to FF00 + C
     */
    fun ldh_c_r8(reg: Register) {
        val address: UShort = (0xFF00u or registers.c.toUInt()).toUShort()
        writeByte(address, registers.getr8(reg))
    }

    /**
     * Name: LDH r8,n
     * Description: Write FF00 + u8 to [reg]
     */
    fun ldh_r8_u8(reg: Register) {
        val address: UShort = (0xFF00u or readByte(registers.pc).toUInt()).toUShort()
        registers.setr8(reg, readByte(address))
    }

    /**
     * Name: LDH r8,c
     * Description: Write FF00 + C to [reg]
     */
    fun ldh_r8_c(reg: Register) {
        val address: UShort = (0xFF00u or registers.c.toUInt()).toUShort()
        registers.setr8(reg, readByte(address))
    }

    /**
     * Name: LD (u16),r8
     * Description: Write 8-bit [reg] to memory at the address specified by memory at PC
     */
    fun ld_u16_r8(reg: Register) {
        val value: UByte = registers.getr8(reg)
        val addr: UShort = readShort(registers.pc)
        writeByte(addr, value)
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
     * Name: DAA
     * Description: Decimal adjust A
     */
    fun daa() {

    }

    /**
     * Name: ADD SP,n
     * Description: Add byte from PC location to SP
     */
    fun add_sp_u8() {
        val src = readByte(registers.pc)
        val result = (registers.a + src).toUShort()
        registers.addsub = false
        registers.zero = false
        registers.halfcarry = ((registers.sp and 0x0Fu) + (src and 0x0Fu)) > 0x0Fu
        registers.carry = ((registers.sp and 0xFFu) + (src and 0xFFu)) > 0xFFu
        registers.sp = result
    }

    /**
     * Name: ADC A,r8
     * Description: Add [reg] and carry to A and then store result in A
     *
     */
    fun addc_a_r8(reg: Register) {
        val carry: UInt = if (registers.carry) 1u else 0u
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
        val carry: UInt = if (registers.carry) 1u else 0u
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
        if (registers.checkCondition(cond)) {
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
        if (ime) {
            status = State.Halt
        }
    }

    /**
     * Name: DI
     * Description: Disables IME flag
     */
    fun di() {
        ime = false
    }

    /**
     * Name: EI
     * Description: Enables IME flag
     */
    fun ei() {
        ime = true
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
        if (registers.checkCondition(cond)) {
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
        return when (reg) {
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
     * Name: LD HL, SP+n
     * Description: Loads SP + byte from memory at pc into HL
     */
    fun ld_hl_sp() {
        val n: UByte = readByte(registers.pc)
        val result: UShort = (registers.sp + n).toUShort()
        registers.f.clear()
        registers.halfcarry = (registers.sp and 0x0Fu) + (n and 0x0Fu) > 0x0Fu
        registers.carry = (registers.sp and 0xFFu) + (n and 0xFFu) > 0xFFu
    }

    /**
     * Name: LD SP,HL
     * Description: Load HL into SP
     */
    fun ld_sp_hl() {
        registers.sp = registers.hl
    }

    /**
     * Name: LD r8,u16
     * Description: Read 8-bit value that PC points to into [reg]
     */
    fun ld_r8_u16(reg: Register) {
        val address: UShort = readShort(registers.pc)
        val n: UByte = readByte(address)
        registers.setr8(reg, n)
    }

    /**
     * Name: PREFIX CB
     * Description: Read byte and execute appropriate CB instruction
     */
    fun cb() {
        val code: UByte = readByteCycle()
        val reg: Register = when((code and 7u).toUInt()) {
            0u -> Register.B
            1u -> Register.C
            2u -> Register.D
            3u -> Register.E
            4u -> Register.H
            5u -> Register.L
            6u -> Register.HL
            7u -> Register.A
            else -> throw Exception("CB reg code returned an impossible value")
        }
        when ((code shr 6).toUInt()) {
            0x0u -> when((code shr 3).toUInt()) {
                0x0u -> {rlc(reg)}
                0x1u -> {rrc(reg)}
                0x2u -> {rl(reg)}
                0x3u -> {rr(reg)}
                0x4u -> {sla(reg)}
                0x5u -> {sra(reg)}
                0x6u -> {swap(reg)}
                0x7u -> {srl(reg)}
            }
            0x1u -> {bit(reg, (1u shl ((code shr 3).toInt() and 7)).toUByte())}
            0x2u -> {res(reg, (1u shl ((code shr 3).toInt() and 7)).toUByte())}
            0x3u -> {set(reg, (1u shl ((code shr 3).toInt() and 7)).toUByte())}
        }
    }

    /**
     *
     */

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
        mmu[address.toShort()] = value
    }

    /**
     * Read 8-bit value from memory at address [address]
     * // TODO complete this
     */
    fun readByte(address: UShort): UByte {
        return mmu[address.toShort()]
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

    /**
     * Read 16-bit value from memory and increment PC
     */
    fun readShortCycle(): UShort {
        return readByteCycle().combine(readByteCycle())
    }

    /**
     * Write 16-bit value to memory at PC and decrement PC
     */
    fun writeShortCycle(value: UShort) {
        writeByteCycle(value.high)
        writeByteCycle(value.low)
    }

    /**
     * Read 8-bit value from memory and increment PC
     */
    fun readByteCycle(): UByte {
        val value = readByte(registers.pc)
        registers.pc++
        return value
    }


    /**
     * Write 8-bit value to memory at PC and decrement PC
     */
    fun writeByteCycle(value: UByte) {
        writeByte(registers.pc, value)
        registers.pc--
    }
}

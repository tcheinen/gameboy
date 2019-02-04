package cpu

import bitset
import byte
import combine
import high
import low

@kotlin.ExperimentalUnsignedTypes
data class CPUState(val reg: Registers = Registers(),
                    var m: UShort = 0u,
                    var t: UShort = 0u)
@kotlin.ExperimentalUnsignedTypes
data class Clock(var m: UShort = 0u,
                 var t: UShort = 0u)


enum class Register {
    A,
    B,
    C,
    D,
    E,
    H,
    L,
    F,
    SP,
    PC,
    AF,
    BC,
    DE,
    HL
}

/**
 * Stores the CPU Registers with convenience methods for accessing them as 16 bit
 */
@kotlin.ExperimentalUnsignedTypes
class Registers(var a: UByte = 0u,
                var b: UByte = 0u,
                var c: UByte = 0u,
                var d: UByte = 0u,
                var e: UByte = 0u,
                var h: UByte = 0u,
                var l: UByte = 0u,
                var f: BitSet = BitSet(8),
                var sp: UShort = 0u,
                var pc: UShort = 0u) {
    companion object {
        const val ZERO_FLAG: Int = 7
        const val ADD_SUB_FLAG: Int = 6
        const val HALF_CARRY_FLAG: Int = 5
        const val CARRY_FLAG: Int = 4
    }

    var af: UShort
        get() =  a.combine(f.byte)
        set(num) {
            a = num.high
            f = num.low.bitset
        }
    var bc: UShort
        get() = b.combine(c)
        set(num) {
            b = num.high
            c = num.low
        }

    var de: UShort
        get() = d.combine(e)
        set(num) {
            d = num.high
            e = num.low
        }

    var hl: UShort
        get() = h.combine(l)
        set(num) {
            h = num.high
            l = num.low
        }

    var zero: Boolean
        get() = f[ZERO_FLAG]
        set(num) = f.set(ZERO_FLAG, num)

    var addsub: Boolean
        get() = f[ADD_SUB_FLAG]
        set(num) = f.set(ADD_SUB_FLAG, num)

    var halfcarry: Boolean
        get() = f[HALF_CARRY_FLAG]
        set(num) = f.set(HALF_CARRY_FLAG, num)

    var carry: Boolean
        get() = f[CARRY_FLAG]
        set(num) = f.set(CARRY_FLAG, num)

    /**
     * Return 8-bit register [reg] by enum [Register]
     * This only exists if I want to get or set a register by enum
     */
    fun getr8(reg: Register): UByte {
        return when(reg) {
            Register.A -> a
            Register.B -> b
            Register.C -> c
            Register.D -> d
            Register.E -> e
            Register.H -> h
            Register.L -> l
            else -> 0u
        }
    }
    /**
     * Set 8-bit register [reg] to [value]
     */
    fun setr8(reg: Register, value: UByte) {
        when(reg) {
            Register.A -> a = value
            Register.B -> b = value
            Register.C -> c = value
            Register.D -> d = value
            Register.E -> e = value
            Register.H -> h = value
            Register.L -> l = value
            else -> {}
        }
    }
    /**
     * Return 16-bit register [reg] by enum [Register]
     * This only exists if I want to get or set a register by enum
     */
    fun getr16(reg: Register): UShort {
        return when(reg) {
            Register.SP -> sp
            Register.PC -> pc
            Register.AF -> af
            Register.BC -> bc
            Register.DE -> de
            Register.HL -> hl
            else -> 0u
        }
    }

    /**
     * Set 16-bit register [reg] to [value]
     */
    fun setr16(reg: Register, value: UShort) {
        when(reg) {
            Register.SP -> sp = value
            Register.PC -> pc = value
            Register.AF -> af = value
            Register.BC -> bc = value
            Register.DE -> de = value
            Register.HL -> hl = value
            else -> {}
        }
    }
}
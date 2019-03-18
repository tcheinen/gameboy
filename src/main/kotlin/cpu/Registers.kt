package cpu

import bitset
import byte
import combine
import high
import low
import java.util.*

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
        get() = f.byte.combine(a)
        set(num) {
            a = num.high
            f = num.low.bitset
        }
    var bc: UShort
        get() = c.combine(b)
        set(num) {
            b = num.high
            c = num.low
        }

    var de: UShort
        get() = e.combine(d)
        set(num) {
            d = num.high
            e = num.low
        }

    var hl: UShort
        get() = l.combine(h)
        set(num) {
            h = num.high
            l = num.low
        }

    var zero: Boolean
        get() = f[ZERO_FLAG]
        set(num) = f.set(ZERO_FLAG, num)

    var addsub: Boolean // flag n
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
     */
    fun getr8(reg: Register): UByte {
        return when (reg) {
            Register.A -> a
            Register.B -> b
            Register.C -> c
            Register.D -> d
            Register.E -> e
            Register.H -> h
            Register.L -> l
            else -> throw Exception("Tried to get 16 bit reg with getr8")
        }
    }

    /**
     * Set 8-bit register [reg] to [value]
     */
    fun setr8(reg: Register, value: UByte) {
        when (reg) {
            Register.A -> a = value
            Register.B -> b = value
            Register.C -> c = value
            Register.D -> d = value
            Register.E -> e = value
            Register.H -> h = value
            Register.L -> l = value
            else -> throw Exception("Tried to set 16 bit reg with getr8")
        }
    }

    /**
     * Return 16-bit register [reg] by enum [Register]
     */
    fun getr16(reg: Register): UShort {
        return when (reg) {
            Register.SP -> sp
            Register.PC -> pc
            Register.AF -> af
            Register.BC -> bc
            Register.DE -> de
            Register.HL -> hl
            else -> throw Exception("Tried to get 8 bit reg with getr16")
        }
    }

    /**
     * Set 16-bit register [reg] to [value]
     */
    fun setr16(reg: Register, value: UShort) {
        when (reg) {
            Register.SP -> sp = value
            Register.PC -> pc = value
            Register.AF -> af = value
            Register.BC -> bc = value
            Register.DE -> de = value
            Register.HL -> hl = value
            else -> throw Exception("Tried to set 8 bit reg with getr16")
        }
    }

    /**
     * Return 8-bit register [reg] by enum [Register]
     */
    fun getFlag(flag: Flag): Boolean {
        return when (flag) {
            Flag.Z -> zero
            Flag.N -> addsub
            Flag.H -> halfcarry
            Flag.C -> carry
        }
    }

    /**
     * Set 8-bit register [reg] to [value]
     */
    fun setFlag(flag: Flag, value: Boolean) {
        when (flag) {
            Flag.Z -> zero = value
            Flag.N -> addsub = value
            Flag.H -> halfcarry = value
            Flag.C -> carry = value
        }
    }

    /**
     * Return boolean based on truth value of condition [cond]
     */
    fun checkCondition(cond: Condition): Boolean {
        return when(cond) {
            Condition.Z -> zero
            Condition.NZ -> !zero
            Condition.C -> carry
            Condition.NC -> !carry
            Condition.TRUE -> true
        }
    }
}

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
    HL,
    u8 // refers to the memory at the address PC is pointing to
}

enum class Flag {
    Z,
    N,
    H,
    C
}


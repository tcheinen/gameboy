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
                var sf: UShort = 0u,
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
}
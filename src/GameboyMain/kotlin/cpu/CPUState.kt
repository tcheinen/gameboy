package cpu

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
class Registers(var A: UByte = 0u,
                     var B: UByte = 0u,
                     var C: UByte = 0u,
                     var D: UByte = 0u,
                     var E: UByte = 0u,
                     var H: UByte = 0u,
                     var L: UByte = 0u,
                     var F: UByte = 0u,
                     var SP: UShort = 0u,
                     var PC: UShort = 0u) {
    var AF: UShort
        get() =  A.combine(F)
        set(num: UShort) {
            A = num.high
            F = num.low
        }
    var BC: UShort
        get() = B.combine(C)
        set(num: UShort) {
            B = num.high
            C = num.low
        }

    var DE: UShort
        get() = D.combine(E)
        set(num: UShort) {
            D = num.high
            E = num.low
        }

    var HL: UShort
        get() = H.combine(L)
        set(num: UShort) {
            H = num.high
            L = num.low
        }
}
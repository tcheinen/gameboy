package cpu

data class CPUState(val registers: UByteArray = UByteArray(8),
                    var pc: UShort = 0u,
                    var sp: UShort = 0u,
                    var m: UShort = 0u,
                    var t: UShort = 0u)

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
    F
}
package cpu

enum class Condition {
    Z,
    NZ,
    C,
    NC,
    TRUE // Always evaluates to true
}

enum class State {
    Okay,
    Stop,
    Halt,
    Hang
}
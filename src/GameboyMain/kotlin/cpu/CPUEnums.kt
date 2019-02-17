package cpu

enum class Condition {
    Z,
    NZ,
    C,
    NC
}

enum class State {
    Okay,
    Stop,
    Halt,
    Hang
}
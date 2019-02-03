package cpu

data class Opcode(val name: String, val length: Int, val time: Int, val operation: () -> Unit)


class Opcodes() {

    companion object {
        val op: Array<Opcode> = arrayOf()
    }

    init {
        op[0x00] = Opcode("NOP", 1,1, {})
        op[0x01] = Opcode("LD BC, nn", 3,12, {})
        op[0x02] = Opcode("LD (BC), A", 1,8, {})
        op[0x03] = Opcode("INC BC", 1,8, {})
        op[0x04] = Opcode("INC B", 1,4, {})
        op[0x05] = Opcode("DEC B", 1,4, {})
        op[0x06] = Opcode("LD B, u8", 2,8, {})
        op[0x07] = Opcode("RLCA", 1,4, {})
        op[0x08] = Opcode("LD (u16), SP", 3,20, {})
        op[0x09] = Opcode("ADD HL, BC", 1,8, {})
        op[0x0A] = Opcode("LD A, (BC)", 1,8, {})
        op[0x0B] = Opcode("DEC BC", 1,8, {})
        op[0x0C] = Opcode("INC C", 1,4, {})
        op[0x0D] = Opcode("DEC C", 1,4, {})
        op[0x0E] = Opcode("DLD C, u8", 2,8, {})
        op[0x0F] = Opcode("RRCA", 1,4, {})

        op[0x10] = Opcode("STOP", 2,4, {})
        op[0x11] = Opcode("LD DE, u16", 3,12, {})
        op[0x12] = Opcode("LD (DE), A", 1,8, {})
        op[0x13] = Opcode("INC DE", 1,8, {})
        op[0x14] = Opcode("INC D", 1,4, {})
        op[0x15] = Opcode("DEC D", 1,4, {})
        op[0x16] = Opcode("LD D, u8", 2,8, {})
        op[0x17] = Opcode("RLA", 1,4, {})
        op[0x18] = Opcode("JR i8", 2,12, {})
        op[0x19] = Opcode("", 1,8, {})
        op[0x1A] = Opcode("", 1,8, {})
        op[0x1B] = Opcode("", 1,8, {})
        op[0x1C] = Opcode("", 1,4, {})
        op[0x1D] = Opcode("", 1,4, {})
        op[0x1E] = Opcode("", 2,8, {})
        op[0x1F] = Opcode("", 1,4, {})

    }
}

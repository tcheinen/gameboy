package cpu

data class Opcode(val name: String, val length: Int, val time: Int, val operation: (Cpu) -> Unit)


@ExperimentalUnsignedTypes
class Opcodes {

    companion object {
        val op: Array<Opcode> = arrayOf()
    }

    init {
        op[0x0] = Opcode("NOP", 1, 4) {cpu: Cpu -> }
        op[0x1] = Opcode("LD BC,u16", 3, 12) {cpu: Cpu -> cpu.ld_r16_u16(Register.BC)}
        op[0x2] = Opcode("LD (BC),A", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.BC, Register.A)}
        op[0x3] = Opcode("INC BC", 1, 8) {cpu: Cpu -> cpu.inc_r16(Register.BC)}
        op[0x4] = Opcode("INC B", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.B)}
        op[0x5] = Opcode("DEC B", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.B)}
        op[0x6] = Opcode("LD B,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.B)}
        op[0x7] = Opcode("RLCA", 1, 4) {cpu: Cpu -> cpu.rlc(Register.A)}
        op[0x8] = Opcode("LD (u16),SP", 3, 20) {cpu: Cpu -> }
        op[0x9] = Opcode("ADD HL,BC", 1, 8) {cpu: Cpu -> }
        op[0xA] = Opcode("LD A,(BC)", 1, 8) {cpu: Cpu -> }
        op[0xB] = Opcode("DEC BC", 1, 8) {cpu: Cpu -> cpu.dec_r16(Register.BC)}
        op[0xC] = Opcode("INC C", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.C)}
        op[0xD] = Opcode("DEC C", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.C)}
        op[0xE] = Opcode("LD C,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.C)}
        op[0xF] = Opcode("RRCA", 1, 4) {cpu: Cpu -> }

        op[0x10] = Opcode("STOP", 2, 4) {cpu: Cpu -> }
        op[0x11] = Opcode("LD DE,u16", 3, 12) {cpu: Cpu -> cpu.ld_r16_u16(Register.DE)}
        op[0x12] = Opcode("LD (DE),A", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.DE, Register.A)}
        op[0x13] = Opcode("INC DE", 1, 8) {cpu: Cpu -> cpu.inc_r16(Register.DE)}
        op[0x14] = Opcode("INC D", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.D)}
        op[0x15] = Opcode("DEC D", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.D)}
        op[0x16] = Opcode("LD D,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.D)}
        op[0x17] = Opcode("RLA", 1, 4) {cpu: Cpu -> }
        op[0x18] = Opcode("JR i8", 2, 12) {cpu: Cpu -> }
        op[0x19] = Opcode("ADD HL,DE", 1, 8) {cpu: Cpu -> }
        op[0x1A] = Opcode("LD A,(DE)", 1, 8) {cpu: Cpu -> }
        op[0x1B] = Opcode("DEC DE", 1, 8) {cpu: Cpu -> cpu.dec_r16(Register.DE)}
        op[0x1C] = Opcode("INC E", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.E)}
        op[0x1D] = Opcode("DEC E", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.E)}
        op[0x1E] = Opcode("LD E,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.E)}
        op[0x1F] = Opcode("RRA", 1, 4) {cpu: Cpu -> }

        op[0x20] = Opcode("JR NZ,i8", 2, 12) {cpu: Cpu -> }
        op[0x21] = Opcode("LD HL,u16", 3, 12) {cpu: Cpu -> cpu.ld_r16_u16(Register.HL)}
        op[0x22] = Opcode("LD (HL+),A", 1, 8) {cpu: Cpu -> }
        op[0x23] = Opcode("INC HL", 1, 8) {cpu: Cpu -> cpu.inc_r16(Register.HL)}
        op[0x24] = Opcode("INC H", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.H)}
        op[0x25] = Opcode("DEC H", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.H)}
        op[0x26] = Opcode("LD H,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.H)}
        op[0x27] = Opcode("DAA", 1, 4) {cpu: Cpu -> }
        op[0x28] = Opcode("JR Z,i8", 2, 12) {cpu: Cpu -> }
        op[0x29] = Opcode("ADD HL,HL", 1, 8) {cpu: Cpu -> }
        op[0x2A] = Opcode("LD A,(HL+)", 1, 8) {cpu: Cpu -> }
        op[0x2B] = Opcode("DEC HL", 1, 8) {cpu: Cpu -> cpu.dec_r16(Register.HL)}
        op[0x2C] = Opcode("INC L", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.L)}
        op[0x2D] = Opcode("DEC L", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.L)}
        op[0x2E] = Opcode("LD L,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.L)}
        op[0x2F] = Opcode("CPL", 1, 4) {cpu: Cpu -> }

        op[0x30] = Opcode("JR NC,i8", 2, 12) {cpu: Cpu -> }
        op[0x31] = Opcode("LD SP,u16", 3, 12) {cpu: Cpu -> cpu.ld_r16_u16(Register.SP)}
        op[0x32] = Opcode("LD (HL-),A", 1, 8) {cpu: Cpu -> }
        op[0x33] = Opcode("INC SP", 1, 8) {cpu: Cpu -> cpu.inc_r16(Register.SP)}
        op[0x34] = Opcode("INC (HL)", 1, 12) {cpu: Cpu -> }
        op[0x35] = Opcode("DEC (HL)", 1, 12) {cpu: Cpu -> }
        op[0x36] = Opcode("LD (HL),u8", 2, 12) {cpu: Cpu -> }
        op[0x37] = Opcode("SCF", 1, 4) {cpu: Cpu -> }
        op[0x38] = Opcode("JR C,i8", 2, 12) {cpu: Cpu -> }
        op[0x39] = Opcode("ADD HL,SP", 1, 8) {cpu: Cpu -> }
        op[0x3A] = Opcode("LD A,(HL-)", 1, 8) {cpu: Cpu -> }
        op[0x3B] = Opcode("DEC SP", 1, 8) {cpu: Cpu -> cpu.dec_r16(Register.SP)}
        op[0x3C] = Opcode("INC A", 1, 4) {cpu: Cpu -> cpu.inc_r8(Register.A)}
        op[0x3D] = Opcode("DEC A", 1, 4) {cpu: Cpu -> cpu.dec_r8(Register.A)}
        op[0x3E] = Opcode("LD A,u8", 2, 8) {cpu: Cpu -> cpu.ld_r8_u8(Register.A)}
        op[0x3F] = Opcode("CCF", 1, 4) {cpu: Cpu -> }

        op[0x40] = Opcode("LD B,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.B)}
        op[0x41] = Opcode("LD B,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.C)}
        op[0x42] = Opcode("LD B,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.D)}
        op[0x43] = Opcode("LD B,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.E)}
        op[0x44] = Opcode("LD B,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.H)}
        op[0x45] = Opcode("LD B,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.L)}
        op[0x46] = Opcode("LD B,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x47] = Opcode("LD B,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.B, Register.A)}
        op[0x48] = Opcode("LD C,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.B)}
        op[0x49] = Opcode("LD C,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.C)}
        op[0x4A] = Opcode("LD C,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.D)}
        op[0x4B] = Opcode("LD C,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.E)}
        op[0x4C] = Opcode("LD C,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.H)}
        op[0x4D] = Opcode("LD C,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.L)}
        op[0x4E] = Opcode("LD C,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x4F] = Opcode("LD C,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.C, Register.A)}

        op[0x50] = Opcode("LD D,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.B)}
        op[0x51] = Opcode("LD D,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.C)}
        op[0x52] = Opcode("LD D,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.D)}
        op[0x53] = Opcode("LD D,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.E)}
        op[0x54] = Opcode("LD D,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.H)}
        op[0x55] = Opcode("LD D,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.L)}
        op[0x56] = Opcode("LD D,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x57] = Opcode("LD D,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.D, Register.A)}
        op[0x58] = Opcode("LD E,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.B)}
        op[0x59] = Opcode("LD E,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.C)}
        op[0x5A] = Opcode("LD E,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.D)}
        op[0x5B] = Opcode("LD E,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.E)}
        op[0x5C] = Opcode("LD E,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.H)}
        op[0x5D] = Opcode("LD E,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.L)}
        op[0x5E] = Opcode("LD E,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x5F] = Opcode("LD E,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.E, Register.A)}

        op[0x60] = Opcode("LD H,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.B)}
        op[0x61] = Opcode("LD H,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.C)}
        op[0x62] = Opcode("LD H,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.D)}
        op[0x63] = Opcode("LD H,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.E)}
        op[0x64] = Opcode("LD H,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.H)}
        op[0x65] = Opcode("LD H,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.L)}
        op[0x66] = Opcode("LD H,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x67] = Opcode("LD H,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.H, Register.A)}
        op[0x68] = Opcode("LD L,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.B)}
        op[0x69] = Opcode("LD L,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.C)}
        op[0x6A] = Opcode("LD L,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.D)}
        op[0x6B] = Opcode("LD L,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.E)}
        op[0x6C] = Opcode("LD L,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.H)}
        op[0x6D] = Opcode("LD L,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.L)}
        op[0x6E] = Opcode("LD L,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x6F] = Opcode("LD L,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.L, Register.A)}

        op[0x70] = Opcode("LD (HL),B", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.B)}
        op[0x71] = Opcode("LD (HL),C", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.C)}
        op[0x72] = Opcode("LD (HL),D", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.D)}
        op[0x73] = Opcode("LD (HL),E", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.E)}
        op[0x74] = Opcode("LD (HL),H", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.H)}
        op[0x75] = Opcode("LD (HL),L", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.L)}
        op[0x76] = Opcode("HALT", 1, 4) {cpu: Cpu -> }
        op[0x77] = Opcode("LD (HL),A", 1, 8) {cpu: Cpu -> cpu.ld_r16_r8(Register.HL, Register.A)}
        op[0x78] = Opcode("LD A,B", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.B)}
        op[0x79] = Opcode("LD A,C", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.C)}
        op[0x7A] = Opcode("LD A,D", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.D)}
        op[0x7B] = Opcode("LD A,E", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.E)}
        op[0x7C] = Opcode("LD A,H", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.H)}
        op[0x7D] = Opcode("LD A,L", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.L)}
        op[0x7E] = Opcode("LD A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x7F] = Opcode("LD A,A", 1, 4) {cpu: Cpu -> cpu.ld_r8_r8(Register.A, Register.A)}

        op[0x80] = Opcode("ADD A,B", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.B)}
        op[0x81] = Opcode("ADD A,C", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.C)}
        op[0x82] = Opcode("ADD A,D", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.D)}
        op[0x83] = Opcode("ADD A,E", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.E)}
        op[0x84] = Opcode("ADD A,H", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.H)}
        op[0x85] = Opcode("ADD A,L", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.L)}
        op[0x86] = Opcode("ADD A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x87] = Opcode("ADD A,A", 1, 4) {cpu: Cpu -> cpu.add_a_r8(Register.A)}
        op[0x88] = Opcode("ADC A,B", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.B)}
        op[0x89] = Opcode("ADC A,C", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.C)}
        op[0x8A] = Opcode("ADC A,D", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.D)}
        op[0x8B] = Opcode("ADC A,E", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.E)}
        op[0x8C] = Opcode("ADC A,H", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.H)}
        op[0x8D] = Opcode("ADC A,L", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.L)}
        op[0x8E] = Opcode("ADC A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x8F] = Opcode("ADC A,A", 1, 4) {cpu: Cpu -> cpu.addc_a_r8(Register.A)}

        op[0x90] = Opcode("SUB A,B", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.B)}
        op[0x91] = Opcode("SUB A,C", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.C)}
        op[0x92] = Opcode("SUB A,D", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.D)}
        op[0x93] = Opcode("SUB A,E", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.E)}
        op[0x94] = Opcode("SUB A,H", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.H)}
        op[0x95] = Opcode("SUB A,L", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.L)}
        op[0x96] = Opcode("SUB A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x97] = Opcode("SUB A,A", 1, 4) {cpu: Cpu -> cpu.sub_a_r8(Register.A)}
        op[0x98] = Opcode("SBC A,B", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.B)}
        op[0x99] = Opcode("SBC A,C", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.C)}
        op[0x9A] = Opcode("SBC A,D", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.D)}
        op[0x9B] = Opcode("SBC A,E", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.E)}
        op[0x9C] = Opcode("SBC A,H", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.H)}
        op[0x9D] = Opcode("SBC A,L", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.L)}
        op[0x9E] = Opcode("SBC A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0x9F] = Opcode("SBC A,A", 1, 4) {cpu: Cpu -> cpu.subc_a_r8(Register.A)}

        op[0xA0] = Opcode("AND A,B", 1, 4) {cpu: Cpu -> }
        op[0xA1] = Opcode("AND A,C", 1, 4) {cpu: Cpu -> }
        op[0xA2] = Opcode("AND A,D", 1, 4) {cpu: Cpu -> }
        op[0xA3] = Opcode("AND A,E", 1, 4) {cpu: Cpu -> }
        op[0xA4] = Opcode("AND A,H", 1, 4) {cpu: Cpu -> }
        op[0xA5] = Opcode("AND A,L", 1, 4) {cpu: Cpu -> }
        op[0xA6] = Opcode("AND A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0xA7] = Opcode("AND A,A", 1, 4) {cpu: Cpu -> }
        op[0xA8] = Opcode("XOR A,B", 1, 4) {cpu: Cpu -> }
        op[0xA9] = Opcode("XOR A,C", 1, 4) {cpu: Cpu -> }
        op[0xAA] = Opcode("XOR A,D", 1, 4) {cpu: Cpu -> }
        op[0xAB] = Opcode("XOR A,E", 1, 4) {cpu: Cpu -> }
        op[0xAC] = Opcode("XOR A,H", 1, 4) {cpu: Cpu -> }
        op[0xAD] = Opcode("XOR A,L", 1, 4) {cpu: Cpu -> }
        op[0xAE] = Opcode("XOR A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0xAF] = Opcode("XOR A,A", 1, 4) {cpu: Cpu -> }

        op[0xB0] = Opcode("OR A,B", 1, 4) {cpu: Cpu -> }
        op[0xB1] = Opcode("OR A,C", 1, 4) {cpu: Cpu -> }
        op[0xB2] = Opcode("OR A,D", 1, 4) {cpu: Cpu -> }
        op[0xB3] = Opcode("OR A,E", 1, 4) {cpu: Cpu -> }
        op[0xB4] = Opcode("OR A,H", 1, 4) {cpu: Cpu -> }
        op[0xB5] = Opcode("OR A,L", 1, 4) {cpu: Cpu -> }
        op[0xB6] = Opcode("OR A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0xB7] = Opcode("OR A,A", 1, 4) {cpu: Cpu -> }
        op[0xB8] = Opcode("CP A,B", 1, 4) {cpu: Cpu -> }
        op[0xB9] = Opcode("CP A,C", 1, 4) {cpu: Cpu -> }
        op[0xBA] = Opcode("CP A,D", 1, 4) {cpu: Cpu -> }
        op[0xBB] = Opcode("CP A,E", 1, 4) {cpu: Cpu -> }
        op[0xBC] = Opcode("CP A,H", 1, 4) {cpu: Cpu -> }
        op[0xBD] = Opcode("CP A,L", 1, 4) {cpu: Cpu -> }
        op[0xBE] = Opcode("CP A,(HL)", 1, 8) {cpu: Cpu -> }
        op[0xBF] = Opcode("CP A,A", 1, 4) {cpu: Cpu -> }

        op[0xC0] = Opcode("RET NZ", 1, 20) {cpu: Cpu -> }
        op[0xC1] = Opcode("POP BC", 1, 12) {cpu: Cpu -> }
        op[0xC2] = Opcode("JP NZ,u16", 3, 16) {cpu: Cpu -> }
        op[0xC3] = Opcode("JP u16", 3, 16) {cpu: Cpu -> }
        op[0xC4] = Opcode("CALL NZ,u16", 3, 24) {cpu: Cpu -> }
        op[0xC5] = Opcode("PUSH BC", 1, 16) {cpu: Cpu -> }
        op[0xC6] = Opcode("ADD A,u8", 2, 8) {cpu: Cpu -> }
        op[0xC7] = Opcode("RST 00h", 1, 16) {cpu: Cpu -> }
        op[0xC8] = Opcode("RET Z", 1, 20) {cpu: Cpu -> }
        op[0xC9] = Opcode("RET", 1, 16) {cpu: Cpu -> }
        op[0xCA] = Opcode("JP Z,u16", 3, 16) {cpu: Cpu -> }
        op[0xCB] = Opcode("PREFIX CB", 1, 4) {cpu: Cpu -> }
        op[0xCC] = Opcode("CALL Z,u16", 3, 24) {cpu: Cpu -> }
        op[0xCD] = Opcode("CALL u16", 3, 24) {cpu: Cpu -> }
        op[0xCE] = Opcode("ADC A,u8", 2, 8) {cpu: Cpu -> }
        op[0xCF] = Opcode("RST 08h", 1, 16) {cpu: Cpu -> }

        op[0xD0] = Opcode("RET NC", 1, 20) {cpu: Cpu -> }
        op[0xD1] = Opcode("POP DE", 1, 12) {cpu: Cpu -> }
        op[0xD2] = Opcode("JP NC,u16", 3, 16) {cpu: Cpu -> }
        op[0xD3] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xD4] = Opcode("CALL NC,u16", 3, 24) {cpu: Cpu -> }
        op[0xD5] = Opcode("PUSH DE", 1, 16) {cpu: Cpu -> }
        op[0xD6] = Opcode("SUB A,u8", 2, 8) {cpu: Cpu -> }
        op[0xD7] = Opcode("RST 10h", 1, 16) {cpu: Cpu -> }
        op[0xD8] = Opcode("RET C", 1, 20) {cpu: Cpu -> }
        op[0xD9] = Opcode("RETI", 1, 16) {cpu: Cpu -> }
        op[0xDA] = Opcode("JP C,u16", 3, 16) {cpu: Cpu -> }
        op[0xDB] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xDC] = Opcode("CALL C,u16", 3, 24) {cpu: Cpu -> }
        op[0xDD] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xDE] = Opcode("SBC A,u8", 2, 8) {cpu: Cpu -> }
        op[0xDF] = Opcode("RST 18h", 1, 16) {cpu: Cpu -> }

        op[0xE0] = Opcode("LD (FF00+u8),A", 2, 12) {cpu: Cpu -> }
        op[0xE1] = Opcode("POP HL", 1, 12) {cpu: Cpu -> }
        op[0xE2] = Opcode("LD (FF00+C),A", 1, 8) {cpu: Cpu -> }
        op[0xE3] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xE4] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xE5] = Opcode("PUSH HL", 1, 16) {cpu: Cpu -> }
        op[0xE6] = Opcode("AND A,u8", 2, 8) {cpu: Cpu -> }
        op[0xE7] = Opcode("RST 20h", 1, 16) {cpu: Cpu -> }
        op[0xE8] = Opcode("ADD SP,i8", 2, 16) {cpu: Cpu -> }
        op[0xE9] = Opcode("JP HL", 1, 4) {cpu: Cpu -> }
        op[0xEA] = Opcode("LD (u16),A", 3, 16) {cpu: Cpu -> }
        op[0xEB] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xEC] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xED] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xEE] = Opcode("XOR A,u8", 2, 8) {cpu: Cpu -> }
        op[0xEF] = Opcode("RST 28h", 1, 16) {cpu: Cpu -> }

        op[0xF0] = Opcode("LD A,(FF00+u8)", 2, 12) {cpu: Cpu -> }
        op[0xF1] = Opcode("POP AF", 1, 12) {cpu: Cpu -> }
        op[0xF2] = Opcode("LD A,(FF00+C)", 1, 8) {cpu: Cpu -> }
        op[0xF3] = Opcode("DI", 1, 4) {cpu: Cpu -> }
        op[0xF4] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xF5] = Opcode("PUSH AF", 1, 16) {cpu: Cpu -> }
        op[0xF6] = Opcode("OR A,u8", 2, 8) {cpu: Cpu -> }
        op[0xF7] = Opcode("RST 30h", 1, 16) {cpu: Cpu -> }
        op[0xF8] = Opcode("LD HL,SP+i8", 2, 12) {cpu: Cpu -> }
        op[0xF9] = Opcode("LD SP,HL", 1, 8) {cpu: Cpu -> }
        op[0xFA] = Opcode("LD A,(u16)", 3, 16) {cpu: Cpu -> }
        op[0xFB] = Opcode("EI", 1, 4) {cpu: Cpu -> }
        op[0xFC] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xFD] = Opcode("UNUSED", 1, 0) {cpu: Cpu -> }
        op[0xFE] = Opcode("CP A,u8", 2, 8) {cpu: Cpu -> }
        op[0xFF] = Opcode("RST 38h", 1, 16) {cpu: Cpu -> }
    }
}

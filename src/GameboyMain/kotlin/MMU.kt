data class MMU(val rom0: ByteArray = ByteArray(16384),
               val rom1: ByteArray = ByteArray(16384),
               val vram: ByteArray = ByteArray(8192),
               val wram1: ByteArray = ByteArray(4096),
               val wram2: ByteArray = ByteArray(4096),
               val oam: ByteArray = ByteArray(160), // is this the right size?
               val hram: ByteArray = ByteArray(128)
) {
    /**
     * Write 8-bit [value] to the memory at address [address]
     */
    fun writeRAM(address: UShort, value: UByte) {
        when (address.toInt()) {
            in 0x0000..0x3FFF -> {
            } // rom bank 0
            in 0x4000..0x7FFF -> {
            } // rom bank 1
            in 0x8000..0x9FFF -> {
            } // vram
            in 0xA000..0xBFFF -> {
            } // external ram
            in 0xC000..0xCFFF -> {
            } // wram 0
            in 0xD000..0xDFFF -> {
            } // wram 1
            in 0xFE00..0xFE9F -> {
            } // Sprite Table (OAM)
            in 0xFF00..0xFF7F -> {
            } // IO
            in 0xFF80..0xFFFE -> {
            } // HRAM
        }
    }
}
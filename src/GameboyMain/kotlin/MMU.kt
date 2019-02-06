data class MMU(val rom0: ByteArray = ByteArray(16384),
               val rom1: ByteArray = ByteArray(16384),
               val vram: ByteArray = ByteArray(8192),
               val wram1: ByteArray = ByteArray(4096),
               val wram2: ByteArray = ByteArray(4096),
               val oam: ByteArray = ByteArray(160), // is this the right size?
               val hram: ByteArray = ByteArray(128)
)
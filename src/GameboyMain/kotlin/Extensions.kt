infix fun UShort.shr(i: Int): UShort = (this.toInt() shr i).toUShort()
infix fun UShort.shl(i: Int): UShort = (this.toInt() shl i).toUShort()

infix fun UByte.shr(i: Int): UByte = (this.toInt() shr i).toUByte()
infix fun UByte.shl(i: Int): UByte = (this.toInt() shl i).toUByte()
infix fun UByte.combine(i: UByte): UShort = ((this.toInt() shl 8) or i.toInt()).toUShort()

val UShort.high: UByte get() = (this shr 8).toUByte()
val UShort.low: UByte get() = (this and 0xFFu).toUByte()



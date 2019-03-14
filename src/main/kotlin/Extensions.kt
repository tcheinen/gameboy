import java.util.*

infix fun UShort.shr(i: Int): UShort = (this.toInt() shr i).toUShort()
infix fun UShort.shl(i: Int): UShort = (this.toInt() shl i).toUShort()

infix fun UByte.shr(i: Int): UByte = (this.toInt() shr i).toUByte()
infix fun UByte.shl(i: Int): UByte = (this.toInt() shl i).toUByte()
infix fun UByte.combine(i: UByte): UShort = ((this.toInt() shl 8) or i.toInt()).toUShort()

val UShort.high: UByte get() = (this shr 8).toUByte()
val UShort.low: UByte get() = (this and 0xFFu).toUByte()

fun Boolean.toUByte(): UByte = if(this) 1u else 0u
// TODO make sure this works
val BitSet.byte: UByte get() {
    var out: UByte = 0u
    for(i in this.size()..0) {
        out shl 1
        if(this[i]) out++
    }
    return out
}

// TODO make sure this works
val UByte.bitset: BitSet get() {
    val out = BitSet(8)
    toString(2).forEachIndexed { i, e ->
        if(e == '1') out.set(7-i,true)
    }
    return out
}
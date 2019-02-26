package com.lanayru.app

import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/8
 *
 **/
class NumberTest {

    @Test
    fun testScale() {
        val s = 5.2 / 100
        println(s)
        val v = BigDecimal(s)
        println(v.toFloat())
        val r = v.setScale(2, RoundingMode.HALF_DOWN).toFloat()
        println(r)
    }

    fun intToBytes(v: Int, len: Int = 1): ByteArray {
        if (v < 0xff) {
            return byteArrayOf(v.toByte())
        }
        val r = ByteArray(len)
        var d = v
        for (i in len - 1 downTo 0) {
            r[i] = (d and 0xff).toByte()
            d = d shr 8
        }
        return r
    }

    fun bytesToInt(v: ByteArray): Int {
        var r = 0

        var t = 0
        for (i in v.size - 1 downTo 0) {
            r  += (v[i].toInt() and 0xff) shl (8 * t)
            ++ t
        }

        return r
    }

    fun intToByteArray(v: Int): ByteArray {
        return if (v > 255) {
            val list = ArrayList<Byte>()
            var d = v
            while (d > 0) {
                val dd = d and 0xFF
                val bb = dd.toByte()
                list.add(bb)
                d = d shr 8
            }

            val size = list.size

            ByteArray(size) {
                list[size - it - 1]
            }

        } else {
            byteArrayOf(v.toByte())
        }
    }

    fun logByteInt(v: Int) {
        val b = v.toByte()
        println("${v} to byte is ${b} And toInt ${b.toInt() and 0xFF}")
    }

    @Test
    fun testInt2B() {
//        logByteInt(127)
//        logByteInt(128)
//        logByteInt(255)
//        logByteInt(256)
//        logByteInt(1000)

        val a = intToBytes(1000, 2)

        println(getByteArrayString(a))

        println(bytesToInt(a))

//        println(getByteArrayString(intToBytes(1000, 2)))
//        println(getByteArrayString(intToByteArray(1000)))
//        println(getByteArrayString(intToByteArray(0xffffee)))
//
//        val s = ByteBuffer.allocate(8).putLong(java.lang.Double.doubleToLongBits(255.toDouble())).array()
//        print(getByteArrayString(s))
    }

    fun getByteArrayString(v: ByteArray): String {
        val sb = StringBuilder("[")
        v.forEachIndexed {i, d ->
            if (i > 0) {
                sb.append(",")
            }
            sb.append(d)
        }
        sb.append("]")
        return sb.toString()
    }

    fun logByte(v: Int) {
        println("to string ${v.toString(16)}")
        println("${v} to byte is ${v.toByte()}")

        println("${v} to byte array is ${getByteArrayString(v.toString().toByteArray())}")
    }

    fun logAnd(v: Int) {
        println("${v and 0xFF00} ${v and 0x00FF}")
    }

    @Test
    fun testByte() {
        logByte(100)
        logByte(0xff)
        logByte(300)
        logByte(1000)

       logAnd(300)
    }

    @Test
    fun testAnd() {

    }

    /**
     * copy from [java.util.HashMap]
     */
    private fun tableSizeFor(cap: Int): Int {
        val MAXIMUM_CAPACITY = 100
        var n = cap - 1
        n = n or n.ushr(1)
        n = n or n.ushr(2)
        n = n or n.ushr(4)
        n = n or n.ushr(8)
        n = n or n.ushr(16)
        return if (n < 0) 1 else if (n >= MAXIMUM_CAPACITY) MAXIMUM_CAPACITY else n + 1
    }

    @Test
    fun testHashMapThreshold() {
        println(tableSizeFor(16))
        println(tableSizeFor(3))
        println(tableSizeFor(9))
    }
}
package com.lanayru.app.num

import org.junit.Test

class NumberTest {

    fun reverse(v: Int): Int {
        if (v < 10 && v > -10) {
            return v
        }
        // Int 最大位数
        val maxSize = 12
        val a = Array<Int>(maxSize){-1}
        var n = v
        var size = 0
        while (n > 0) {
            a[size] = n % 10
            n /= 10
            ++size
        }

        var newV = 0

        for (i in 0 until size) {
            val ai = (Math.pow(10.0, ((size - i -1).toDouble())) * a[i]).toInt()
            newV += ai
        }

        return newV
    }

    @Test
    fun testReverse() {
        println("origin: 123 reverse: ${reverse(123)}")
        println("origin: 7890 reverse: ${reverse(7890)}")
    }
}
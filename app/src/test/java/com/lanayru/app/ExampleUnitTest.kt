package com.lanayru.app

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun testToInt() {
        println(15.6.toInt())
    }

    @Test
    fun testForIn() {
        for (i in 0..2) {
            println(i)
        }
    }

    @Test
    fun testMath() {
        println(1/2)
        println(3/2)
        println(3/2f)
        println(Math.ceil(3/2.0).toInt())

    }

    @Test
    fun testEquals() {
        val s: String? = null
        val s1: String? = null

        println(Objects.equals(s, s1))
    }
}

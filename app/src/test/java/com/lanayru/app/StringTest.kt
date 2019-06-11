package com.lanayru.app

import org.junit.Test

class StringTest {

    @Test
    fun indexof() {
        val s="/index"
        val s1="/index/1"
        println(
                s.indexOf("/", 1)
        )
        println(
                s1.indexOf("/", 1)
        )

        println(s.substring(1, s.indexOf("/", 1)))
        println(s1.substring(1, s.indexOf("/", 1)))
    }

    @Test
    fun testSplit() {
        val s = "a,b,c,,"
        val array = s.split(",")
        println("array: $array length ${array.size}")
    }

}
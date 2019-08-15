package com.lanayru.app.thread

import org.junit.Test

/**
 * @author 郑齐
 * @since 2019-07-16
 * @version V1.0
 *
 */
class TraceTest {

    fun a() {
        Thread.dumpStack()
        Throwable("I am a throwable").printStackTrace(System.out)
    }

    fun aa() {
        a()
    }

    @Test
    fun printTrace() {
        aa()

        println("after exception end....")
        for (i in 0.. 10) {
            println("i is $i")
        }
    }
}
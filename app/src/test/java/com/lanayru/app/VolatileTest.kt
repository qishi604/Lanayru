package com.lanayru.app

import org.junit.Test
import kotlin.concurrent.thread

class VolatileTest {

//        @Volatile var a = 0
    var a = 0
    var flag = false


    private fun write() {
        println("write in thread ${Thread.currentThread().name}")
        a += 1

        flag = true

        Thread.sleep(200)

        println("write end")
    }

    private fun read() {
//        println("i is $a")
        println("read in thread ${Thread.currentThread().name}")
        if (flag) {
//            val i = a
            println("i is $a")
        }
    }

    private fun execute(f: () -> Unit) {
        thread(start=true) {
            f()
        }
    }

    @Test
    fun testWR() {
        execute {
            write()
        }

        execute {
            write()
        }

        execute {
            read()
        }

        execute { read() }

        Thread.sleep(1000)
    }
}
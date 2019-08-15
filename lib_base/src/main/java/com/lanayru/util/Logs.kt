package com.lanayru.util

/**
 *
 * @author seven
 * @since 2018/7/23
 * @version V1.0
 */
object Logs {

    fun i(s: String) = println(s)

    fun e(s: String) = println(s)

    fun duration(tag: String = "", m: ()->Unit) {
        val last = System.currentTimeMillis()
        m()
        i( "$tag duration: ${System.currentTimeMillis() - last}" )
    }
}
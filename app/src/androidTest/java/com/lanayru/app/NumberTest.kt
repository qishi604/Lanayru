package com.lanayru.app

import android.net.Uri
import android.text.TextUtils
import org.junit.Test

/**
 * @author 郑齐
 * @since 2019/4/17
 * @version V1.0
 *
 */

class NumberTest1 {

    fun isNumber(s: String) {
        println("$s is number ${TextUtils.isDigitsOnly(s)}")
    }

    @Test
    fun testIsNumber() {
        isNumber("abc")
        isNumber("123")
        isNumber("2d3")

        val s = null
        val uri = Uri.parse("tel : ${s}")
        println(uri)
    }
}
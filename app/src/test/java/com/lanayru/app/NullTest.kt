package com.lanayru.app

import org.junit.Test

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/5
 *
 **/
class NullTest {

    /**
     * [help](http://www.baidu.com)
     */
    @Test
    fun testNull() {
        var s : String? = null

//        val s1 = s as Long // null cannot be cast to non-null type kotlin.Long
        val s1 = s as? Long? // null pass
//        val s1 = s as Long? // pass

        print(s1)
    }
}
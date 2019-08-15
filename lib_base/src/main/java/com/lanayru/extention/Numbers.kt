package com.lanayru.extention

import java.math.BigDecimal
import java.math.RoundingMode

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/8
 *
 **/

fun Number.scale(i: Int): Float {
    val v = BigDecimal(this.toDouble())
    v.setScale(i, RoundingMode.HALF_UP)
    return v.toFloat()
}
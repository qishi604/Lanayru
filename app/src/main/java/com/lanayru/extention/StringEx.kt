package com.lanayru.extention

import java.text.DecimalFormat

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/11
 *
 **/

private val format = DecimalFormat.getInstance().apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 2
}

fun Number.moneyFormat() = format.format(this)

package com.lanayru.extention

import android.content.res.Resources

/**
 *
 * @author seven
 * @since 2018/7/20
 * @version V1.0
 */

private val displayMetrics = Resources.getSystem().displayMetrics

val Int.dp :Int get() = Math.ceil(displayMetrics.density * this.toDouble()).toInt()

val Int.sp :Int get() = Math.ceil(displayMetrics.scaledDensity * this.toDouble()).toInt()

val Float.dp :Int get() = Math.ceil(displayMetrics.density * this.toDouble()).toInt()

val Double.dp :Int get() = Math.ceil(displayMetrics.density * this).toInt()

val screenWidth = displayMetrics.widthPixels

val screenHeight = displayMetrics.heightPixels
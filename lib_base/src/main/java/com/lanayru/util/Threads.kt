package com.lanayru.util

import android.os.AsyncTask

/**
 *
 * @author seven
 * @since 2018/8/3
 * @version V1.0
 */

fun execute(r: ()->Unit) {
    AsyncTask.THREAD_POOL_EXECUTOR.execute(r)
}
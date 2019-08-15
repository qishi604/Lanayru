package com.lanayru.util

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

object SimpleLeakWatcher {

    private val mQueue = ReferenceQueue<Any>()

    fun watch(o: Any) {
        val ref = WeakReference(o, mQueue)
    }

    fun showQueue() {
        mQueue.poll()
    }
}
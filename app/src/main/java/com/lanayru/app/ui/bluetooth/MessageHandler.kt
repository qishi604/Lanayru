package com.lanayru.app.ui.bluetooth

import android.os.Handler
import android.os.Message

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/19
 *
 **/
class MessageHandler(l: (msg: String)->Unit) : Handler() {

    private val onMessage : (msg: String)->Unit = l

    override fun handleMessage(msg: Message?) {
        super.handleMessage(msg)
        msg?.let {
            onMessage(msg.obj.toString())
        }
    }

    fun postMessage(msg: String) {
        Message.obtain(this).apply {
            obj = msg
        }.sendToTarget()
    }
}
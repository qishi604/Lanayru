package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.model.Event
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * @author 郑齐
 * @since 2019/5/13
 * @version V1.0
 *
 */
class ImageDetailActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("send notify") {
                onClick {
                    send()
                }
            }
        }
    }

    private fun send() {
        val ev = Event()
        ev.data = "Hello~"
        MainActivity.sMainActivity!!.onEvent(ev)
    }

}


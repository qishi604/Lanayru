package com.lanayru.model

import org.greenrobot.eventbus.EventBus

/**
 * @author 郑齐
 * @since 2019/5/13
 * @version V1.0
 *
 */
class Event {
    var type: Int = 0
    var data: Any? = null

    fun post() {
        EventBus.getDefault().post(this)
    }
}
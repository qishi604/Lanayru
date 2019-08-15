package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.Logs

/**
 * @author seven
 * @since 2019/1/10
 * @version V1.0
 *
 * 测试隐藏方法能不能用
 *
 */
class AndroidPActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val cls = Class.forName("android.app.ActivityThread")
        val m = cls.getDeclaredMethod("currentApplication")

        val app = m.invoke(cls)

        Logs.i("app " + app)
        Logs.i("from activity $application")
    }
}
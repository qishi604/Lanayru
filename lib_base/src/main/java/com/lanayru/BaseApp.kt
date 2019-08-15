package com.lanayru

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.lanayru.library.BuildConfig

/**
 * @author 郑齐
 * @since 2019-07-31
 * @version V1.0
 *
 */
open class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        initRouter()
    }

    private fun initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }
}
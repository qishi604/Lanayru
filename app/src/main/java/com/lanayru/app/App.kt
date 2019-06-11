package com.lanayru.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.lanayru.util.HotFix
import kotlin.properties.Delegates

/**
 *
 * @author seven
 * @since 2018/7/24
 * @version V1.0
 */
class App : Application() {

    companion object {
        private var sInstance: App by Delegates.notNull()

        fun getApp() = sInstance
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        sInstance = this

        HotFix.patch(this)

        logDirs()
    }

    fun logDirs() {
        val info = applicationInfo

        println("sourceDir: ${info.sourceDir}  dataDir ${info.dataDir}")
    }

    /**
     * 内存剪裁，防止被系统强杀，可以根据不同的 level 做不同的内存释放
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {

        }
    }
}
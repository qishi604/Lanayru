package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.Logs
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * @author seven
 * @since 2019/2/26
 * @version V1.0
 *
 */
class RxJavaTestActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("just") {
                onClick {
                    testJust()
                }
            }
        }
    }

    private fun testJust() {
        Observable.just(getUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    logThread("subscribe")
                }
    }

    private fun getUrl(): String {
        Thread.sleep(4000)
        logThread("getUrl")
        return "http://www.baidu.com"
    }

    private fun logThread(s: String) {
        Logs.i("In thread ${Thread.currentThread()} ====== $s")
    }



}
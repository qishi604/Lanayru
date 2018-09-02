package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.app.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

/**
 *
 * @author seven
 * @since 2018/8/2
 * @version V1.0
 */
class ProcessActivity: BaseActivity() {
    override fun render(savedInstanceState: Bundle?) {
        val root = verticalLayout {

            button("sleep") {
                onClick {
                    sleepAWhile()
                }
            }

            button("toast") {
                onClick {
                    toast("hello")
                }
            }
        }

        setContentView(root)


    }

    private fun sleepAWhile() {
        Thread.sleep(20000)
    }
}
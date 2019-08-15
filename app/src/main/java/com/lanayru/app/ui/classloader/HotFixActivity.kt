package com.lanayru.app.ui.classloader

import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

/**
 *
 * 参考
 * https://blog.csdn.net/lmj623565791/article/details/49883661
 *
 * @author seven
 * @since 2018/7/30
 * @version V1.0
 */
class HotFixActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {

        val root = verticalLayout {

            button("show") {
                onClick {
                    showText()
                }
            }
        }

        setContentView(root)
    }

    private fun showText() {
        toast("bug")
    }
}
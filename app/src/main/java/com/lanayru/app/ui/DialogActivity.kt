package com.lanayru.app.ui

import android.app.AlertDialog
import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * 测试非栈顶的 Activity 弹出对话框是否会显示在 栈顶 Activity 之上
 * <p>结论是不会</p>
 *
 * @author 郑齐
 * @since 2019/5/8
 * @version V1.0
 *
 */
class DialogActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("showDialog") {
                onClick {
                    postDelayed({showDialogHello()}, 5000)
                }
            }

            button("start activity") {
                onClick {
                    startActivity<PlayActivity>()
                }
            }
        }
    }

    private fun showDialogHello() {
        AlertDialog.Builder(_this)
                .setTitle("Alert")
                .setMessage("Hello~")
                .show()
    }
}
package com.lanayru.app.ui

import android.os.Bundle
import android.os.Trace
import com.lanayru.app.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * This is a crash test logcat
 */
class CrashActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        Trace.beginSection("")

        linearLayout {
            button("Null pointer") {
                onClick {
                    throw NullPointerException("throw by button click")
                }
            }

            button("Null pointer in thread") {
                onClick {
                    Thread {

                        throw NullPointerException("throw by button click")

                    }.start()
                }
            }
        }
    }
}
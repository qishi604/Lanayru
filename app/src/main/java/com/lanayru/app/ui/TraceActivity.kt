package com.lanayru.app.ui

import android.os.Bundle
import android.os.Debug
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.Logs
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.File

/**
 * @author seven
 * @since 2019/2/8
 * @version V1.0
 *
 */
class TraceActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("GC") {
                onClick {
                    gc()
                }
            }

            button("sleep") {
                onClick {
                    writeFile()
                }
            }

            button("stack trace") {
                onClick {
                    printThreadStackTrace()
                }
            }
        }
    }

    private fun writeFile() {
        tracing {

            Logs.i("write start")

            val f = File(filesDir, "tmp")
            val bytes = ByteArray(1000*1000*10) {
                1
            }

            f.writeBytes(bytes)

            Logs.i("write finish")
        }
    }

    private fun gc() {
        tracing {
            for (i in 0..1000) {
                System.gc()
            }
        }
    }

    private fun tracing(f: () -> Unit) {
        Debug.startMethodTracingSampling("lanayru", 20 * 1000000, 100)

        f()

        Debug.stopMethodTracing()
    }

    private fun printThreadStackTrace() {
        val t = Thread.currentThread().stackTrace

        t.forEach {
            System.err.println("\t ${it.className}#${it.methodName}:${it.lineNumber}" )
        }
    }
}
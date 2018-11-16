package com.lanayru.app.ui.jni

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.Logs
import org.jetbrains.anko.imageView
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/25
 *
 **/
class JniDemoActivity: BaseActivity() {

    private lateinit var mTvInfo: TextView

    private lateinit var mIvSrc: ImageView
    private lateinit var mIvRed: ImageView
    private lateinit var mIvDst: ImageView
    private lateinit var mIvDst2: ImageView

    override fun render(savedInstanceState: Bundle?) {
        val root = scrollView {
            verticalLayout {

                mTvInfo = textView {
                    val a = 12
                    val b = 20
                    val s = Jnis.max(a, b)
                    text = "$a and $b max is $s"
                }

                mIvSrc = imageView {

                }
                mIvRed = imageView {

                }
                mIvDst = imageView {

                }
                mIvDst2 = imageView {

                }
            }
        }
        setContentView(root)

        logBm()

        renderRed()
        renderBm()
    }

    private fun logBm() {
        val bm = BitmapFactory.decodeResource(resources, R.drawable.zelda)
        val txt = Jnis.getBitmapInfo(bm)
        mTvInfo.append("\n")
        mTvInfo.append(txt)
    }

    private fun renderRed() {
        val src = BitmapFactory.decodeResource(resources, R.drawable.zelda)
        var dst = Bitmap.createBitmap(src)

        Logs.duration("to red") {
            Bitmaps.toRed(src, dst)
            mIvRed.setImageBitmap(dst)
        }
    }

    private fun renderBm() {
        val src = BitmapFactory.decodeResource(resources, R.drawable.zelda)
        var dst = Bitmap.createBitmap(src)

        if (src == dst) {
            dst = BitmapFactory.decodeResource(resources, R.drawable.zelda)
        }

        mIvSrc.setImageBitmap(src)

        Logs.i("java current millis: ${System.currentTimeMillis()}")

        Logs.duration("to gray") {
            Bitmaps.toGray(src, dst)
            mIvDst.setImageBitmap(dst)
        }


        Logs.duration("java gray") {
            mIvDst2.setImageBitmap(Bitmaps.gray1(src))
        }

    }
}
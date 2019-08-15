package com.lanayru.app.ui.anim

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.extention.moneyFormat
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/11
 *
 **/
class NumberAnimActivity : BaseActivity() {

    lateinit var mTvNum: TextView

    var num = 0f

    override fun render(savedInstanceState: Bundle?) {
        verticalLayout {
            gravity = Gravity.CENTER
            mTvNum = textView("10000") {
                gravity = Gravity.CENTER
                textSize = 32f
                textColor = Color.BLACK
                padding = 32.dp
                onClick {
                    anim()
                }
            }
        }
    }

    private var mAnim: ValueAnimator? =  null

    private fun anim() {
        if (null != mAnim && mAnim!!.isRunning) {
            mAnim!!.cancel()
        }

        val start = num
        val end = start + (Math.random() * 1000000).toFloat()
        mAnim = ValueAnimator.ofFloat(start, end)
                .apply {
                    duration = 600
                    addUpdateListener {
                        val v = it.animatedValue as Float
                        mTvNum.text = v.moneyFormat()
                    }
                }

        num = end

        mAnim!!.start()
    }
}
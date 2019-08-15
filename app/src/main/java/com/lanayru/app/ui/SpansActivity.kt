package com.lanayru.app.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.util.Spans
import com.lanayru.view.emoji.EmojiSpans
import org.jetbrains.anko.*

/**
 *
 * @author seven
 * @since 2018/8/7
 * @version V1.0
 */
class SpansActivity : BaseActivity() {

    private lateinit var mTvContent: TextView

    private lateinit var mTvContent1: TextView

    override fun render(savedInstanceState: Bundle?) {

        val root = verticalLayout() {

            mTvContent = textView() {
                textColor = Color.GRAY
                textSize = 18f
                padding = 16.dp


            }

            mTvContent1 = textView() {
                textColor = Color.GRAY
                textSize = 18f
                padding = 16.dp


            }
        }

        setContentView(root)
        setSpans()
    }

    private fun setSpans() {
        val s1 = Spans("This is an ")
        val s2 = Spans("Android", color = Color.RED, click = {
            toast("Android")
        })
        val s3 = Spans(" Demo", color = Color.GREEN)

        Spans.setSpans(mTvContent, s1, s2, s3)

        mTvContent1.text = EmojiSpans.getTestCharSequence()
    }
}
package com.lanayru.app.ui.emoji

import android.os.Bundle
import android.os.Debug
import android.os.Trace
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.Logs
import kotlinx.android.synthetic.main.activity_span_emoji.*

/**
 * @author 郑齐
 * @since 2019-08-02
 * @version V1.0
 *
 */
class SpanEmojiActivity: BaseActivity() {



    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_span_emoji)

        renderSpan()
    }

    private fun renderSpan() {
        btnSvg.setOnClickListener {
            Logs.duration {
                Trace.beginSection("svg-load")
                Debug.startMethodTracing()
                load(R.drawable.ic_emoji__1)
                Debug.stopMethodTracing()
                Trace.endSection()
            }
        }

        btnPng.setOnClickListener {
            Logs.duration {
                Trace.beginSection("png-load")
                Debug.startMethodTracing()
                load(R.drawable.div)
                Debug.stopMethodTracing()
                Trace.endSection()
            }
        }

    }

    private fun load(resId: Int) {
        val string = genString()
        val sb = SpannableString(string)
        val emojiDrawable = _this.getDrawable( resId).apply {
            setBounds(0, 0, intrinsicWidth * 5, intrinsicHeight * 5)
        }

        for (i in 0 until 10) {
            var imageSpan = ImageSpan(emojiDrawable)
            sb.setSpan(imageSpan, i, i+1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        tv_content.text = sb
    }

    private fun genString() : String {
        var s = StringBuilder()

        for (i in 0 until 100) {
            s.append('0')
        }
        return s.toString()
    }
}
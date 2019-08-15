package com.lanayru.util

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView

/**
 *
 * Helper class for generate span.
 *
 * @author seven
 * @since 2018/8/7
 * @version V1.0
 */
data class Spans(
        val s: String,
        val click: ((v: View?) -> Unit)? = null,
        var color: Int = Color.TRANSPARENT,
        var underline: Boolean = false
) {

    companion object {

        fun getSpans(vararg s: Spans): Spanned {
            val sb = SpannableStringBuilder()
            s.forEach {
                val text = it.s
                if (text.isNotEmpty()) {
                    when {
                        null != it.click -> {
                            val clickSpan = ClickSpan(it)
                            sb.append(text, clickSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }

                        Color.TRANSPARENT != it.color -> {
                            val colorSpan = ForegroundColorSpan(it.color)
                            sb.append(text, colorSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }

                        else -> {
                            sb.append(text)
                        }
                    }
                }
            }

            return sb
        }

        fun setSpans(tv: TextView, vararg s: Spans) {
            var hasClickSpan = false

            val sb = SpannableStringBuilder()
            s.forEach {
                val text = it.s
                if (text.isNotEmpty()) {
                    when {
                        null != it.click -> {
                            hasClickSpan = true
                            val clickSpan = ClickSpan(it)
                            sb.append(text, clickSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }

                        Color.TRANSPARENT != it.color -> {
                            val colorSpan = ForegroundColorSpan(it.color)
                            sb.append(text, colorSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }

                        else -> {
                            sb.append(text)
                        }
                    }
                }
            }

            if (hasClickSpan) {
                tv.movementMethod = LinkMovementMethod.getInstance()
            }

            tv.text = sb
        }
    }
}

class ClickSpan(s: Spans) : ClickableSpan() {
    private val span = s
    override fun onClick(widget: View?) {
        span.click?.invoke(widget)
    }

    override fun updateDrawState(ds: TextPaint?) {
        super.updateDrawState(ds)
        ds?.let {
            if (span.color != Color.TRANSPARENT) {
                it.color = span.color
            }
            it.isUnderlineText = span.underline
        }
    }


}

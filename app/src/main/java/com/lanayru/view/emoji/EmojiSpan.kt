package com.lanayru.view.emoji

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import com.blankj.utilcode.util.SizeUtils
import com.lanayru.app.App
import com.lanayru.app.R
import java.util.regex.Pattern

object EmojiSpan {

    val patten = Pattern.compile("\\[[^\\[\\]]\\]")

    fun getEmojiText(text: String) : CharSequence {
        var matcher = patten.matcher(text)
        if (matcher.find()) {

        }

        return ""
    }

    fun getTestCharSequence(): CharSequence {
        val source = "This is an android emoji test"

        val icon = App.getApp().getDrawable(R.drawable.dit)
        val spance = SizeUtils.dp2px(12f)
        icon.setBounds(spance, 0, icon.intrinsicWidth  + spance, icon.intrinsicHeight)

        val span = ImageSpan(icon, ImageSpan.ALIGN_BASELINE)

        var spannableString = SpannableString(source)
        spannableString.setSpan(span, source.length - 1, source.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableString
    }
}
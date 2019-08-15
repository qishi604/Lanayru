package com.lanayru.app.ui.emoji

import android.graphics.Typeface
import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.data.EmojiProvider
import com.lanayru.library.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_emoji.*

/**
 *
 * 🥶
 * ✊
 *
 * @author 郑齐
 * @since 2019-07-11
 * @version V1.0
 *
 */
class EmojiActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_emoji)
        
        val ss = ""

        val s = "\uD83D\uDC6E️️\uD83D\uDC77️️"


//        tv_emoji.setText(s)

        v_emoji.setOnClickListener {
//            getEmojiString()
//            setEmoji()
//            loadEmoji()
        }

        tv_apple_emoji.typeface = Typeface.createFromAsset(assets, "font/AppleColorEmoji.ttf")
        tv_emoji_test.typeface = Typeface.createFromAsset(assets, "font/Funkster.ttf")
        tv_emoji_one.typeface = Typeface.createFromAsset(assets, "font/EmojiOneColor.ttf")

        tv_emoji.append("哈哈")
        tv_apple_emoji.append("哈哈")
    }

    private fun getEmojiString() {
        val sb = StringBuilder()
        for (i in 0x1F601..0x1F976) {
            sb.append(getEmojiChar(i))
        }
    }

    private fun loadEmoji() {
        EmojiProvider.load()
    }

    private fun setEmoji() {
        val string = resources.getString(R.string.emoji_data_21)

        val array = string.split(",")
        val sb = StringBuilder()
        var i = 1;
        for (a in array) {
            sb.append(i ++)
                    .append(a)
        }

        tv_emoji.setText(sb)
    }

    private fun getEmoji(v: Int) = String(Character.toChars(v))

    private fun getEmojiChar(v: Int) = Character.toChars(v)
}
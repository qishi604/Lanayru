package com.lanayru.app.ui.emoji

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lanayru.app.R
import com.lanayru.data.EmojiProvider
import com.lanayru.emoji.EmojiTextView
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.model.Emoji
import com.lanayru.view.GridLayout
import kotlinx.android.synthetic.main.activity_emoji_keyboard.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textColor

/**
 * @author 郑齐
 * @since 2019-08-01
 * @version V1.0
 *
 */
class EmojiKeyboardActivity: BaseActivity() {

    lateinit var mLGrid: GridLayout

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_emoji_keyboard)

        val font = Typeface.createFromAsset(assets, "font/iconfont.ttf")
        tv_iconfont.typeface = font


        mLGrid = GridLayout(_this, 8)

        l_root.addView(mLGrid, ViewGroup.LayoutParams(matchParent, matchParent))

        renderEmoji()
    }

    private fun renderEmoji() {
        var list = EmojiProvider.load()
        if (null != list) {
            logEmoji(list)
            logEmojiName(list)
            for (d in list) {
                renderItem(d)
            }
        }
    }

    private fun logEmoji(list: ArrayList<Emoji>) {
        val sb = StringBuilder()
        sb.append(list[0].unified)
        for (i in 1 until list.size) {
            sb.append(',')
            sb.append(list[i].unified)
        }
        println(sb.toString())
    }

    private fun logEmojiName(list: ArrayList<Emoji>) {
        val sb = StringBuilder()
        sb.append(list[0].name)
        for (i in 1 until list.size) {
            sb.append(',')
            sb.append(list[i].name)
        }
        println(sb.toString())
    }

    private val l = View.OnClickListener {
        val tv = it as TextView
        tv_content.append(tv.text)
    }

    private fun renderItem(d: Emoji) {
        val item = EmojiTextView(_this).apply {
            textColor = Color.BLACK
            textSize = 22f
            setOnClickListener(l)
        }
        mLGrid.addView(item)

        var u = Integer.parseInt(d.unified, 16)

        var s = String(Character.toChars(u))

        item.text = s

    }
}
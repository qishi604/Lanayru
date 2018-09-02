package com.lanayru.app.ui.custom

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.view.GridLayout
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.padding
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

/**
 *
 * @author seven
 * @since 2018/8/8
 * @version V1.0
 */
class GridLayoutActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val root = verticalLayout {
            gravity = Gravity.CENTER_HORIZONTAL
        }

        root.addView(renderGrid(), ViewGroup.LayoutParams(wrapContent, wrapContent))
        root.addView(renderGrid())

        setContentView(root)
    }

    private fun renderGrid(): GridLayout {
        val gridLayout = GridLayout(_this, 2).apply {
            horizontalMargin = 12.dp
            verticalMargin = 8.dp
            padding = 2.dp
            backgroundColor = Color.RED
        }
        for (i in 0 until 4) {
            val v = TextView(_this)
            v.gravity = Gravity.CENTER
            v.text = "$i"
            v.setBackgroundColor(Color.GREEN)
            val lp = ViewGroup.LayoutParams(120.dp, 120.dp)
            gridLayout.addView(v, lp)
        }
        return gridLayout
    }
}
package com.lanayru.app.ui.custom

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.view.InfoView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class InfoViewActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val root = verticalLayout()
        root.addView(
                InfoView(_this).apply {

                    backgroundColor = Color.RED
                    layoutParams = LinearLayout.LayoutParams(dip(100), matchParent)
                }
        )

        setContentView(root)
    }
}
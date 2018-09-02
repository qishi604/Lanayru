package com.lanayru.app.ui.custom

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.view.MFrameLayout
import com.lanayru.view.PullLoadingView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class PullLoadingViewActivity : BaseActivity(), AnkoLogger {

    override fun render(savedInstanceState: Bundle?) {


        val content = MFrameLayout(_this).apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        }
//            val lp = ViewGroup.LayoutParams(matchParent, dip(100))
        content.addView(View(_this).apply {
            backgroundColor = Color.BLUE
            layoutParams = ViewGroup.LayoutParams(wrapContent, dip((10)))
        }

        )

        val lp = FrameLayout.LayoutParams(wrapContent, wrapContent)
        lp.gravity = Gravity.CENTER_HORIZONTAL
        val pullView = PullLoadingView(_this)
        content.addView(pullView, lp)

        val root = verticalLayout{

            button("requestLayout") {
                onClick { pullView.requestLayout() }
            }

        }

        root.addView(content)

        setContentView(root)

        val dp = 1.4f

        info("density " + resources.displayMetrics.density)
        info("dp 10 " + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics))
        info("resource dp 1.2 " + resources.getDimensionPixelSize(R.dimen.dp1_2))

        info("dip 10 " + dip(dp))
        info("dp 10 ${dp.dp}")



//        val lp = ViewGroup.LayoutParams(dip(100), dip(100))

    }

}
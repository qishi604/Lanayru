package com.lanayru.app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.view.MaterialProgressBar
import com.lanayru.view.MusicProgressBar
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

/**
 *
 * @author seven
 * @since 2018/8/6
 * @version V1.0
 */
class ProgressBarActivity : BaseActivity() {

    private lateinit var mMDProgress: MaterialProgressBar

    override fun render(savedInstanceState: Bundle?) {
        val progressBar = MusicProgressBar(_this).apply {
            layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                topMargin = 0.dp
            }
        }


        mMDProgress = MaterialProgressBar(_this).apply {
            setColors(Color.RED, Color.CYAN)
            setStrokeWidth(2.dp)
        }

        val root = verticalLayout {


        }
        root.addView(mMDProgress, LinearLayout.LayoutParams(24.dp, 24.dp).apply {
            gravity = Gravity.CENTER
            bottomMargin = 24.dp
        })

        root.addView(progressBar)

        setContentView(root)
    }

    override fun onResume() {
        super.onResume()
        mMDProgress.start()
    }

    override fun onPause() {
        super.onPause()
        mMDProgress.stop()
    }
}
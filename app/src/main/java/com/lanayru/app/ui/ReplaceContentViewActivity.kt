package com.lanayru.app.ui

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.matchParent

class ReplaceContentViewActivity: BaseActivity() {

    private lateinit var mTvText: TextView

    private var mIsReplace = false

    override fun render(savedInstanceState: Bundle?) {
        mTvText = TextView(_this).apply {
            text = "hello world"
            setOnClickListener {
                if (mIsReplace) {
                    it.requestLayout()
                } else {
                    replaceContentView()
                    mIsReplace = true
                }
            }
        }

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(mTvText)
    }

    /**
     * 只保留一层ViewGroup
     */
    private fun replaceContentView() {
        var vg = mTvText.parent as ViewGroup

        while (vg.childCount > 0) {
            vg.removeAllViews()
            if (vg.parent is ViewGroup) {
                vg = vg.parent as ViewGroup
            } else {
                break
            }
        }

        vg.addView(mTvText, ViewGroup.LayoutParams(matchParent, matchParent))
    }
}
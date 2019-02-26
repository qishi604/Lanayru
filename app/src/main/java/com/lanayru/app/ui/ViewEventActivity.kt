package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_event.*
import org.jetbrains.anko.toast

class ViewEventActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_view_event)

        btn_view.setOnClickListener {
            toast("click")
        }

        btn_view.setOnTouchListener { v, event ->
            toast("touch")
            true
        }
    }
}
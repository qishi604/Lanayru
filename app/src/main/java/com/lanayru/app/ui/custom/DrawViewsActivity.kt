package com.lanayru.app.ui.custom

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import org.jetbrains.anko.toast

class DrawViewsActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        toast("hello")

    }

    override fun renderToolbar() {
//        super.renderToolbar()
    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_draws)
    }
}
package com.lanayru.app.ui.router

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_router_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RouterMainActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_router_main)

        btn_player.onClick {
            ARouter.getInstance().build("/music/player").navigation(_this)
        }
    }
}
package com.lanayru.app.ui.router

import android.os.Bundle
import com.chenenyu.router.RouteCallback
import com.chenenyu.router.Router
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.app.R
import kotlinx.android.synthetic.main.activity_router_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RouterMainActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_router_main)

        btn_player.onClick {
            Router.build("music/player").go(_this) { status, uri, message ->
                println("status: $status uri: $uri message: $message")
            }
        }
    }
}
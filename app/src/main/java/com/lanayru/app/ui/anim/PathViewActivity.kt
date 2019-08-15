package com.lanayru.app.ui.anim

import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.view.PathAnimView

class PathViewActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(PathAnimView(_this))
    }
}
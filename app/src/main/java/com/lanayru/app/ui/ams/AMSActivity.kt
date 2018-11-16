package com.lanayru.app.ui.ams

import android.content.Intent
import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.app.ui.PlayActivity
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.ActivityUtil
import kotlinx.android.synthetic.main.activity_ams.*
import org.jetbrains.anko.toast

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/11/12
 *
 **/
class AMSActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_ams)

        btn_start_normal.setOnClickListener {
            startActivity(buildIntent())
        }

        btn_start_ams.setOnClickListener {
            if (ActivityUtil.startActivityAMS(buildIntent())) {
                toast("call success")
            }
        }
    }

    private fun buildIntent() = Intent(_this, PlayActivity::class.java).apply {
        `package` = _this.packageName
    }
}
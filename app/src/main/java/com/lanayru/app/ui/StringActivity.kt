package com.lanayru.app.ui

import android.os.Bundle
import android.text.Html
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_string.*

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/11
 *
 **/
class StringActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_string)
        val s = getString(R.string.format_text)
        print(s)

        val s1 = getString(R.string.invite_friend_des)
        tv_des.text = Html.fromHtml(s1)

    }
}
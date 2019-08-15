package com.lanayru.app.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_autosizing_textview.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/13
 *
 **/
class AutoSizingTextViewActivity : BaseActivity(){

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_autosizing_textview)

//        TextViewCompat.setAutoSizeTextTypeWithDefaults(tv_content, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        et_content.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tv_content.text = s
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        btn_visibility.onClick {
            v_detect.visibility = if (v_detect.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }
}
package com.lanayru.app.ui.bug

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.widget.EditText
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.hideSoftKeyboard
import com.lanayru.util.showSoftKeyboard
import kotlinx.android.synthetic.main.activity_soft_keyboard_dialog.*
import org.jetbrains.anko.matchParent

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/16
 *
 **/
class SoftKeyboardDialogActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_soft_keyboard_dialog)
        btn_show.setOnClickListener {
            showDialog()
        }
    }

    var dialog: AlertDialog? = null
    var et: EditText? = null
    private fun showDialog() {
        if (null == dialog) {
            et = EditText(_this)
            val d = AlertDialog.Builder(_this)
                    .setView(et)
                    .create()

            d.window.setBackgroundDrawable(null)

            d.window.attributes.apply {
                width = matchParent
                dimAmount = 0f
                gravity = Gravity.BOTTOM
            }

            d.setOnDismissListener {
                hideSoftKeyboard(et!!)
            }

            d.setOnShowListener {
                showSoftKeyboard(et!!)
            }

            dialog = d
        }
        et!!.requestLayout()
        dialog!!.show()
    }
}
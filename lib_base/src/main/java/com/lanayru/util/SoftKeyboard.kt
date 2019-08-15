package com.lanayru.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/16
 *
 **/

fun showSoftKeyboard(view: View) {
    val context = view.context
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun showSoftKeyboard(activity: Activity) {
    activity.currentFocus?.let {
        showSoftKeyboard(it)
    }
}

fun hideSoftKeyboard(view: View) {
    val context = view.context
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromInputMethod(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun hideSoftKeyboard(activity: Activity) {
    activity.currentFocus?.let {
        hideSoftKeyboard(it)
    }
}
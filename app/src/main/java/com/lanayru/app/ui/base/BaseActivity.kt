package com.lanayru.app.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.lanayru.extention.dp
import com.lanayru.theme.ThemeHelper
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import org.jetbrains.anko.matchParent
import java.util.*

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
abstract class BaseActivity: AppCompatActivity(), Observer {

    lateinit var _this: BaseActivity

    var _toolbar: Toolbar? = null

    var isResume = false

    var isThemeChange = true

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        _this = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        render(savedInstanceState)
        renderToolbar()
        ThemeHelper.addObserver(this)
    }

    abstract fun render(savedInstanceState: Bundle?)

    open fun renderToolbar() {
        _toolbar = Toolbar(_this)
        val vg = find<ViewGroup>(android.R.id.content)
        val toolbarHeight = 48.dp
        if (vg.childCount > 0) {
            val lp = vg.getChildAt(0).layoutParams as ViewGroup.MarginLayoutParams
            lp.topMargin = toolbarHeight
        }
        vg.addView(_toolbar, ViewGroup.LayoutParams(matchParent, toolbarHeight))
        _toolbar!!.title = title
    }

    override fun onResume() {
        super.onResume()
        isResume = true

        renderTheme()
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    private fun onThemeChange() {
        isThemeChange = true
        renderTheme()
    }

    fun renderTheme() {
        if (isResume && isThemeChange) {
            isThemeChange = false

            val skin = ThemeHelper.skin
            window.statusBarColor = skin.colorPrimaryDark
            _toolbar?.backgroundColor = skin.colorPrimary
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        onThemeChange()
    }

    override fun onDestroy() {
        super.onDestroy()
        ThemeHelper.deleteObserver(this)
    }

}
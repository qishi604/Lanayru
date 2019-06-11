package com.lanayru.app.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.wrapContent

/**
 * @author 郑齐
 * @since 2019/5/14
 * @version V1.0
 *
 */
class PopupActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val root = relativeLayout {

        }

        val button = Button(_this).apply {
            text = "Show center"
            onClick {
                showpopup(it!!)
            }
        }

        var lp1 = RelativeLayout.LayoutParams(wrapContent, wrapContent)
        lp1.addRule(RelativeLayout.CENTER_IN_PARENT)
        root.addView(button, lp1)

        val button1 = Button(_this).apply {
            text = "Show Right"
            onClick {
                showpopup(it!!)
            }
        }

        lp1 = RelativeLayout.LayoutParams(wrapContent, wrapContent)
        lp1.addRule(RelativeLayout.ALIGN_RIGHT)
        lp1.addRule(RelativeLayout.CENTER_VERTICAL)
        root.addView(button1, lp1)

    }

    private fun showpopup(anchor: View) {
        anchor.isEnabled = false
        val contentView = LayoutInflater.from(_this).inflate(R.layout.popup_menu, null);
        val popup = PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popup.setOnDismissListener {
            anchor.postDelayed({
                anchor.isEnabled = true
            }, 200)
        }

        popup.isOutsideTouchable = true
        popup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popup.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, 0)


        contentView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {

            }

            override fun onViewAttachedToWindow(v: View?) {

            }

        })

        val layoutListener = object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                val w = right - left

                if (w == 0) {
                    return
                }

                val h = bottom - top

                v!!.removeOnLayoutChangeListener(this)
                val xy = IntArray(2)
                anchor!!.getLocationInWindow(xy)

                val x = xy[0] - (w - anchor.width) / 2
                val y = xy[1] - h
                popup.update(x, y, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }

        }

        contentView.addOnLayoutChangeListener(layoutListener)

    }
}
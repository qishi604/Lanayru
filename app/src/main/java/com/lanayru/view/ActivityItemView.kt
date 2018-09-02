package com.lanayru.view

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import com.lanayru.app.R
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class ActivityItemView(context: Context): TextView(context), IDateView<ActivityInfo> {

    companion object {
        const val prefix = "app.ui."
    }

    init {
        val p = dip(16)
        setPadding(p, p, p, p)
        backgroundResource = R.drawable.item_background

        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)

        textSize = 16f

        typeface = Typeface.DEFAULT_BOLD
    }

    override var data: ActivityInfo? = null
        set(value) {
            field = value
            text = value?.let {
                it.name.replace("${it.packageName}.$prefix", "")
            } ?: ""
        }


}
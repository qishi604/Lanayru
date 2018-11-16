package com.lanayru.view

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.lanayru.app.R
import com.lanayru.extention.dp
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/17
 *
 **/
class BluetoothItemView: RelativeLayout, IDateView<BluetoothDevice> {

    val tvName = TextView(context).apply {
        textSize = 16f
        textColor = 0xff333333.toInt()

    }
    val tvAddress = TextView(context).apply {
        textSize = 14f
        textColor = 0xff999999.toInt()
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        tvName.id = R.id.title
        addView(tvName)

        addView(tvAddress, LayoutParams(wrapContent, wrapContent).apply {
            addRule(RelativeLayout.RIGHT_OF, R.id.title)
            addRule(RelativeLayout.ALIGN_BOTTOM, R.id.title)
            leftMargin = 20.dp
        })

        val pd = 16.dp
        setPadding(pd, pd, pd, pd)
    }

    override var data: BluetoothDevice? = null
    set(value) {
        field = value

        value?.let {
            tvName.text = it.name
            tvAddress.text = it.address
        }
    }
}
package com.lanayru.view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/11
 *
 **/
class FrameAnimView: ImageView {

    private var mAnimDrawable: AnimationDrawable? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun load(drawableRes: Int) {
        setBackgroundResource(drawableRes)
        mAnimDrawable = background as AnimationDrawable
    }

    fun start() {
        mAnimDrawable?.start()
    }

    fun stop() {
        mAnimDrawable?.stop()
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE && this.visibility == View.VISIBLE) {
            start()

        } else {
            stop()
        }
    }
}
package com.lanayru.view.rv

import android.content.Context
import android.util.AttributeSet

class RootRecyclerView: BetterRecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // keep empty
//        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }
}
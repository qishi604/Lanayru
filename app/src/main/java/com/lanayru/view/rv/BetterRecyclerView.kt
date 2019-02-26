package com.lanayru.view.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.lanayru.util.setField

open class BetterRecyclerView: RecyclerView {

    private val mTouchSlop: Int

    private var mScrollPointerId: Int = 0
    private var mInitialTouchX: Int = 0
    private var mInitialTouchY: Int = 0


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        val vc = ViewConfiguration.get(context)
        mTouchSlop = vc.scaledTouchSlop
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (null == e) {
            return super.onInterceptTouchEvent(e)
        }

        when (e.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN-> {
                val actionIndex = e.actionIndex
                mScrollPointerId = e.findPointerIndex(actionIndex)
                mInitialTouchX = (e.x + 0.5f).toInt()

                mInitialTouchY = (e.y + 0.5f).toInt()

                return super.onInterceptTouchEvent(e)
            }

            MotionEvent.ACTION_MOVE-> {
                val index = e.findPointerIndex(mScrollPointerId)
                if (index < 0) {
                    return false
                }

                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()

                if (scrollState != SCROLL_STATE_DRAGGING) {
                    val dx = x - mInitialTouchX
                    val dy = y - mInitialTouchY
                    var startScroll = false
                    val canScrollHorizontally = layoutManager.canScrollHorizontally()
                    val canScrollVertically = layoutManager.canScrollVertically()
                    if (canScrollHorizontally && Math.abs(dx) > mTouchSlop
                    && (Math.abs(dx) > Math.abs(dy) || canScrollVertically)
                    ) {
                        startScroll = true
                    }

                    if (layoutManager.canScrollVertically() && Math.abs(dy) > mTouchSlop
                    && (Math.abs(dy) > Math.abs(dx) || canScrollHorizontally)
                    ) {
                        startScroll = true
                    }

                    return startScroll && super.onInterceptTouchEvent(e)
                }
            }
        }

        return super.onInterceptTouchEvent(e)
    }

    private fun setScrollState(state: Int) {
        setField(this, "mScrollState", state)
    }
}
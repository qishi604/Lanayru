package com.lanayru.app.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.lanayru.util.Logs

/**
 * @author 郑齐
 * @since 2019-07-08
 * @version V1.0
 *
 */
class LifeCycleFragment: Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Logs.i("onAttach ${activity} ${activity as LifeCycleTest}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logs.i("onCreate ${activity} ${activity as LifeCycleTest}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logs.i("onCreateView ${activity} ${activity as LifeCycleTest}")
        return LinearLayout(container!!.context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logs.i("onActivityCreated ${activity} ${activity as LifeCycleTest}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logs.i("onSaveInstanceState ${activity}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logs.i("onDestroyView ${activity}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logs.i("onDestroy ${activity}")
    }

    override fun onDetach() {
        super.onDetach()
        Logs.i("onDetach")
    }

    interface LifeCycleTest {
        fun test()
    }
}
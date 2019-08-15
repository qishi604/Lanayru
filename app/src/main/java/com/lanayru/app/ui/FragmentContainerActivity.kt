package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.Logs

/**
 * @author 郑齐
 * @since 2019-07-08
 * @version V1.0
 *
 */
class FragmentContainerActivity : BaseActivity(), LifeCycleFragment.LifeCycleTest {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_fragment)

        Logs.i("Activity create savedInstanceState ${savedInstanceState}" )

        if (null == savedInstanceState) {

            supportFragmentManager.beginTransaction().add(R.id.fragment_container, LifeCycleFragment()).commit()
        }
    }

    override fun test() {

    }
}
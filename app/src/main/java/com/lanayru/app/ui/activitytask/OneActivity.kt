package com.lanayru.app.ui.activitytask

import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * 测试Activity 栈
 * - standard: 标准模式，每次启动都会创建一个新的Activity实例，并将其压入任务栈栈顶
 * - singleTop: 栈顶复用模式
 * - singleTask: 栈内复用模式。
 * - singleInstance: 加强版的singleTask。单独一个任务栈。
 *
 * [参考](https://juejin.im/post/5935081d2f301e006b09cb9e)
 */
class OneActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("showSecond ${taskId}").onClick {
                startActivity<SecondActivity>()
            }
        }
    }
}

/**
 * 在Manifest文件设置android:taskAffinity="com.seven.app"，
 * 通过这个属性可以把不同的Activity分在不同的任务栈中
 * 结论：
 * 1. taskAffinity 类似栈，后进先出结构
 * 2. taskAffinity 只有 launchMode 设置为 singleTask 或 singleInstance 才有效
 * 3. launchMode=singleTask 时，1在一个栈，2、3在一个栈。 回退流程：3->2->1
 * 4. launchMode=singleInstance 时，1、3一个栈，2单独一个栈。回退流程：3->1...->2，这里解释以下，先1、3所在的栈退出完，才轮到2退出
 */
class SecondActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("showThird ${taskId}").onClick {
                startActivity<ThirdActivity>()
            }
        }
    }
}

class ThirdActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("back ${taskId}").onClick {
                finish()
            }
        }
    }
}
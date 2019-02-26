package com.lanayru.app.rxjava

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class RxJavaTest  {

    fun log(msg: Any) {
        println("thread ${Thread.currentThread().name} $msg")
    }

    @Test
    fun testSubscribeOn() {
        val onNext = Consumer<Int> {
            log("onNext : $it")
        }

//        Observable.just(1,2,3)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.io())
//                .subscribe(onNext)

        log("start ================== ")

        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(it: ObservableEmitter<Int>) {
                log("create observable")
                it.onNext(1)
                it.onComplete()
            }

        })
//                .observeOn(Schedulers.computation()) // 指定map发生的线程
                .subscribeOn(Schedulers.io()) // 指定create发生的线程
                .map(object : Function<Int, Int> {
                    override fun apply(t: Int): Int {
                        log("map value")
                        return t * 100
                    }
                })
                .observeOn(Schedulers.newThread()) // 指定onNext发生的线程
                .subscribeOn(Schedulers.newThread())
                .flatMap {
                    log("flat map")
                    Observable.just(it * 10)
                }
//                .subscribeOn(Schedulers.computation())
                .subscribe(object : Observer<Int> {

                    override fun onComplete() {
                        log("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        log("onSubscribe is dispose ${d.isDisposed}")
                    }

                    override fun onNext(t: Int) {
                        log("onNext: $t")
                    }

                    override fun onError(e: Throwable) {
                        log("onError ${e.message}")
                    }

                })

        // 调试的时候需要把这个值设置大一些，不然main线程结束，程序就会直接退出
        Thread.sleep(10 * 60 * 1000)
    }
}
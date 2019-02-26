package com.lanayru.app.thread;

import org.junit.Test;

/**
 *
 * synchronized
 * - 可重入：同一线程外层函数获取锁之后，内层函数可以直接再次获取该锁
 * - 好处：避免死锁，提高封装性
 * - 粒度：线程范围，即 synchronized 修饰的同步方法内部既能调用同步方法，也能调用非同步方法
 *
 *
 * Java 内存模型
 *
 * 描述Java 程序中各变量的访问规则，即在 JVM 中将变量存储到内存和从内存中读取变量的底层细节
 *
 * 1. 所有变量都存储在内存中
 * 2. 每个线程都有自己独立的工作内存，其保存该线程用到的变量副本（主内存变量拷贝）
 *
 * 规定：
 * 1. 线程对变量的所有操作都必须在工作内存中进行，不能直接读写主内存
 * 2. 线程无法直接访问其他线程的工作内存中的变量，线程间变量值的传递需要通过主内存
 */
public class SynchronizedTest {

    private int mCount = 0;

    private void log(Object obj) {
        System.out.println(obj);
    }

    Runnable runnable = new Runnable() {
        @Override
        public synchronized void run() {
            for (int i = 0; i < 10000; i++) {
                ++mCount;
            }
        }
    };

    @Test
    public void test1() {
        log("测试哪个线程先运行，是main 还是子线程");

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);


        t1.start();
        t2.start();

        try {
            t1.join(); // 当前线程等待这个线程执行完（也就是main等待t1执行完）
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        log(mCount);
        System.out.println(mCount);
    }
}

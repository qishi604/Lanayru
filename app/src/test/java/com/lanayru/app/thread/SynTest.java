package com.lanayru.app.thread;

import org.junit.Test;

public class SynTest {


    public synchronized void a() {
        System.out.println("call a");
    }

    /**
     * 同一个线程是不会发生竞争
     */
    public synchronized void b() {
        System.out.println("call b");
        a();
        System.out.println("call b end");
    }

    @Test
    public void test() {
        new Thread() {
            @Override
            public void run() {
                b();
            }
        }.start();
//        b();
    }
}

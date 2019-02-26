package com.lanayru.app.lock;

import org.junit.Test;

public class ThisLockTest {

    final Object lock = new Object();

    public synchronized void dome() {
            System.out.println("lock on thread " + Thread.currentThread().getName());
            try {
//                lock.wait();
                Thread.sleep(5000);
//                lock.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("release lock");
    }

    public synchronized void getLock() {
        System.out.println("get lock on thread " + Thread.currentThread().getName());
    }

    @Test
    public void testLock() {
        new Thread() {
            @Override
            public void run() {
                dome();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                getLock();
            }
        }.start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

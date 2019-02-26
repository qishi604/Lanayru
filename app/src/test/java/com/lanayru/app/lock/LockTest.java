package com.lanayru.app.lock;

import org.junit.Test;

public class LockTest {

    final Object lock = new Object();

    public void dome() {
        synchronized (lock) {
            System.out.println("lock on thread " + Thread.currentThread().getName());
            try {
//                lock.wait();
                Thread.sleep(5000);
//                lock.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("release lock");
    }

    public void getLock() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            System.out.println("get lock on thread " + Thread.currentThread().getName());
        }
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

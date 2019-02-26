package com.lanayru.app.lock;

import org.junit.Test;

public class LockWaitTest {

    final Object lock = new Object();

    public void dome() {
        synchronized (lock) {
            System.out.println("lock on thread " + Thread.currentThread().getName());
            try {
                lock.wait();

//                lock.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("re get lock");
        }
    }

    public void releaseLock() {
        synchronized (lock) {
            System.out.println("After 5s notifyLock lock on thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notifyAll();
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
                releaseLock();
            }
        }.start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

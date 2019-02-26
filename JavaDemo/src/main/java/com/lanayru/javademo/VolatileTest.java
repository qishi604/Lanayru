package com.lanayru.javademo;

public class VolatileTest {

    long a = 0;
    boolean flag = false;

    private void write() {
        System.out.println("write in thread " + Thread.currentThread().getName());
        flag = true;
        a = 10000;
        System.out.println("end write in thread " + Thread.currentThread().getName());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        a = 20000;
    }

    private void read() {
        System.out.println("read in thread " + Thread.currentThread().getName());
        if (flag) {
            long i = a;
            System.out.println("a is " + i);
        }
    }

    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                write();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                read();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                read();
            }
        }).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

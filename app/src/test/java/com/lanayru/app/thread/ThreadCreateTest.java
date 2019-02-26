package com.lanayru.app.thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadCreateTest {

    @Test
    public void test1() {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                return "from call";
            }
        };
        FutureTask<String> ft = new FutureTask(callable);

        System.out.println("start thread ==== ");

        Thread t = new Thread(ft, "future");
        t.start();
        try {
            t.join(); // （当前线程等待子线程终止）等待t线程结束，main线程才会执行
            System.out.println("wait thread join ==== ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            String s = ft.get();
            System.out.println(s);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

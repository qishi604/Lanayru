package com.lanayru.app.lock;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用 LinkedBlockingQueue 实现生产者消费者
 *
 * @author 郑齐
 * @version V1.0
 * @since 2019-08-15
 */
public class Produce2Test {


    private int maxInventory = 10;

    private LinkedBlockingQueue<String> products = new LinkedBlockingQueue<>(maxInventory);


    private void produce(String e) {
        try {
            products.put(e);
            System.out.println("Produce " + e + " size = " +products.size());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String consume() {
        String result = null;
        try {
            result = products.take();
            System.out.println("Eat " + result + " size = " +products.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 1, N = 20; i <= N; i++) {
                produce("Egg " + i);
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0, N = 20; i < N; i++) {
                consume();
            }
        }
    }

    @Test
    public void main() {
        Produce2Test test = new Produce2Test();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
    }

}

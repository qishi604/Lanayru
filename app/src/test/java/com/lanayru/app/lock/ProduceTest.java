package com.lanayru.app.lock;

import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者
 *
 * @author 郑齐
 * @version V1.0
 * @since 2019-08-15
 */
public class ProduceTest {

    private LinkedList<String> products = new LinkedList<>();

    private int maxInventory = 10;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private void produce(String e) {
        lock.lock();
        try {
            while (products.size() == maxInventory) {
                condition.await();
            }

            products.add(e);
            System.out.println("Produce " + e + " size = " +products.size());
            condition.signalAll();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private String consume() {
        String result = null;

        lock.lock();
        try {
            while (products.size() == 0) {
                condition.await();
            }
            result = products.removeLast();
            System.out.println("Eat " + result + " size = " +products.size());
            condition.signalAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
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
        ProduceTest test = new ProduceTest();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
    }

}

package com.lanayru.app.lock;

import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者优化 1
 *
 * @author 郑齐
 * @version V1.0
 * @since 2019-08-15
 */
public class Produce1Test {

    private LinkedList<String> products = new LinkedList<>();

    private AtomicInteger inventory = new AtomicInteger(0);

    private final int maxInventory = 10;

    private Lock produceLock = new ReentrantLock();

    private Lock consumerLock = new ReentrantLock();

    private Condition notFullCondition = produceLock.newCondition();

    private Condition notEmptyCondition = consumerLock.newCondition();

    private void produce(String e) {
        produceLock.lock();
        try {
            while (inventory.get() == maxInventory) {
                notFullCondition.await();
            }

            products.add(e);
            System.out.println("Produce " + e + " size = " + inventory.incrementAndGet());

            if (inventory.get() < maxInventory) {
                notFullCondition.signalAll();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            produceLock.unlock();
        }

        if (inventory.get() > 0) {
            try {
                consumerLock.lockInterruptibly();
                notEmptyCondition.signalAll();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } finally {
                consumerLock.unlock();
            }
        }
    }

    private String consume() {
        String result = null;

        consumerLock.lock();
        try {
            while (products.size() == 0) {
                notEmptyCondition.await();
            }
            result = products.removeLast();
            System.out.println("Eat " + result + " size = " + inventory.decrementAndGet());

            if (inventory.get() > 0) {
                notEmptyCondition.signalAll();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            consumerLock.unlock();
        }

        if (inventory.get() < maxInventory) {
            try {
                produceLock.lockInterruptibly();
                notFullCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                produceLock.unlock();
            }

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
        Produce1Test test = new Produce1Test();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
        new Thread(test.new Producer()).start();
        new Thread(test.new Consumer()).start();
    }

}

package bank;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private int cash;
    private final int id;
    private ReentrantLock lock;
    private Condition condition;

    public Account(int initialCash, int id) {
        this.cash = initialCash;
        this.id = id;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    void deposit(int amount) {
        System.out.println(" in deposit " + Thread.currentThread() + " with state " + Thread.currentThread().getState());
//        lock.writeLock().lock();
//        try {
            cash += amount;
//        } finally {
//            lock.writeLock().unlock();
//        }
    }

    void withdraw(int amount) {

        System.out.println(" in withdraw " + Thread.currentThread() + " with state " + Thread.currentThread().getState());
        lock.lock();
        while (amount > cash) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            cash -= amount;
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    int getBalance() {
        return cash;
    }

    public boolean hasNotEnoughMoney(int amount) {
        return cash < amount;
    }
}

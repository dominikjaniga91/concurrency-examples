package bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Bank bank = new Bank(10);
        Random random = new Random();
        List<Thread> threads = new ArrayList<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    int from = random.nextInt(10);
                    int to = random.nextInt(10);
                    bank.transfer(from, to, 500);
                    int balance = bank.totalBalance();
                    System.out.println("current balance: " + balance);
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.setName("This is a thread number " + i);
            threads.add(thread);
        }

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }
}

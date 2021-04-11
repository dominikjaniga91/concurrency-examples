package bank;

import java.util.Arrays;

public class Bank {

    private final Account[] accounts;

    public Bank(int numberOfAccount) {
        this.accounts = new Account[numberOfAccount];
        for (int i = 0; i < numberOfAccount; i++) {
            accounts[i] = new Account(1000 ,i);
        }
    }

    void transfer(int from, int to, int amount) {

        Account accountFrom = accounts[from];
        Account accountTo = accounts[to];

//        synchronized (accountFrom) {
            System.out.println("Current thread " + Thread.currentThread());
//            while (accountFrom.hasNotEnoughMoney(amount)) {
//                System.out.println(" while from account " + from + " with money: " + accountFrom.getBalance()
//                        + " to account " + to + " with money " + accountTo.getBalance());
//                try {
//                    accountFrom.wait();
//                    printAllThreads();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            System.out.println(Thread.currentThread() + " moving money from account " + from + " with money: " + accountFrom.getBalance()
                    + " to account " + to + " with money " + accountTo.getBalance());
            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
//            accountFrom.notifyAll();
            printAllThreads();

//        }

    }

    private void printAllThreads() {
        Thread.getAllStackTraces().keySet().forEach(t -> System.out.println(t.getName() + " in state " + t.getState()));
    }

    int totalBalance() {
        return Arrays.stream(accounts).mapToInt(Account::getBalance).sum();
    }
}

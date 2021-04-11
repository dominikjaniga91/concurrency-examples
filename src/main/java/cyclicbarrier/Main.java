package cyclicbarrier;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {

        List<List<Integer>> results = Collections.synchronizedList(new ArrayList<>());
        Random random = new Random();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new JobAggregator(results));

        for (int i = 0; i < 5; i++) {
            new Thread(new Job(results, random, cyclicBarrier)).start();
        }
    }
}

class Job implements Runnable {

    private final List<List<Integer>> numbers;
    private final Random random;
    private final CyclicBarrier cyclicBarrier;

    public Job(List<List<Integer>> numbers,
               Random random,
               CyclicBarrier cyclicBarrier) {
        this.numbers = numbers;
        this.random = random;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        List<Integer> partialNumbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(100);
            partialNumbers.add(number);
        }
        numbers.add(partialNumbers);
        System.out.println(Thread.currentThread() + " generate " + partialNumbers);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class JobAggregator implements Runnable {

    private final List<List<Integer>> results;

    public JobAggregator(List<List<Integer>> results) {
        this.results = results;
    }

    @Override
    public void run() {
        System.out.println(" aggregator");
        int reduce = results.stream().flatMap(Collection::stream).reduce(0, Integer::sum);
        System.out.println(" sum:  " + reduce);
    }
}
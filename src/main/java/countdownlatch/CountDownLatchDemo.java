package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  <b>CountDownLatch</b>
 * </p>
 * <p>
 *     With CountDownLatch we can cause a thread to block until other threads
 *     have completed a given task. Has a counter field which we can decrement
 *     as we require.
 * </p>
 * <p>
 *     <ul>
 *         <li>is initialized with a given count.</li>
 *         <li>allows one or more threads to wait until a set of operations begin performed</li>
 *         <li>await method block until the current count reaches zero</li>
 *         <li>the "counter" is decreasing by countDown method</li>
 *         <li>the count cannot be reset</li>
 *         <li></li>
 *     </ul>
 * </p>
 * <p>
 *     The usage is very simple:
 *     <ol>
 *         <li>create a class, for instance MyThread, that implements runnable</li>
 *         <li>pass the instance of CountDownLatch as constructor parameter of MyThread</li>
 *         <li>override the run method and invoke there countDown method</li>
 *         <li>create an instance of CountDownLatch with initial count</li>
 *         <li>create definite number of Threads</li>
 *         <li>start the Threads</li>
 *         <li>invoke the await method</li>
 *     </ol>
 * </p>
 */
public class CountDownLatchDemo {



    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(new MyThread(count)).start();
        }
//        runJobs(count);

        /**
         * It is good to use await method with timeout as parameter
         * instead of normal await method to avoid deadlock.
         *
         */
        count.await(400, TimeUnit.MILLISECONDS);
    }

//    private static void runJobs(CountDownLatch countDownLatch) {
//        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
//        threadPool.schedule(new MyThread(countDownLatch),100, TimeUnit.MILLISECONDS);
//        threadPool.schedule(new MyThread(countDownLatch),300, TimeUnit.MILLISECONDS);
//        threadPool.schedule(new MyThread(countDownLatch),200, TimeUnit.MILLISECONDS);
//        threadPool.shutdown();
//    }
}


class MyThread implements Runnable {

    private final CountDownLatch count;

    MyThread(CountDownLatch count) {
        this.count = count;
    }

    /**
     * It is a good practice to put
     * countDown method in finally block.
     *
     */
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread() + " with state " + Thread.currentThread().getState());
        } finally {
            count.countDown();
            System.out.println("Count " + count.getCount());
        }
    }
}
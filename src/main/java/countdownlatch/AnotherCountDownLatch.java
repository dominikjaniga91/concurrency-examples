package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * In this example instead of blocking parent Thread
 * we will block each child thread until all the others have started.
 *
 */
public class AnotherCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch start = new CountDownLatch(5);
        CountDownLatch end = new CountDownLatch(5);
    }
}

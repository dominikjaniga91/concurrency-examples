package countdownlatch;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class AnotherCountDownLatchTest {

    @Test
    public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime()
            throws InterruptedException {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);
        List<Thread> workers = Stream.generate(() -> new Thread(new WaitingWorker(
                        outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);
        readyThreadCounter.await(); // this waits for all threads
        outputScraper.add("Workers ready");
        System.out.println("Workers ready");


        callingThreadBlocker.countDown(); // this lets all thread to finish
        completedThreadCounter.await();
        outputScraper.add("Workers complete");

        Assertions.assertThat(outputScraper)
                .containsExactly(
                        "Workers ready",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Workers complete"
                );
    }
}

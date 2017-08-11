package nl.bsoft.writequeue;

import nl.bsoft.reader.Receiver;
import nl.bsoft.sender.Sender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)

@SpringBootTest
public class WritequeueApplicationTests {

    private final Logger log = LoggerFactory.getLogger(WritequeueApplicationTests.class);

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Test
    public void test01Receive() throws Exception {
        sender.send("simple.queue", "Hello Boot!");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        Assert.assertEquals(receiver.getLatch().getCount(), 0L);
    }

    @Test
    public void test02SendAndReceive() {
        final long n = 100L;

        ExecutorService executor01 = Executors.newSingleThreadExecutor();
        executor01.submit(() -> {
            for (int i = 0; i < n; i++) {
                String message = "This is message: " + i;
                sender.send("simple.queue", message);
            }
            log.info("Sended {} messages", n);
        });

        ExecutorService executor02 = Executors.newSingleThreadExecutor();
        executor02.submit(() -> {
            try {
                receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
                long nmes = receiver.getLatch().getCount();
                Assert.assertEquals(nmes, n);
                log.info("Received {} messages", nmes);
            } catch (Exception e) {
                log.error("Error during reading", e);
            }
        });

        long timeout = 10 * 1000;
        try {
            Thread.sleep(timeout);
        } catch (Exception e) {
            log.error("Interrupted", e);
        }
        try {
            log.info("attempt to shutdown executor");
            executor02.shutdown();
            executor02.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("tasks interrupted");
        } finally {
            if (!executor02.isTerminated()) {
                log.error("cancel non-finished tasks");
            }
            executor02.shutdownNow();
            log.info("shutdown finished");
        }

        try {
            log.info("attempt to shutdown executor");
            executor01.shutdown();
            executor01.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("tasks interrupted");
        } finally {
            if (!executor01.isTerminated()) {
                log.error("cancel non-finished tasks");
            }
            executor01.shutdownNow();
            log.info("shutdown finished");
        }

    }

    @Test
    public void test03SendAndReceive() {
        final long n = 100L;
        for (int i = 0; i < n; i++) {
            String message = "This is message: " + i;
            sender.send("simple.queue", message);
        }
        log.info("Sended {} messages", n);

        try {
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            long nmes = receiver.getLatch().getCount();
            Assert.assertEquals(nmes, n);
            log.info("Received {} messages", nmes);
        } catch (Exception e) {
            log.error("Unexpected error", e);
        }
    }
}

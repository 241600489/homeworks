package zym.concurrent.patterns.juc;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class ClhLockTest {
    private static int i = 1;

    public static void incr() {
        i++;
    }

    @Test
    public void  testLock() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ClhLock clhLock = new ClhLock();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                clhLock.lock();
                incr();
                clhLock.unLock();
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
       Assertions.assertEquals(101,i);

    }


}

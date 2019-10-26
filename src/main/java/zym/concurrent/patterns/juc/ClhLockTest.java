package zym.concurrent.patterns.juc;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class ClhLockTest {
    private static int i = 1;

    public static void incr() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
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
        System.out.println(i);

    }


}

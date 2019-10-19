package zym.concurrent.patterns.juc;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MutexTest {

    private Mutex mutex;

    @BeforeEach
    public void init() {
        mutex = new Mutex();
    }



    @Test
    public void lock() {
        mutex.lock();
        assertTrue(mutex.isHoldTheLock());
        mutex.unlock();
    }

    @Test
    public void lockInterruptibly() {
        mutex.lock();
        Thread testThread = new Thread(() -> {
            try {
                mutex.lockInterruptibly();
            } catch (InterruptedException e) {
                assertFalse(mutex.isHoldTheLock());
                System.out.println("abort some");
            }
        });
        testThread.start();

        testThread.interrupt();

        mutex.unlock();
    }

    @Test
    public void tryLock() {
        boolean lock = mutex.tryLock();
        Assertions.assertTrue(lock);
        mutex.unlock();
    }


    @Test
    public void unlock() {
        mutex.lock();
        mutex.unlock();
        assertFalse(mutex.isHoldTheLock());
    }
}

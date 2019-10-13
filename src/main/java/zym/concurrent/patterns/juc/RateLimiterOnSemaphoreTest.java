package zym.concurrent.patterns.juc;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterOnSemaphoreTest {
    public static void main(String[] args) {
        RateLimiterOnSemaphore rateLimiter = RateLimiterOnSemaphore.create(10);
        //正式项目慎用
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                rateLimiter.acquire();
                doSomething();
            });
        }
    }

    private static void doSomething() {
        System.out.println("now is " + System.currentTimeMillis() / 1000);
    }




}

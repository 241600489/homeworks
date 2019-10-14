package zym.concurrent.patterns.juc;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testRetry() {
        RateLimiterOnSemaphore rateLimiter = RateLimiterOnSemaphore.create(10);
        //正式项目慎用
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                if (rateLimiter.tryAcquire()) {
                    doSomething();
                }else {
                    System.out.println("失败");
                }
            });
        }
    }

    @Test
    public void testGoogleRateLimiterTry() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Thread.sleep(1000);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                if (rateLimiter.tryAcquire()) {
                    doSomething();
                }else {
                    System.out.println("失败");
                }
            });
        }

    }


}

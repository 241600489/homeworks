package zym.concurrent.patterns.juc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 限流：令牌桶算法
 */
public class RateLimiterOnSemaphore {
    private static final int DEFAULT_REQUEST_PER_SECOND = 200;
    private static final int SECOND_MILLIONS = 1000;
    private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    private final Semaphore tokenContainer;

    private final int requestPerSecond;

    public RateLimiterOnSemaphore() {
        this(DEFAULT_REQUEST_PER_SECOND);
    }

    public RateLimiterOnSemaphore(int requestPerSecond) {
        this.requestPerSecond = requestPerSecond;
        tokenContainer = new Semaphore(requestPerSecond);
        //定时任务往container 匀速的存放token
        //计算 定时任务执行的间隔时间
        long period = SECOND_MILLIONS / requestPerSecond;

        schedule.scheduleAtFixedRate(this::putToken, 0, period, TimeUnit.MILLISECONDS);
    }

    public static RateLimiterOnSemaphore create(int tokensPerSecond) {
        return new RateLimiterOnSemaphore(tokensPerSecond);
    }

    /**
     * 获取token
     */
    public void acquire() {
        tokenContainer.acquireUninterruptibly();
    }

    /**
     * 尝试获取token
     *
     * @return true 如果获取成功 否则返回false
     */
    public boolean tryAcquire() {
        return tokenContainer.tryAcquire();
    }

    /**
     * 往容器里存放token
     */
    private void putToken() {
        //判断是否达到每秒上线
        if (tokenContainer.availablePermits() < requestPerSecond) {
            tokenContainer.release();
        }
    }

    public void shutDown() {
        schedule.shutdown();
    }
}

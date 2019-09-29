package zym.netty.nio;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liangziqiang
 * @date 2019/9/29 16:30
 */
public class MonkeyThreadFactory implements ThreadFactory {
    private AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread("monkey-worker-" + atomicLong.getAndIncrement());
    }
}

package zym.concurrent.patterns.juc.pc;

import zym.collections.CircleQueue;

/**
 * 生产者和消费者模式
 * 消费者
 *
 * @author liangziqiang
 */
public class Consumer {

    private  CircleQueue<String> cache;


    public Consumer(CircleQueue<String> container) {
        this.cache = container;
    }

    public void consume() throws InterruptedException {
        synchronized (cache) {
            while (cache.isEmpty()) {
                cache.wait();
            }
            System.out.println(
                    String.format("thread:%s,consume a element:%s",
                            Thread.currentThread().getName(),
                            cache.take()));
            cache.notifyAll();
        }
    }
}

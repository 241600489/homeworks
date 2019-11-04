package zym.concurrent.patterns.juc.pc;

import zym.collections.CircleQueue;

/**
 * 生产者和消费者模式
 * 生产者
 *
 * @author liangziqiang
 */
public class Producer {
    private CircleQueue<String> cache;


    public Producer(CircleQueue<String> cache) {
        this.cache = cache;
    }

    public void produce(String e) throws InterruptedException {
        synchronized (cache) {
            while (cache.isFull()) {
                cache.wait();
            }
            cache.put(e);
            cache.notifyAll();
        }
    }
}

package zym.concurrent.patterns.juc.pc;

import zym.collections.CircleQueue;

/**
 * 测试生产者和消费者
 *
 * @author liangziqiang
 */
public class Test {
    public static void main(String[] args) {
        CircleQueue<String> cache = new CircleQueue<>(10);
        Object lock = new Object();
        for (int j = 0; j < 2; j++) {
            new Thread(() -> {
                Producer producer = new Producer(cache);
                for (int i = 0; i < 10; i++) {
                    try {
                        producer.produce("数据" + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                Consumer consumer = new Consumer(cache);
                while (true) {
                    try {
                        consumer.consume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }
}

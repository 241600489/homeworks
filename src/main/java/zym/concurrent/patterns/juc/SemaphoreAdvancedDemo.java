package zym.concurrent.patterns.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import static zym.concurrent.patterns.juc.SemaphoreDemo.THREAD_SIZE;
import static zym.concurrent.patterns.juc.SemaphoreDemo.runSomething;

/**
 * @author 梁自强
 * @date 2019.09.20
 */
public class SemaphoreAdvancedDemo {

    public static void main(String[] args) {
        //新建一个拥有4个许可的信号量
        Semaphore semaphoreAdvance = new Semaphore(4);
        for (int i = 0; i < THREAD_SIZE; i++) {
            Thread demoThread = new Thread(() -> {
                boolean isAcquire = true;
                try {
                    isAcquire = semaphoreAdvance.tryAcquire();
                    runSomething();
                } catch (InterruptedException e) {
                    System.out.println(String.format("threadId:%s interrupt", Thread.currentThread().getId()));
                    Thread.currentThread().interrupt();
                } finally {
                    if (isAcquire) {
                        //若是tryAcquire 被中断则不会走这里
                        semaphoreAdvance.release();
                        System.out.println(String.format("current threadId:%s released a permit", Thread.currentThread().getId()));
                    }
                }
            });
            demoThread.start();
            //随机调用interrupt 方法模仿实际被中断
            if (ThreadLocalRandom.current().nextInt(THREAD_SIZE) > THREAD_SIZE / 2) {
                demoThread.interrupt();
            }
        }
    }
}

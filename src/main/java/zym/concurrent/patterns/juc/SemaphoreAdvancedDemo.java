package zym.concurrent.patterns.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static zym.concurrent.patterns.juc.SemaphoreDemo.THREAD_SIZE;
import static zym.concurrent.patterns.juc.SemaphoreDemo.runSomething;

/**
 * @author 梁自强
 * @date 2019.09.20
 */
public class SemaphoreAdvancedDemo {

    public static void main(String[] args) throws InterruptedException {
        //新建一个拥有4个许可的信号量
        Semaphore semaphoreAdvance = new Semaphore(4);
//        testSemaphore(() -> new Advance(semaphoreAdvance));
        //等待上面执行完
//        Thread.sleep(5000);
//        System.out.println("+++++++++++++华丽的分割线+++++++++++++++");
        testSemaphore(()->new Final(semaphoreAdvance));
    }

    private static void testSemaphore(Supplier<Runnable> supplier) {

        for (int i = 0; i < THREAD_SIZE; i++) {
            Thread demoThread = new Thread(supplier.get());

            demoThread.start();
            //随机调用interrupt 方法模仿实际被中断
            if (ThreadLocalRandom.current().nextInt(THREAD_SIZE) > THREAD_SIZE / 2) {
                demoThread.interrupt();
            }
        }
    }
    static class Advance implements Runnable {

         Advance(Semaphore semaphoreAdvance) {
            this.semaphoreAdvance = semaphoreAdvance;
        }

        private Semaphore semaphoreAdvance;

        @Override
        public void run() {
            boolean isAcquire = false;
            try {
                //tryAcquire 如果 没有许可会立即返回false,否则会通过CAS 去修改被volatile修饰的许可总数即state
                while (!(isAcquire = semaphoreAdvance.tryAcquire())) {
                    Thread.sleep(100);
                }
                runSomething();
            } catch (InterruptedException e) {
                System.out.println(String.format("threadId:%s interrupt", Thread.currentThread().getId()));
                Thread.currentThread().interrupt();
            } finally {
                if (isAcquire) {
                    semaphoreAdvance.release();
                    System.out.println(String.format("current threadId:%s released a permit", Thread.currentThread().getId()));
                }
            }
        }
    }

    static class Final implements Runnable {

         Final(Semaphore semaphoreFinal) {
            this.semaphoreFinal = semaphoreFinal;
        }
        private Semaphore semaphoreFinal;

        @Override
        public void run() {
            try {
                semaphoreFinal.acquireUninterruptibly();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(String.format("[final] %s have interrupt", Thread.currentThread().getName()));
                    throw new InterruptedException();
                }
                runSomething();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphoreFinal.release();
                System.out.println(String.format("%s released a permit", Thread.currentThread().getName()));
            }
        }
    }
}

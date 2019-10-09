package zym.concurrent.patterns.juc;

import java.util.concurrent.Semaphore;

public class SemaphoreTestMain {
    public static void main(String[] args) throws InterruptedException {

        /**
         *
         * {@link Semaphore#acquireUninterruptibly} 方法 获取许可，如有许可则返回，
         * 若么有阻塞，在阻塞的过程中线程调用中断方法也不会影响线程的等待获取许可，但是在返回时该线程的中断状态会被设置为true
         * 测试步骤：
         * 1.创建一个拥有一个许可的信号量实例
         * 2.在主线程中acquire一个许可
         * 3.创建一个线程a去获取许可
         * 4.调用a.interrupt方法
         * 5.主线程释放许可
         */
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        Thread testThread = new Thread(() -> {
            semaphore.acquireUninterruptibly();
            //测试线程是否是中断状态
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("pass");
            } else {
                System.out.println("get error");
            }
            //释放信号量
            semaphore.release();
        });
        //启动测试线程
        testThread.start();
        //中断测试线程
        testThread.interrupt();
        //释放许可
        semaphore.release();
    }
}

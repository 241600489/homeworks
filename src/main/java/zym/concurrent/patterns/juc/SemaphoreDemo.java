package zym.concurrent.patterns.juc;

import java.util.concurrent.Semaphore;

/**
 * @author 梁自强
 * @date 2019.09.20
 */
public class SemaphoreDemo {

    public static final int THREAD_SIZE = 10;

    public static void  runSomething() {
        System.out.println(String.format("%s,current time is %d",
                Thread.currentThread().getName(), System.currentTimeMillis() / 1000));
    }

    public static void main(String[] args) throws InterruptedException {
        //创建一个包含4个许可证的信号量实例
        Semaphore semaphoreDemo = new Semaphore(4);
        for (int i = 0; i < THREAD_SIZE; i++) {
            //获取许可
            Thread demoThread = new Thread(() -> {
                try {
                    //获取许可
                    semaphoreDemo.acquire();
                    //操作资源
                    runSomething();
                } catch (InterruptedException e) {
                    //抛出InterruptedException 会将该线程的中断标志设置为false
                    Thread.currentThread().interrupt();
                } finally {
                    semaphoreDemo.release();
                }
            });
            //开启demo线程
            demoThread.start();

        }

    }
}

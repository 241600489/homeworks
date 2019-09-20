package zym.concurrent.patterns.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author 梁自强
 * @date 2019.09.20
 */
public class SemaphoreDemo {

    public static final int THREAD_SIZE = 10;

    public static void runSomething() throws InterruptedException {
        //模拟处理什么事
        Thread.sleep(1000);
        System.out.println(String.format("current threadId is %d,current time is %d",
                Thread.currentThread().getId(), System.currentTimeMillis() / 1000));
    }

    public static void main(String[] args) throws InterruptedException {
        //创建一个包含5个许可证的信号量实例
        Random random = new Random();
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

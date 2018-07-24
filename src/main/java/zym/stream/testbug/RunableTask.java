package zym.stream.testbug;

import java.util.concurrent.CountDownLatch;

/**
 * @Author unyielding
 * @date 2018/7/24 0024 19:00
 * @desc
 */
public class RunableTask implements Runnable {

    private CountDownLatch countDownLatch;

    public RunableTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("我阻塞了100ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}

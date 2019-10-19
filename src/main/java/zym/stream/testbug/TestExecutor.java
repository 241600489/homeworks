package zym.stream.testbug;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author unyielding
 * @date 2018/7/24 0024 18:44
 * @desc 多线程测试
 */
public class TestExecutor {
    private static final Logger log = Logger.getGlobal();
    private CountDownLatch countDownLatch;
    private ExecutorService service;
    @Before("")
    public void prepareData() {
        countDownLatch = new CountDownLatch(100);
        service = Executors.newFixedThreadPool(100);
    }

    @Test
    public void excutorTest() {
        for (int i = 0; i < 100; i++) {
            service.execute(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println("我阻塞了100ms");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void runCountDown() {
        for (int i = 0; i < 100; i++) {
            service.execute(new RunableTask(countDownLatch));
        }
        try {
            countDownLatch.await();
            log.info("执行成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.log(Level.WARNING,e,()->"抛出中断异常");
        }
    }
}

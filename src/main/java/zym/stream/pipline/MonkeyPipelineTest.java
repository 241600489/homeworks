package zym.stream.pipline;

import org.junit.Test;

/**
 * @Author unyielding
 * @date 2018/8/2 0002 20:31
 * @desc 管道模式测试
 */
public class MonkeyPipelineTest {
    @Test
    public void monkeyPipelineTest() {
        MonkeyPipeline<String> monkeyPipeline = new MonkeyPipeline<>();
        monkeyPipeline.addFirst(new TestHandler2());
        monkeyPipeline.addFirst(new TestHanler1());
        for (int i = 0; i < 10; i++) {
            monkeyPipeline.request("msg" + i);
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

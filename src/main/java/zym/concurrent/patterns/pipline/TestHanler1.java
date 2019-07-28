package zym.concurrent.patterns.pipline;

import java.util.logging.Logger;

/**
 * @Author unyielding
 * @date 2018/8/2 0002 18:49
 * @desc
 */
public class TestHanler1 implements Handler<String> {
    private final Logger log = Logger.getGlobal();
    @Override
    public void chanelRead(HandlerContext ctx, String msg) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = msg + "-handler";
        System.out.println(result);
        ctx.write(msg);
    }
}

package zym.concurrent.patterns.pipline;

/**
 * @Author unyielding
 * @date 2018/8/2 0002 18:53
 * @desc
 */
public class TestHandler2 implements Handler<String> {
    @Override
    public void chanelRead(HandlerContext<String> ctx, String msg) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = (String) (msg + "-handler2");
        System.out.println(result);
        ctx.write(result);
    }
}

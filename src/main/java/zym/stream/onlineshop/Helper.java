package zym.stream.onlineshop;

import java.util.Random;

/**
 * @Author 梁自强
 * @date 2018/7/20 0020 13:18
 * @desc 工具类
 */
public class Helper {
    private static final Random random = new Random();

    public static void delay() {
        try {
            Thread.sleep(random.nextInt(2000) + 500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

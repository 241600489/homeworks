package zym.stream.onlineshop;

import static zym.stream.onlineshop.Helper.delay;

/**
 * @Author unyielding
 * @date 2018/7/21 0021 9:30
 * @desc 折扣服务 枚举类型
 */
public class Discount {
    /** 枚举*/
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        /** 折扣百分比*/
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }
    //
    public static String applyDiscount(Quote quote) {
        delay();
        return quote.getShopName() + " price is " + apply(quote);
    }

    private static double apply(Quote quote) {
        return quote.getCode().percentage * quote.getPrice();
    }
}

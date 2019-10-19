package zym.interview.dayuwuxian;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * 题目：实现方法sqrt，求n的算术平方根。如sqrt(4) = 2, sqrt(10) = 3
 * 思路：
 * 使用牛顿迭代法
 * 得出 result=
 */
public class MySqrt {
    final static double ERP = 0.00001d;

    public long sqrt(long n) {

        if (n == 0L) {
            return 0L;
        }
        double lastValue = 1;
        double result = n;
        do {
            lastValue = result;
            result = lastValue / 2.0d + n / 2.0d / lastValue;
        } while (Math.abs(result - lastValue) > ERP);
        return new BigDecimal(result).setScale(0,BigDecimal.ROUND_HALF_UP).longValueExact();
    }

    @Test
    public void testSqrt() {
        MySqrt mySqrt = new MySqrt();
        assertEquals(2L, mySqrt.sqrt(4L));
        assertEquals(3L, mySqrt.sqrt(10L));
        assertEquals(0L, mySqrt.sqrt(0L));
    }

}

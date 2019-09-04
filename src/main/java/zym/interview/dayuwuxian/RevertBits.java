package zym.interview.dayuwuxian;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;

/**
 * 题目：给定一个32位整数 . 输出二进制表示反转后的值.例如 input 43261596 , return 964176192
 * 思路：
 * 1.第一个如果是1，翻转到32 为则  则 値为 2 的(32-1)次方
 * 2.第i位翻转之后为((n>>1)&1)*2^(32-i)次方
 * 3.则将每次翻转的値 加起来就可以了
 */
public class RevertBits {


    public int reverse(int n) {
        int a = 0;
        for (int i = 1; i <= 32; i++) {
            a += (n&1) * Math.pow(2, 32 - i);
            n = n >> 1;
        }
        return a;
    }

    @Test
    public void testReverse() {
        RevertBits revertBits = new RevertBits();
        assertEquals(964176192, revertBits.reverse(43261596));
    }
}

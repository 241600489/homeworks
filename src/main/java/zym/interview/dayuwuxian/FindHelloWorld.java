package zym.interview.dayuwuxian;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 题目：判断从一个字符串中是否能抓取出helloworld，可以不连续但是一定要保持顺序
 * 如helllllllo wwwwwwworld 但不能是helolllllworld。
 * 思路：
 * 定义辅助参数
 * fromIndex: 从 text 中 fromIndex 位置处开始查找
 * lastIndex:  记录上一个helloworld中字符在text 中的位置，保证单词的顺序
 * * 1.遍历 hellowworld 取出每个字符 从text 里查找返回下标,要从指定start 位置开始查找
 *   2.判断第一步返回的下标 是否小于零或者是否小于上一个helloworld 中字符的小标
 *   3.更新 上一个下标的値
 *   循环1,2,3步
 */
public class FindHelloWorld {
    final String HELLO_WORLD = "helloworld";

    public boolean isHelloWorld(String text) {
        //判空或者原字符串的长度小于 helloworld
        if (Objects.isNull(text) && HELLO_WORLD.length() > text.length()) {
            return false;
        }
        //从 text 中 fromIndex 位置处开始查找
        int fromIndex = -1;
        //记录上一个helloworld中字符在text 中的位置，保证单词的顺序
        int lastIndex = 0;
        /**
         * 1.遍历 hellowworld 取出每个字符 从text 里查找返回下标,要从指定start 位置开始查找
         * 2.判断第一步返回的下标 是否小于零或者是否小于上一个helloworld 中字符的小标
         * 3.更新 上一个下标的値
         * 循环1,2,3步
         */

        for (int i = 0; i < HELLO_WORLD.length(); i++) {

            fromIndex = text.indexOf(HELLO_WORLD.charAt(i), fromIndex + 1);
            if (fromIndex < 0 || fromIndex < lastIndex) {
                return false;
            }
            lastIndex = fromIndex;
        }
        return true;
    }

    @Test
    public void testIsHelloWorld() {
        FindHelloWorld findHelloWorld = new FindHelloWorld();
        assertTrue(findHelloWorld.isHelloWorld("helllllllo wwwwwwworld"));
        assertFalse(findHelloWorld.isHelloWorld("“helolllllworld"));
    }
}

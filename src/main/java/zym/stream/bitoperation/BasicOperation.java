package zym.stream.bitoperation;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

/**
 * @Author unyielding
 * @date 2018/8/11 0011 18:40
 * @desc 位的基本运算
 */
public class BasicOperation {
    int mask=0;
    int enabled = 1 << 0;
    int pressed = 1 << 1;
    int armed = 1 << 2;
    int selected = 1 << 3;

    @Before("before")
    public void before() {
        setEnabled(false);

    }

    public void setEnabled(boolean state) {
        if (state == isEnabled()) {
            return;
        }
        if (state) {
            mask |= enabled;
            System.out.println(mask);
        }else {
            mask &= ~enabled;
            System.out.println(mask);
        }
    }

    private boolean isEnabled() {
        return (mask & enabled) != 0;
    }

    @Test
    public void testSetEnable() {
        setEnabled(true);
        setEnabled(false);

    }

    @Test
    public void testOperationFly() {
        int position = 0;
        position *= 2;
        System.out.println(position);
    }
}

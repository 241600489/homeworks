package zym.concurrent.patterns.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @author liangziqiang
 * @date 2019.10.23
 */
public class UnsafeUtil {
    /**
     * 通过反射获取Unsafe 实例
     *
     * @return 返回Unsafe 实例 使用Optional包装,如果有异常返回空的
     */
    public static Optional<Unsafe> getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return Optional.of((Unsafe) f.get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

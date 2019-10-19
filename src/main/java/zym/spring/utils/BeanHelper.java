package zym.spring.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 封装BeanUtils
 *
 * @author liangziqiang
 * @date 2019.10.19
 */
public class BeanHelper {
    /**
     * 拷贝指定源列表 到 指定目标bean类型，并返回目标bean列表
     *
     * @param targetClazz 目标bean 类型
     * @param sourceList  源bean 列表
     * @param <T>         指目标bean类型
     * @param <D>         指代源bean类型
     * @return 返回指定目标bean类型的列表
     */
    @Deprecated
    public static <T, D> List<T> copyForList(Class<T> targetClazz, List<D> sourceList) {
        if (Objects.isNull(sourceList) || Objects.isNull(targetClazz)) {
            return null;
        }
        return sourceList.stream().filter(Objects::nonNull).map(d -> {
            T t = null;
            try {
                //使用反射构建对象
                t = targetClazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (Objects.nonNull(t)) {
                BeanUtils.copyProperties(d, t);
            }
            return t;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 拷贝指定源列表 到 指定目标bean类型，并返回目标bean列表
     * List<UserDto> userDtos = BeanHelper.copyForList(UserDto::new, userDos);
     * @param targetSupplier 目标bean对象提供者
     * @param sourceList     源bean 列表
     * @param <T>            指目标bean类型
     * @param <D>            指代源bean类型
     * @return 返回指定目标bean类型的列表
     */
    public static <T, D> List<T> copyForList(Supplier<T> targetSupplier, List<D> sourceList) {
        if (Objects.isNull(sourceList) || Objects.isNull(targetSupplier)) {
            return null;
        }
        return sourceList.stream().filter(Objects::nonNull).map(d ->
                BeanHelper.copyForBean(targetSupplier,d))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 拷贝指定bean 到目标bean
     * 用法：
     * UserDto userDto=BeanHelper.copyForBean(UserDto::new, useDo);
     * @param targetSupplier
     * @param d
     * @param <T>
     * @param <D>
     * @return
     */
    public  static <T, D> T  copyForBean(Supplier<T> targetSupplier, D d) {
        if (Objects.isNull(targetSupplier) || Objects.isNull(d)) {
            return null;
        }
        T t = null;
        t = targetSupplier.get();
        if (Objects.nonNull(t)) {
            BeanUtils.copyProperties(d, t);
        }
        return t;
    }
}

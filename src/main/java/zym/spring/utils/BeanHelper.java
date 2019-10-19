package zym.spring.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
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
}

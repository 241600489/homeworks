package zym.reflect;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeanHelper {
    /**
     * 拷贝指定bean 到目标bean
     * 用法：
     * UserDto userDto=BeanHelper.copyForBean(UserDto::new, useDo);
     *
     * @param targetSupplier
     * @param source
     * @param <T>
     * @param <D>
     * @return
     */
    public static <T, D> T copyForBean(Supplier<T> targetSupplier, D source) {
        if (Objects.isNull(source) || Objects.isNull(targetSupplier)) {
            throw new IllegalArgumentException("method copyForBean's parameters can not be null");
        }
        T target = targetSupplier.get();
        if (Objects.isNull(target)) {
            throw new IllegalArgumentException("the parameter 'targetSupplier''s 'get' method can not return null ");
        }

        Collection<PropertyDec> targetPropertyDecs = PropertiesCache.getPropertyDecs(target);
        targetPropertyDecs.forEach(propertyDec -> {
            BiConsumer setter = propertyDec.getSetter();
            PropertyDec sourcePropertyDec = PropertiesCache.getPropertyDecs(source.getClass(), propertyDec.getField().getName());
            if (Objects.nonNull(sourcePropertyDec)) {
                setter.accept(target, sourcePropertyDec.getGetter().apply(source));
            }
        });
        return target;
    }




}

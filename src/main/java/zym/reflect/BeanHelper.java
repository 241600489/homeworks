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
            propertyDec.setGetter();
        });

//        Field[] declaredFields = target.getClass().getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            //过滤 final /static  修饰的字段
//            if (!isNeedCopy(declaredField)) {
//                continue;
//            }
//            declaredField.setAccessible(true);
//            //获取 target 中字段的set 方法
//            Optional<BiConsumer> biConsumerOptional = fetchSetter(target, declaredField);
//            biConsumerOptional.ifPresent(biConsumer -> {
//                //获取source 中 的get 方法
//                Optional<Function> getterOptional = fetchGetter(source, declaredField);
//                if (getterOptional.isPresent()) {
//                    biConsumer.accept(target, getterOptional.get().apply(source));
//                }
//            });
//        }

        return target;
    }




}

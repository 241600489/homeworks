package zym.reflect;


import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.invoke.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PropertiesCache {
    private static MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final Map<Class<?>, Map<String, PropertyDec>> METHOD_CACHE = new ConcurrentReferenceHashMap<>();

    public static Collection<PropertyDec> getPropertyDecs(Object bean) {
        return PropertiesCache.forClass(bean.getClass()).values();
    }

    private static boolean isNeedCopy(Field declaredField) {
        int modifiers = declaredField.getModifiers();
        int finalAndStatic = Modifier.FINAL | Modifier.STATIC;

        return (modifiers & finalAndStatic) == 0;
    }

    private static Optional<BiConsumer> fetchSetter(Class<?> clazz, Field declaredField) {
        try {
            String methodName = FieldMethodPrefix.SET.getCode() + declaredField.getName().substring(0, 1).toUpperCase(Locale.ENGLISH) + declaredField.getName().substring(1);
            MethodHandle setter = lookup.findVirtual(clazz, methodName, MethodType.methodType(void.class, declaredField.getType()));

            CallSite accept = LambdaMetafactory.metafactory(lookup, "accept",
                    MethodType.methodType(BiConsumer.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    setter,
                    setter.type());
            return Optional.ofNullable((BiConsumer) accept.getTarget().invokeExact());
        } catch (LambdaConversionException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 获取给定对象的给定字段的get 方法
     *
     * @param clazz        给定类
     * @param declaredField 给定的字段
     * @return
     */
    private static Optional<Function> fetchGetter(Class<?> clazz, Field declaredField) {
        // 如果 字段是boolean 类型则 是 is 开头否则是get 开头
        String methodName = "";
        Class<?> fieldType = declaredField.getType();
        if (fieldType == boolean.class) {
            methodName = FieldMethodPrefix.IS.getCode() + declaredField.getName().substring(0, 1).toUpperCase(Locale.ENGLISH) + declaredField.getName().substring(1);
        } else {
            methodName = FieldMethodPrefix.GET.getCode() + declaredField.getName().substring(0, 1).toUpperCase(Locale.ENGLISH) + declaredField.getName().substring(1);
        }
        try {
            CallSite readFieldFromSource = LambdaMetafactory.metafactory(
                    lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    lookup.findVirtual(clazz, methodName, MethodType.methodType(declaredField.getType())),
                    MethodType.methodType(declaredField.getType(), clazz));
            return Optional.ofNullable(((Function) readFieldFromSource.getTarget().invokeExact()));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable te) {
            te.printStackTrace();
        }
        return Optional.empty();
    }

    public static PropertyDec getPropertyDecs(Class<?> clazz, String name) {
        Map<String, PropertyDec> propertyDecMap = PropertiesCache.forClass(clazz);

        return propertyDecMap.get(name);
    }

    private static Map<String, PropertyDec> forClass(Class<?> clazz) {
        if (!METHOD_CACHE.containsKey(clazz)) {
            ConcurrentHashMap<String, PropertyDec> beanNameReadMethod = new ConcurrentHashMap<>();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                //过滤 final /static  修饰的字段
                if (!isNeedCopy(declaredField)) {
                    continue;
                }
                PropertyDec propertyDec = new PropertyDec();
                propertyDec.setField(declaredField);
                propertyDec.setClazz(clazz);
                Optional<Function> readMethodOptional = fetchGetter(clazz, declaredField);
                readMethodOptional.ifPresent(propertyDec::setGetter);
                Optional<BiConsumer> writeMethodOptional = fetchSetter(clazz, declaredField);
                writeMethodOptional.ifPresent(propertyDec::setSetter);
                if (Objects.nonNull(propertyDec.getGetter()) && Objects.nonNull(propertyDec.getSetter())) {
                    beanNameReadMethod.put(declaredField.getName(), propertyDec);
                }
            }
            METHOD_CACHE.putIfAbsent(clazz, beanNameReadMethod);
        }
        return METHOD_CACHE.get(clazz);
    }
}

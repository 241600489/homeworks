package zym.reflect;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PropertyDec {

    private Class<?> clazz;

    private Field field;

    private Function getter;

    private BiConsumer setter;


    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Function getGetter() {
        return getter;
    }

    public void setGetter(Function getter) {
        this.getter = getter;
    }

    public BiConsumer getSetter() {
        return setter;
    }

    public void setSetter(BiConsumer setter) {
        this.setter = setter;
    }
}

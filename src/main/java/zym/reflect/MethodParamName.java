package zym.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 反射获取方法名称
 */
public class MethodParamName {
    public static void main(String[] args) {
        MethodParamName methodParamName = new MethodParamName();
        Map<String, MonkeyMethod> stringListMap = methodParamName.fetchMethodParameterAndIndex(MethodParamName.class);
        stringListMap.values().forEach(System.out::println);
    }

    public Map<String, MonkeyMethod> fetchMethodParameterAndIndex(Class<?> clazz) {
        HashMap<String, MonkeyMethod> map = new HashMap<>();
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            String methodName = declaredMethod.getName();
            Parameter[] parameters = declaredMethod.getParameters();
            List<MethodParameter> methodParameters = new LinkedList<>();
            for (int i = 0; i < parameters.length; i++) {
                MethodParameter methodParameter = new MethodParameter();
                methodParameter.setIndex(i);
                methodParameter.setName(parameters[i].getName() );
                methodParameter.setType(parameters[i].getType().getName());

                methodParameters.add(methodParameter);
            }
            MonkeyMethod monkeyMethod = new MonkeyMethod();
            monkeyMethod.setMethod(methodName);
            monkeyMethod.setMethodParameters(methodParameters);
            map.put(methodName, monkeyMethod);
        }
        return map;
    }

    public void demo(int a, String b) {

    }

    public void demo1(String b, int a) {

    }
}

package zym.reflect;

import java.util.List;

public class MonkeyMethod {
    /**
     * 方法名称
     */
    private String method;

    /**
     * 方法参数列表
     */
    private List<MethodParameter> methodParameters;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<MethodParameter> getMethodParameters() {
        return methodParameters;
    }

    public void setMethodParameters(List<MethodParameter> methodParameters) {
        this.methodParameters = methodParameters;
    }

    @Override
    public String toString() {
        return "MonkeyMethod{" +
                "method='" + method + '\'' +
                ", methodParameters=" + methodParameters +
                '}';
    }
}

package zym.reflect;

/**
 * 方法参数
 * @author liangziqiang
 */
public class MethodParameter {
    private String name;
    private int index;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MethodParameter{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", type='" + type + '\'' +
                '}';
    }
}

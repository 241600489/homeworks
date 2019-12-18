package zym.reflect;

public enum FieldMethodPrefix {
    /**
     * 当字段为boolean值时,获取值时为is 开头
     */
    IS("is"),
    /**
     * 当字段为非 boolean 值时,获取值为get开头
     */
    GET("get"),

    /**
     * set field  method prefix
     */
    SET("set");

    private String code;

    FieldMethodPrefix(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

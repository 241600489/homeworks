package zym.stream.model;

/**
 * @Author 梁自强
 * @date 2018/7/18 0018 17:32
 * @desc 是否都是数字
 */
public class IsNumeric implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s.matches("\\d+");
    }

}

package zym.stream.model;

/**
 * @Author 梁自强
 * @date 2018/7/18 0018 17:31
 * @desc  是否是都是小写字母判定
 */
public class IsAllLowerCase implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s.matches("[a-z]+");
    }

}

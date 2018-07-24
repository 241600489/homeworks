package zym.stream.model;

/**
 * @Author 梁自强
 * @date 2018/7/18 0018 17:34
 * @desc 验证
 */
public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }
}

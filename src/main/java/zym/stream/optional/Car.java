package zym.stream.optional;

import java.util.Optional;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 6:33
 * @desc 车
 */
public class Car {
    public Optional<Ensurence> getEnsurence() {
        return ensurence;
    }

    public void setEnsurence(Optional<Ensurence> ensurence) {
        this.ensurence = ensurence;
    }

    private Optional<Ensurence> ensurence;//保险名称
}

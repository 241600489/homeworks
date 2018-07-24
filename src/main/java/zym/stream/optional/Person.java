package zym.stream.optional;

import java.util.Optional;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 6:32
 * @desc 人
 */
public class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

}

package zym.stream;

import org.junit.Before;
import org.junit.Test;
import zym.stream.bean.CaloricLevel;
import zym.stream.bean.Dish;
import zym.stream.bean.Type;
import zym.stream.fileaccess.DataHandler;
import zym.stream.fileaccess.Device;
import zym.stream.fileaccess.FileAccess;
import zym.stream.forkjoin.ForkJoinSumCalculator;
import zym.stream.model.IsNumeric;
import zym.stream.model.ValidationStrategy;
import zym.stream.optional.Car;
import zym.stream.optional.Ensurence;
import zym.stream.optional.Person;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.IntPredicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @Author 梁自强
 * @date 2018/7/10 0010 20:59
 * @desc collections
 */
public class CollectionMethod {
    /**
     * reverseOrder 倒序
     */
    @Test
    public void methodOne() {
        Comparator<Integer> cm = Collections.reverseOrder(((o1, o2) -> o1 - o2));
        System.out.println(cm.compare(5, 10) > 0);

    }

    /**
     * singletonMap 构造一个 map
     */
    @Test
    public void methodTwo() {
        Map<String, String> map = Collections.singletonMap("nih", "scs");
        System.out.println(map.get("nih"));

    }

    static int[] stream = {1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3,
            4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6,
            4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6};

    static Integer[] strea = {1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3,
            4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6,
            4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6, 4, 6, 4, 5, 5, 6, 4, 5, 6, 1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6,
            1, 2, 3, 4, 6, 4, 5, 5, 6, 4, 5, 6};
    @Test
    public void methodThree() {
        System.out.println(stream.length);

        List<Integer> list = new ArrayList<>();
        Arrays.stream(stream).parallel().peek(list::add).map(t -> t + 2).count();
        System.out.println(list.size());
    }

    @Test
    public void methodFour() {
        Map<String, Boolean> map = new HashMap<>();
        boolean b = (map != null ? map.get("test") : false);
        System.out.println(b);
    }

    @Test
    public void methodFive() {
        IntPredicate intPredicate = i -> i > 1;
        long l1 = System.currentTimeMillis();
        System.out.println(Stream.of(strea)
                .filter(i -> i > 4).count());
        System.out.println("l1 = " + (System.currentTimeMillis() - l1));

    }

    @Test
    public void methodSix() {
        long l2 = System.currentTimeMillis();

        System.out.println(Arrays.stream(strea).filter(i -> i > 4).count());
        System.out.println("l2 = " + (System.currentTimeMillis() - l2));
    }

    List<Dish> menu;
    ValidationStrategy v;

    @Before
    public void before() {
        menu = Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("salmon", false, 450, Type.FISH));

        v = new IsNumeric();
    }

    @Test
    public void methodSeven() {

        Map<Type, List<Dish>> map = menu.parallelStream().collect(groupingBy(Dish::getType));

        System.out.println(map.toString());

    }

    @Test
    public void SumInt() {
        IntSummaryStatistics instatics = menu.parallelStream().collect(summarizingInt(Dish::getCalories));
        System.out.println(instatics.getMax());
        System.out.println(instatics.getCount());

    }

    @Test
    public void groupDuoji() {
        Map<Type, Map<CaloricLevel, List<Dish>>> map = menu.parallelStream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() < 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })));

        System.out.println(map.toString());
    }

    @Test
    public void groupByAndCounting() {
        Map<Type, Long> longMap = menu.parallelStream()
                .collect(groupingBy(Dish::getType, counting()));
        System.out.println(longMap.toString());

    }

    @Test
    public void groupByAndMaxBy() {
        Map<Type, Optional<Dish>> calories = menu.parallelStream()
                .collect(groupingBy(Dish::getType, maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println(calories.toString());

    }

    @Test
    public void collectAndThen() {
        Map<Type, Dish> map = menu.parallelStream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                maxBy(Comparator.comparing(Dish::getCalories)),
                                Optional::get)));
        System.out.println(map.toString());

    }

    @Test
    public void sumInt() {
        Map<Type, Set<CaloricLevel>> setMap = menu.parallelStream()
                .collect(groupingBy(Dish::getType, mapping(dish -> {
            if (dish.getCalories() < 400) {
                return CaloricLevel.DIET;
            } else if (dish.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
            } else return CaloricLevel.FAT;
        }, toSet())));
        System.out.println(setMap.toString());
    }

    @Test
    public void comparingInt() {

    }

    @Test
    public void forkJoin() {
        long re = ForkJoinPool.commonPool().invoke(
                new ForkJoinSumCalculator(LongStream.rangeClosed(0, 1000000).toArray(),
                        0, 1000000));
        System.out.println("计算结果：" + re);
    }

    @Test
    public void isAlllower() {
        System.out.println(v.execute("SADDaa"));
    }

    @Test
    public void isNumberic() {
        System.out.println(v.execute("1123"));
        ValidationStrategy num = s ->
                s.matches("\\d+");
        System.out.println(num.execute("niaho "));
        ValidationStrategy isUpp = s -> s.matches("[A-Z]+");
        System.out.println(isUpp.execute("AGDDJD"));
    }

    @Test
    public void getEnsurenceName() {
        Person person = new Person();
        Car car = new Car();
        Ensurence ensurence = new Ensurence();
        ensurence.setName("太平洋保险公司");
        car.setEnsurence(Optional.empty());
        person.setCar(Optional.of(car));
        Optional<Person> optional = Optional.of(person);
        Person orElse = optional.orElse(new Person());

        String enSurenceName = optional.flatMap(Person::getCar)
                .flatMap(Car::getEnsurence)
                .map(Ensurence::getName).orElse("Unknown");

        System.out.println(enSurenceName);
    }

    @Test
    public void aquireDataListTest() {
        Optional<List<Device>> list = new FileAccess().aquireDataList();
        DataHandler handler = new DataHandler();
        handler.handleList(list);
    }

    @Test
    public void updateDeviceName() {
        Optional<List<Device>> list = new FileAccess().aquireDeviceName();
        DataHandler handler = new DataHandler();
        handler.handleDat(list);
    }
    @Test
    public void insertRoadData() {
        Optional<List<Device>> list = new FileAccess().aquireRoad();
        DataHandler handler = new DataHandler();
        handler.handleRoadData(list);
    }
    @Test
    public void insertDirectionData() {
        Optional<List<Device>> list = new FileAccess().aquireDirection();
        DataHandler handler = new DataHandler();
        handler.handleDirection(list);
    }

    @Test
    public void testParallelNode() {
        Stream.of(strea).parallel().forEachOrdered(System.out::println);

    }
}

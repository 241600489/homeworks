package zym.stream.bean;

/**
 * @Author 梁自强
 * @date 2018/7/15 0015 10:15
 * @desc 菜单
 */
public class Dish {


    private final Type type ;
    private final Integer calories;
    private final  String name;
    private final boolean vegetarian;

    public Dish( String name,boolean vegetarian, Integer calories,Type type) {
        this.type = type;
        this.calories = calories;
        this.name = name;
        this.vegetarian = vegetarian;
    }

    public Type getType() {
        return type;
    }

    public Integer getCalories() {
        return calories;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "type=" + type +
                ", calories=" + calories +
                ", name='" + name + '\'' +
                ", vegetarian=" + vegetarian +
                '}';
    }
}

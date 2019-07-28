package zym.concurrent.patterns.imutable;

/**
 * @Author unyielding
 * @date 2018/8/3 0003 21:52
 * @desc
 */
public final class Location {
    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

package zym.stream.imutable;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 7:08
 * @desc
 */
public class ManipulatorLocation {
    public Location changeStateTo(double x, double y) {
        return new Location(x, y);
    }
}

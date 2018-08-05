package zym.stream.twophasedclose;

import java.util.concurrent.Callable;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 15:10
 * @desc
 */
public abstract  class GuardedAction<V> implements Callable<V> {
    private final Predicate guard;

    public GuardedAction(Predicate guard) {
        this.guard = guard;
    }
}

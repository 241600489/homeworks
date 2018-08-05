package zym.stream.twophasedclose;

import java.util.concurrent.Callable;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 14:58
 * @desc
 */
public interface Blocker  {
    <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception;

    void signalAfter(Callable<Boolean> stateOperation) throws Exception;
}

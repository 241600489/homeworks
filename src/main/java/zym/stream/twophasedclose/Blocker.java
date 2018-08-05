package zym.stream.twophasedclose;

import java.util.concurrent.Callable;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 14:58
 * @desc
 */
public interface Blocker  {
    void callWithGuard(GuardedAction<Void> guardedAction) throws Exception;

    void signAfter(Callable<Boolean> stateOperation) throws Exception;
}

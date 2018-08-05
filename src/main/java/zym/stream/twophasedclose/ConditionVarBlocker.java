package zym.stream.twophasedclose;

import java.util.concurrent.Callable;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 14:59
 * @desc
 */
public class ConditionVarBlocker implements Blocker {
    @Override
    public void callWithGuard(GuardedAction<Void> guardedAction) {

    }

    @Override
    public void signAfter(Callable<Boolean> stateOperation) throws Exception {

    }

}

package zym.concurrent.patterns.juc;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 这里是一个不可重入的互斥锁类，它使用值0表示解锁状态，使用值1表示锁定状态。
 * 虽然非重入锁并不严格要求记录当前所有者线程，但是这个类这样做是为了更容易监视使用情况。
 * @author liangziqiang
 * @date 2019.10.19
 */
public class Mutex implements Lock, Serializable {
    private final Sync sync = new Sync();

    /**
     * 继承AQS 当做内部帮助类
     */
    private static class Sync extends AbstractQueuedSynchronizer{
        /**
         * 判断锁是否被占有
         * @return 返回true：代表锁被持有，否则返回false
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        /**
         * 尝试获取锁
         * 如果state为零 返回true
         * @param acquires 只允许传1
         */
        @Override
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return Boolean.TRUE;
            }

            return Boolean.FALSE;
        }

        /**
         * 是否锁
         * 通过将state设置为零来释放锁
         * @param releases 该值为1
         * @return
         */
        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 创建条件对象
         * @return
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException { sync.acquireInterruptibly(1);}

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1); }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));}

    @Override
    public void unlock() {
        sync.release(1);}

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isHoldTheLock() {
        return sync.isHeldExclusively();
    }

}

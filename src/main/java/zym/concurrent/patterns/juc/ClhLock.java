package zym.concurrent.patterns.juc;

import sun.misc.Unsafe;

import java.util.Objects;
import java.util.concurrent.locks.LockSupport;

/**
 * CLH锁：一个种自旋锁，通过先进先出确保无饥饿的和公平的锁
 */
public class ClhLock {
    private static Unsafe unsafe = UnsafeUtil.getUnsafe().orElse(null);
    /**
     * 0表示未锁住
     * 1表示锁住
     */
    private volatile int state = 0;

    /**
     * 获得锁节点
     * 开始为空
     */
    private transient volatile Node head;

    /**
     * 尾部节点
     * 开始为空
     */
    private transient volatile Node tail;

    private final static long headOffset;

    private final static long tailOffset;

    private final static long stateOffset;
    /**
     * 独占锁拥有者线程
     */
    private Thread exclusiveOwnerThread;

    static {
        try {
            if (Objects.isNull(unsafe)) {
                throw new IllegalStateException("Unsafe instance has not initialized");
            }
            headOffset = unsafe.objectFieldOffset(ClhLock.class.getDeclaredField("headOffset"));
            tailOffset = unsafe.objectFieldOffset(ClhLock.class.getDeclaredField("tailOffset"));
            stateOffset = unsafe.objectFieldOffset(ClhLock.class.getDeclaredField("stateOffset"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public ClhLock() {
    }

    /**
     * 尝试获取锁，如果没有获取则加入等待队列
     */
    public void lock() {
        if (!tryAcquire(1)) {
            acquire(1);
        }
    }

    private void acquire(int arg) {
        Node node = addWaiter(Thread.currentThread());
        for (; ; ) {
            Node h = head;
            if (node.prev == h && tryAcquire(arg)) {
                setHead(node);
                return;
            }
            LockSupport.park(node.thread);
        }
    }

    private void setHead(Node node) {
        Node h = head;
        if (compareAndSetHeadOrTail(headOffset, h, node)) {
            node.prev = null;
            node.thread = null;
        }
    }

    private Node addWaiter(Thread currentThread) {
        Node node = new Node(currentThread);
        Node t = tail;

        if (t != null) {
            node.prev = t;
            if (compareAndSetHeadOrTail(tailOffset, t, node)) {
                t.next = node;
                return node;
            }
        }
        return enq(node);
    }

    private Node enq(Node node) {
        for (; ; ) {
            Node t = tail;
            if (t == null) {
                if (compareAndSetHeadOrTail(headOffset, null, new Node())) {
                    tail = head;
                }
            } else {
                node.prev = t;
                if (compareAndSetHeadOrTail(tailOffset, t, node)) {
                    t.next = node;
                    return node;
                }
            }
        }
    }

    protected boolean tryAcquire(int arg) {
        assert arg == 1;
        int tempState = getState();
        if (compareAndSetState(tempState, arg)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    protected boolean compareAndSetState(int oldValue, int expectValue) {
        return unsafe.compareAndSwapInt(this, stateOffset, oldValue, expectValue);
    }

    private boolean compareAndSetHeadOrTail(long offset, Node oldValue, Node expectValue) {
        return unsafe.compareAndSwapObject(this, offset, oldValue, expectValue);
    }

    /**
     * 释放锁
     */
    public void unLock() {

    }

    /**
     * 队列的一个元素
     */
    class Node {
        /**
         * 包含哪个线程，创建时实例化
         */
        private Thread thread;

        /**
         * 前驱
         */
        private Node prev;
        /**
         * 后继
         */
        private Node next;

        public Node(Thread thread) {
            this.thread = thread;
        }

        public Thread getThread() {
            return thread;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node() {
        }
    }

    public int getState() {
        return state;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
}

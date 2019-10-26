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
     * 独占锁拥有者线程
     */
    private Thread exclusiveOwnerThread;

    /**
     * 尾部节点
     * 开始为空
     */
    private transient volatile Node tail;

    /**
     * 为了CAS 操作而设置的变量.
     * 用下面这几个参数获取对应实体上对应字段的地址，充当CAS 第一个参数
     */
    private final static long headOffset;

    private final static long tailOffset;

    private final static long stateOffset;


    static {
        try {
            if (Objects.isNull(unsafe)) {
                throw new IllegalStateException("Unsafe instance has not initialized");
            }
            headOffset = unsafe.objectFieldOffset(ClhLock.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(ClhLock.class.getDeclaredField("tail"));
            stateOffset = unsafe.objectFieldOffset(ClhLock.class.getDeclaredField("state"));
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
//        if (!tryAcquire(1)) {
            acquire(1);
//        }
    }

    private void acquire(int arg) {
        Node node = addWaiter(Thread.currentThread());
        for (; ; ) {
            Node h = head;
            if (node.prev == h && tryAcquire(arg)) {
                System.out.println("acquire lock thread:" + Thread.currentThread().getName());
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

    /**
     * 将线程入队
     * @param currentThread 将要入队的线程
     * @return
     */
    private Node addWaiter(Thread currentThread) {
        //新建一个节点代表当前线程
        Node node = new Node(currentThread);
        Node t = tail;
        //判断尾部节点是否为空,开始时尾部节点为空
        if (t != null) {
            //尾部节点不为空则将尾部节点赋给当前节点的前驱
            node.prev = t;
            //将自己设置为尾部节点,可能不成功，会被其它线程先一步设置，若设置不了则会进入下面的enq
            if (compareAndSetHeadOrTail(tailOffset, t, node)) {
                t.next = node;
                return node;
            }
        }
        //若尾部节点为空（第一个线程进来），或者将当前节点设置为尾部节点失败
        return enq(node);
    }

    private Node enq(Node node) {
        //轮询
        for (; ; ) {
            Node t = tail;
            //若尾部节点为空，则设置下头节点和为节点
            if (t == null) {
                if (compareAndSetHeadOrTail(headOffset, null, new Node())) {
                    tail = head;
                }
            } else {
                //尾部节点不为空则将尾部节点赋给当前节点的前驱
                node.prev = t;
                //将自己设置为尾部节点,可能不成功，会被其它线程先一步设置，若设置不了则会进入下面的
                if (compareAndSetHeadOrTail(tailOffset, t, node)) {
                    //将当前节点即尾部节点设置为上一个尾部节点的后继
                    t.next = node;
                    return node;
                }
            }
        }
    }

    protected boolean tryAcquire(int arg) {
        assert arg == 1;
        int tempState = getState();
        if (tempState == 0 && compareAndSetState(tempState, arg)) {
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
        release();
    }

    protected void release() {
        //尝试释放锁
        if (tryRelease(1)) {
            Node h = head;
            if (h != null) {
                //解除后继的阻塞状态
                unParkSuccessor();
            }
        }
    }

    private void unParkSuccessor() {
        //解除head 后继的阻塞状态
        Node n = head.next;
        //下面逻辑暂时不管，不会出现这种情况
        if (n == null) {
            for (Node prev = tail.prev; prev != null; prev = prev.prev) {
                if (prev != head) {
                    n = prev;
                }
            }
        }

        if (Objects.nonNull(n)) {
            LockSupport.unpark(n.thread);
        }
    }

    private boolean tryRelease(int arg) {
        assert arg == 1;
        int tempState = getState();
        if (tempState == 1 && compareAndSetState(tempState, 0)) {
            setExclusiveOwnerThread(null);
            return true;
        }
        return false;
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

package zym.concurrent.patterns.juc;

import sun.misc.Unsafe;

import java.util.Objects;

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

    private final  static  long headOffset;

    private final static long tailOffset;

    private final static long stateOffset;

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

    public ClhLock() { }

    /**
     * 尝试获取锁，如果没有获取则加入等待队列
     */
    public void lock() {

    }

    /**
     * 释放锁
     */
    public void unLock() {

    }

    /**
     * 队列的一个元素
     */
    class Node{
        /**
         * 包含哪个线程，创建时实例化
         */
        private Thread thread;

        /**
         * 前驱
         */
        private Node prev;

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
    }
}

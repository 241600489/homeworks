package zym.collections;

/**
 * 环形队列 使用数组实现
 */
public class CircleQueue<T> {
    /**
     * 使用数组装填队列元素
     */
    private T[] elements;

    /**
     * 队列的最大长度
     */
    private int maxSize;

    /**
     * head 指针，指向队列首部元素
     */
    private int head;

    /**
     * tail 指针,指向队列尾部元素
     */
    private int tail;

    public CircleQueue(int size) {
        this.maxSize = size;
        this.elements = (T[]) new Object[size];
    }

    /**
     * 判断环形队列是否已满
     * (tail+1)%maxSize == head 表示已满
     *
     * @return 返回当前队列是否已满
     */
    public boolean isFull() {
        return (tail + 1) % maxSize == head;
    }

    /**
     * 判断环形队列时否为空
     * head == tail
     *
     * @return 返回当前队列是否为空
     */
    public boolean isEmpty() {
        return head == tail;
    }

    /**
     * 取出队列首部元素
     *
     * @return 返回 队列首部元素
     * @throws ArrayIndexOutOfBoundsException 若队列为空
     */
    public T take() throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        T t = elements[head];
        head = (head + 1) % maxSize;
        return t;
    }

    /**
     * 往队列尾部存放元素
     *
     * @param t 放进环形队列的元素
     * @throws ArrayIndexOutOfBoundsException 若队列已满 则抛出指针越界异常
     */
    public void put(T t) throws ArrayIndexOutOfBoundsException {
        if (isFull()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        elements[tail] = t;
        tail = (tail + 1) % maxSize;
    }

    /**
     * 获取队列里有多少个元素
     * @return  环形队列中的元素个数
     */
    public int size() {

        if (isEmpty()) {
            return 0;
        }
        if (tail > head) {
           return tail - head;
        }else {
            return maxSize + tail  - head;
        }

    }
}

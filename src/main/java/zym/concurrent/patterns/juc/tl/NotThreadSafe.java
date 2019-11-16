package zym.concurrent.patterns.juc.tl;

/**
 * @author liangziqiang
 * @date 2019.11.16
 */
public class NotThreadSafe {
    private ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> Integer.MIN_VALUE);

    public void increment() {
        Integer countValue = count.get();
        countValue++;
        count.set(countValue);
    }

    public void decrement() {
        Integer countValue = count.get();
        countValue--;
        count.set(countValue);
    }

    public int getValue() {
        return count.get();
    }

    public void remove() {
        count.remove();
    }

    public static void main(String[] args) {
        NotThreadSafe notThreadSafe = new NotThreadSafe();
        new Thread(() -> {
            try {
                notThreadSafe.increment();
                System.out.println("increment i=" + notThreadSafe.getValue());
                notThreadSafe.decrement();
                System.out.println("decrement i=" + notThreadSafe.getValue());
            } finally {
                notThreadSafe.remove();
            }
        }).start();
    }
}

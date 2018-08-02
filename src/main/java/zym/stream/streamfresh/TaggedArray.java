package zym.stream.streamfresh;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * @Author unyielding
 * @date 2018/7/26 0026 19:48
 * @desc 一个类(除了当做例子之外,它不是一个非常有用的类)，
 * 它维护一个数组， 其中实际数据保存在偶数位置，而不相关的标记数据保存在奇数位置。
 * 它的Spliterator会忽略标记数据。
 */
public class TaggedArray<T> {
    private final Object[] elements;//创建后，不可变的
    /**
     * 构造方法
     *
     * @param data 实际数据
     * @param tags 标记数据
     */
    TaggedArray(T[] data, Object[] tags) {
        int size = data.length;
        //保证实际数据数组和标记数据数组的大小相同
        if (tags.length != size) throw new IllegalArgumentException();
        this.elements = new Object[2 * size];
        //初始化elements 数组
        for (int i = 0, j = 0; i < size; ++i) {
            elements[j++] = data[i];
            elements[j++] = tags[i];
        }
    }

    public Spliterator<T> spliterator() {
        return new TaggedArraySpliterator<>(elements, 0, elements.length);
    }

    static class TaggedArraySpliterator<T> implements Spliterator<T> {
        private final Object[] array;

        private int origin; //当前索引，在分割或者遍历时使用

        private final int fence;//最大的下标加一

        TaggedArraySpliterator(Object[] array, int origin, int fence) {
            this.array = array;
            this.origin = origin;
            this.fence = fence;
        }

        /**
         *  批量遍历
         * @param action 消费函数 {@link Consumer} 的子类，可以通过lambda表达式表示
         */
        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            for (; origin < fence; origin += 2) {
                action.accept((T) array[origin]);
            }
        }

        /**
         *  处理单个元素
         * @param action 消费函数 {@link Consumer} 的子类，可以通过lambda表达式表示
         * @return 如果有元素消费就返回true,如果没有就直接返回false
         */
        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (origin < fence) {
                action.accept((T) array[origin]);
                origin += 2;
                return true;
            }
            return false;
        }

        /**
         * 分割数据源
         * @return 返回分割后生成的Spliterator
         */
        @Override
        public Spliterator<T> trySplit() {
            int lo = origin;
            int mid = ((lo + fence) >> 1) & 1;//强制中点数为偶数
            if (lo < mid) {
                origin = mid;//重置Spliterator的 当前下标
                return new TaggedArraySpliterator<>(array, lo, mid);
            }//太小不需要拆分
            return null;
        }

        /**
         * 估计剩余还有多少元素
         * @return 剩余还有多少元素
         */
        @Override
        public long estimateSize() {
            return (long) ((fence - origin) / 2);
        }

        /**
         * 获取特征值 用户可以根据 特征值 ，
         * 用户可以根据 配置更好的控制和优化它的使用
         * @return
         */
        @Override
        public int characteristics() {
            return ORDERED | IMMUTABLE | SIZED | SUBSIZED;
        }
    }

    /**
     * 并行遍历
     * @param a 一个{@link TaggedArray} 实例
     * @param action
     * @param <T> 每个元素的值
     */
    static <T> void parEach(TaggedArray<T> a, Consumer<T> action) {
        Spliterator<T> spliterator = a.spliterator();
        long targetBatchSize = spliterator.estimateSize()
                / (ForkJoinPool.getCommonPoolParallelism() * 8);
        new ParEach<>(null, spliterator, action, targetBatchSize).invoke();
    }

    /**
     * 并行计算器
     * @param <T> 元素的类型
     */
    static class ParEach<T> extends CountedCompleter<T> {
        final Spliterator<T> spliterator;
        final Consumer<T> action;
        final long targetBatchSize;

        ParEach(ParEach<T> parent, Spliterator<T> spliterator,
                Consumer<T> action, long targetBatchSize) {
            super(parent);
            this.spliterator = spliterator;
            this.action = action;
            this.targetBatchSize = targetBatchSize;
        }

        @Override
        public void compute() {
            Spliterator<T> sub;
            while (spliterator.estimateSize() > targetBatchSize
                    && (sub = spliterator.trySplit()) != null) {
                addToPendingCount(1);
                new ParEach<>(this, sub, action, targetBatchSize).fork();
            }
            spliterator.forEachRemaining(action);
            propagateCompletion();
        }
    }
}

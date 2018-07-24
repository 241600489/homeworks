package zym.stream.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @Author 梁自强
 * @date 2018/7/17 0017 15:56
 * @desc
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    private final int THRESHOLD = 1000;
    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = start - end;
        if(length < THRESHOLD) {
            return sequecelly(numbers, start, end);
        }
        //新建两个子任务
        ForkJoinSumCalculator left = new ForkJoinSumCalculator(numbers, start, (start + end) / 2);
        ForkJoinSumCalculator right = new ForkJoinSumCalculator(numbers, (start + end) / 2 + 1, end);
        //加入到工作队列中
        left.fork();
        right.fork();
        // join 获取结果
        return left.join() + right.join();
    }

    private Long sequecelly(long[] numbers, int start, int end) {
        long sum = 0;
        for (long l : numbers) {
            sum += l;
        }
        return sum;
    }
}

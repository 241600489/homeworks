package zym.stream.streamfresh;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @Author unyielding
 * @date 2018/7/24 0024 22:18
 * @desc 生成流
 */
public class GenerateStream {
    @Test
    public void arrayGenerate() {
        Integer[] arr = {1, 2, 3, 4, 6};//这里可以开成int[] 试试看看结果。。。①
        Stream.of(arr)//生成流Stream<Integer>
                .forEach(System.out::println);//打印流中的每个元素
    }

    @Test
    public void listGenerate() {
        Arrays.asList(1, 2, 3, 4)//返回list集合
                .stream()//转换为Stream<Integer>
                .forEach(System.out::println);
    }

    @Test
    public void fileGenerate() throws IOException {
        Files.lines(Paths.get("E:\\temp\\test.txt"))
                .forEach(System.out::println);

    }

    @Test
    public void streamsGenerate() {
        Stream.generate(() -> 1)//generate方法 参数是Supplier函数接口：生产流的元素
                .limit(5)
                .forEach(System.out::println);

    }

    @Test
    public void streamItearate() {
        Stream.iterate(5, i -> i + 1)//生成无线流
                .limit(5)//使用limit 截断流 这里取前5个，类比sql 的limit
                .forEach(System.out::println);
    }

    @Test
    public void streamRanged() {
        IntStream.range(1, 2)
                .forEach(System.out::println);
    }
    @Test
    public void streamRangeClosed() {
        IntStream.rangeClosed(1, 2)
                .forEach(System.out::println);
    }
}

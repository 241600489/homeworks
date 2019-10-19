package zym.stream.streamfresh;


import org.junit.jupiter.api.Test;

/**
 * @Author unyielding
 * @date 2018/7/31 0031 22:46
 * @desc 测试 TaggedArray类
 */
public class TaggedArrayTest {
    @Test
    public void  parEach() {
        TaggedArray<String> taggedArray = new TaggedArray<>(new
                String[]{"我爱你", "你爱我"}, new String[]{"1", "2"});
        TaggedArray.parEach(taggedArray, System.out::println);
    }
}

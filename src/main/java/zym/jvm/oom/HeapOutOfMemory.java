package zym.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存 溢出
 * 设置虚拟参数为
 * -xms=512m -xmx512m
 */
public class HeapOutOfMemory {
    public static void main(String[] args) {

        List<Object> list = new ArrayList<>();

        while (true) { list.add(new Object());}

    }

}

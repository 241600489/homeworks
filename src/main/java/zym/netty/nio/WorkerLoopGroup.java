package zym.netty.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 主要用来处理io 读写请求
 *
 * @author liangziqiang
 * @date 2019/9/29 16:13
 */
public class WorkerLoopGroup {

    /**
     * 指定多个数组
     */
    private WorkerLoop[] workerLoops;

    /**
     * 新连接来了 选取一个工作循环来处理该连接
     */
    private int next;


    private static final int DEFAULT_LOOP_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;




    public WorkerLoopGroup() throws IOException {
        this(DEFAULT_LOOP_SIZE);
    }

    public WorkerLoopGroup(int size) throws IOException {
        workerLoops = new WorkerLoop[size];
        for (int i = 0; i < workerLoops.length; i++) {
            workerLoops[i] = new WorkerLoop();
        }
    }


    public WorkerLoop next() {
        next = (next + 1) % workerLoops.length;
        return workerLoops[next];
    }

    /**
     * 将channel 注册到通道上并设置感兴趣的事件
     *
     * @param channel  目标通道
     * @param interest -1 则默认为{@link java.nio.channels.SelectionKey#OP_READ}
     */
    public void register(MonkeyChannel channel, int interest) {
        if (interest == -1) {
            interest = SelectionKey.OP_READ;
        }
        //WorkGroup 注册完start
        WorkerLoop nextWorkLoop = next();
        nextWorkLoop.register(channel, interest);
        nextWorkLoop.start();
    }
}

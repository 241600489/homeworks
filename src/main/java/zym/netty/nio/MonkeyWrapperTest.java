package zym.netty.nio;

import java.io.IOException;

/**
 * @author liangziqiang
 * @date 2019/9/30 17:44
 */
public class MonkeyWrapperTest {
    public static void main(String[] args) throws IOException {
        Bootstrap bootstrap = new Bootstrap();
        WorkerLoopGroup bossWorkerGroup = new WorkerLoopGroup(1);
        WorkerLoopGroup workerLoopGroup = new WorkerLoopGroup();

        bootstrap.group(bossWorkerGroup, workerLoopGroup).bind("127.0.0.1", 8080);
    }
}

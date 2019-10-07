package zym.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * @author liangziqiang
 * @date 2019/9/30 16:38
 */
public class Bootstrap {
    public static final int DEFAULT_SO_BACKLOG = 1024;
    private WorkerLoopGroup bossGroup;

    private WorkerLoopGroup workerGroup;


    public Bootstrap group(WorkerLoopGroup bossGroup, WorkerLoopGroup workerGroup) {
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
        return this;
    }

    public void bind(String ip, int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(ip, port),DEFAULT_SO_BACKLOG);

        MonkeyChannel monkeyServerChannel = new MonkeyChannel(serverSocketChannel);
        monkeyServerChannel.setMonkeyChannelHandler(new Acceptor(workerGroup));
        bossGroup.register(monkeyServerChannel, SelectionKey.OP_ACCEPT);
        System.out.println("启动成功");
    }


    class Acceptor implements MonkeyChannelHandler {

        private WorkerLoopGroup childGroup;

        public Acceptor(WorkerLoopGroup childGroup) {
            this.childGroup = childGroup;
        }

        @Override
        public void processRead(Object msg) throws IOException {
            System.out.println("have something received");
            childGroup.register(new MonkeyChannel((SocketChannel)msg), SelectionKey.OP_READ);
        }
    }
}


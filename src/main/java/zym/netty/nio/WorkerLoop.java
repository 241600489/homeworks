package zym.netty.nio;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 单个处理io 请求循环
 *
 * @author liangziqiang
 * @date 2019/9/29 17:44
 */
public class WorkerLoop extends Thread {

    private Selector selector;


    public WorkerLoop() throws IOException {
        this.selector = Selector.open();
    }

    /**
     * 将channel注册到{@link WorkerLoop#selector}
     *
     * @param sc 将要注册到 {@link WorkerLoop#selector} 上的通道
     * @return 返回是否注册成功
     */
    public boolean register(MonkeyChannel sc, int interestOps) {
        try {
            sc.doRegister(this, interestOps);
            return true;
        } catch (ClosedChannelException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                final int selectedKeysCount = selector.selectNow();
                //如果准备就绪的感兴趣事件数 小于零则继续
                if (selectedKeysCount <= 0) {
                    continue;
                }
                Set<SelectionKey> prepared = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = prepared.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey preparedKey = keyIterator.next();
                    keyIterator.remove();
                    int readyOps = preparedKey.interestOps();
                    MonkeyChannel monkeyChannel = (MonkeyChannel) preparedKey.attachment();
                    if (readyOps == SelectionKey.OP_ACCEPT) {
                        ServerSocketChannel channel = (ServerSocketChannel) preparedKey.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        monkeyChannel.getMonkeyChannelHandler().processRead(accept);
                    }
                    if (readyOps == SelectionKey.OP_READ) {
                        monkeyChannel.doRead();
                        System.out.println("threadId:" + Thread.currentThread().getId() + " read");

                    }
                    if (readyOps == SelectionKey.OP_WRITE) {
                        monkeyChannel.doWrite();
                        System.out.println("threadId:" + Thread.currentThread().getId() + " write");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Selector selector() {
        return selector;
    }
}

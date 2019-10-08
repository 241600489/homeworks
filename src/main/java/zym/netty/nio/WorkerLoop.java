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

    private final static long EMPTY_POLL_DEFAULT = 200;

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
            if (Thread.currentThread() == this) {
                sc.doRegister(this, interestOps);
            }else {
                new Thread(() -> sc.doRegister(WorkerLoop.this, interestOps)).start();
            }
            return true;
    }

    @Override
    public void run() {
        int selectCnt = 0;
        for (; ; ) {
            try {
                if (Thread.currentThread().isInterrupted() == Boolean.TRUE) {
                    break;
                }

                final int selectedKeysCount = selector.select();
                //如果准备就绪的感兴趣事件数 小于零则继续
                if (selectedKeysCount <= 0) {
                    if (++selectCnt >= EMPTY_POLL_DEFAULT) {
                        sleep(100);
                        selectCnt = 0;
                    }
                    continue;
                }
                //
                selectCnt = 0;

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
                        System.out.println( Thread.currentThread().getName() + " read");

                    }

                    if (readyOps == SelectionKey.OP_WRITE) {
                        monkeyChannel.doWrite();
                        System.out.println( Thread.currentThread().getName() + " write");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Selector selector() {
        return selector;
    }

    public void wakeup() {
        selector.wakeup();
    }
}

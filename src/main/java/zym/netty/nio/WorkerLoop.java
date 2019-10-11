package zym.netty.nio;

import org.apache.xmlbeans.impl.xb.xsdschema.Facet;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 单个处理io 请求循环
 *
 * @author liangziqiang
 * @date 2019/9/29 17:44
 */
public class WorkerLoop extends Thread {

    private Selector selector;

    private final static long EMPTY_POLL_DEFAULT = 200;

    private volatile boolean isStop = false;

    public WorkerLoop() throws IOException {
        this.selector = Selector.open();
        new Thread(() -> {
            while (isStop) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }

                    Runnable task = registerToSelector.take();
                    task.run();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    //增加定时任务
    public ArrayBlockingQueue<Runnable> registerToSelector = new ArrayBlockingQueue<>(50);


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

                registerToSelector.add(() -> sc.doRegister(WorkerLoop.this, interestOps));

            }
            return true;
    }

    /**
     * 处理注册到selector 的任务
     */
    public void processRegisterTask() {
        int size = registerToSelector.size();

        for (int i = 0; i < size; i++) {
            try {

                registerToSelector.take().run();

            } catch (InterruptedException e) {
                System.out.println(String.format("%s interrupted ", Thread.currentThread().getName()));
            }
        }
    }

    /**
     * 判断是否有任务处理
     */
    public boolean isHaveTaskProcess() {
        return registerToSelector.size() > 0;
    }

    @Override
    public void run() {
        int selectCnt = 0;
        for (; ; ) {
            try {

                if (Thread.currentThread().isInterrupted() == Boolean.TRUE) {
                    break;
                }

                if (isHaveTaskProcess()) {
                    processRegisterTask();
                }

                final int selectedKeysCount = selector.select(200);
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
                        System.out.println( Thread.currentThread().getName() + " read");
                    }

                    if (readyOps == SelectionKey.OP_WRITE) {
                        monkeyChannel.doWrite();
                        System.out.println(Thread.currentThread().getName() + " write");
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

    public void wakeup() {
        selector.wakeup();
    }
}

package zym.netty.nio;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

/**
 * 单个处理io 请求循环
 *
 * @author liangziqiang
 * @date 2019/9/29 17:44
 */
public class WorkerLoop {

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
    public boolean register(SelectableChannel sc) {
        try {
            sc.register(selector, SelectionKey.OP_READ);
            return true;
        } catch (ClosedChannelException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void run() {
        for (; ; ) {
            try {
                final int selectedKeysCount = selector.select();
                //如果准备就绪的感兴趣事件数 小于零则继续
                if (selectedKeysCount <= 0) {
                    continue;
                }
                Set<SelectionKey> prepared = selector.selectedKeys();
                for (SelectionKey preparedKey : prepared) {
                    int readyOps = preparedKey.interestOps();
                    if ((readyOps & SelectionKey.OP_READ) == 0) {

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}

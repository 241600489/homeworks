package zym.netty.nio;

import java.nio.ByteBuffer;

/**
 * 此为逻辑buffer,通道处理器调用write时向该buffer里,
 * 最多有1024个ByteBuffer,如果超了抛出异常关闭当前通道
 *
 * @author liangziqiang
 * @date 2019/9/30 15:08
 */
public class MonkeyBuffer {
    /**
     * 记录第几个消息 从零开始
     */
    private int next = 0;

    /**
     * 是否已刷新
     */
    private boolean flushed = false;

    private ByteBuffer[] logicalCaches;

    /**
     * 与实际绑定的通道
     */
    private MonkeyChannel monkeyChannel;


    public MonkeyBuffer(MonkeyChannel monkeyChannel) {
        logicalCaches = new ByteBuffer[1024];
        this.monkeyChannel = monkeyChannel;
    }

    /**
     * 向指定通道写数据
     *
     * @param msg
     */
    public void writeMsg(ByteBuffer msg) {
        if (next < logicalCaches.length) {
            logicalCaches[next++] = msg;
        } else {
            throw new IllegalStateException("logical cache is full,selector may have something wrong");
        }
    }

    public void flush() {
        for (int i = 0; i < next; i++) {
            logicalCaches[i].flip();
            monkeyChannel.write(logicalCaches[i]);
        }
        flushed = true;
    }
}

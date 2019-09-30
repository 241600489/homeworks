package zym.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * 对{@link java.nio.channels.SelectableChannel} 进行封装并增加新的功能
 *
 * @author liangziqiang
 * @date 2019/9/30 14:57
 */
public class MonkeyChannel {


    /**
     * 核心选择通道
     */
    private SelectableChannel sch;

    /**
     * 注册到Selector上的标识
     */
    private SelectionKey sky;


    private MonkeyChannelHandler monkeyChannelHandler;


    public MonkeyChannelHandler getMonkeyChannelHandler() {
        return monkeyChannelHandler;
    }

    public void setMonkeyChannelHandler(MonkeyChannelHandler monkeyChannelHandler) {
        this.monkeyChannelHandler = monkeyChannelHandler;
    }

    private MonkeyBuffer monkeyBuffer;

    public MonkeyChannel(SelectableChannel sch) {
        this.sch = sch;
        monkeyBuffer = new MonkeyBuffer(this);
    }

    public void write(ByteBuffer tempBuffer) {
        try {
            SocketChannel socketChannel = (SocketChannel) sch;
            socketChannel.write(tempBuffer);
        } catch (IOException e) {
            System.out.println("write to socket channel error");
            e.printStackTrace();
        }
    }

    public void doRegister(Selector selector,int intrestOps) throws ClosedChannelException {
        sky = sch.register(selector, intrestOps, this);
    }

    public void doRead() {
        try {
            //读取请求头 可以考虑实现一个 Protocol 接口 来读自定义协议
            SocketChannel readChannel = (SocketChannel) sky.channel();
            ByteBuffer headBuffer = ByteBuffer.allocate(4);
            while (readChannel.read(headBuffer) != 0) { }
            headBuffer.flip();
            int valueLength = headBuffer.getInt();
            ByteBuffer valueContainer = ByteBuffer.allocateDirect(valueLength);
            while (readChannel.read(valueContainer) != 0) {}
            monkeyBuffer.writeMsg(headBuffer);
            monkeyBuffer.writeMsg(valueContainer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doWrite() {
        monkeyBuffer.flush();
    }

    public SelectableChannel javaChanel() {
        return sch;
    }
}

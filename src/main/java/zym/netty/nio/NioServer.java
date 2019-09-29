package zym.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lzq
 */
public class NioServer {
    private final static int PORT = 8080;
    public static final String HOSTNAME = "127.0.0.1";

    public void bind() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置为非阻塞的
            serverSocketChannel.configureBlocking(false);
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(HOSTNAME, PORT));
            //多路复用
            Selector selector = Selector.open();
            //将通道注册到同步事件分离器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            for (;;) {
                final int selectedKeysCount = selector.select();
                if (selectedKeysCount > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();
                    while (keyIterator.hasNext()) {

                        SelectionKey selectionKey = keyIterator.next();
                        keyIterator.remove();

                        if (selectionKey.isAcceptable()) {

                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = channel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                        }else if(selectionKey.isReadable()) {

                            SocketChannel readChannel = (SocketChannel)selectionKey.channel();
                            ByteBuffer headBuffer = ByteBuffer.allocate(4);
                            readChannel.read(headBuffer);
                            headBuffer.flip();
                            int valueLength = headBuffer.getInt();
                            System.out.println("读取 " + valueLength + " 字节");
                            ByteBuffer valueBuffer = ByteBuffer.allocate(valueLength);
                            while(readChannel.read(valueBuffer) != 0) { }
                            valueBuffer.flip();
                            System.out.println(new String(valueBuffer.array()));
                            selectionKey.interestOps(SelectionKey.OP_WRITE);

                        } else if (selectionKey.isWritable()) {

                            SocketChannel channel = (SocketChannel) selectionKey.channel();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(8);
                            writeBuffer.putInt(4);
                            writeBuffer.put("hell".getBytes());
                            writeBuffer.flip();
                            channel.write(writeBuffer);
                            //主动关闭通道
                            channel.close();
                        }

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioServer().bind();
    }
}

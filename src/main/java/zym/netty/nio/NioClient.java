package zym.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class NioClient {

    public static final int INT_SIZE = 4;

    public static void main(String[] args) throws IOException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    cyclicBarrier.await();
                    long now = System.currentTimeMillis();
                    sendSomethingToMonkeyServer();
                    System.out.println("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void sendSomethingToMonkeyServer() throws IOException {
        SocketChannel client = SocketChannel.open();
        client.connect(new InetSocketAddress("127.0.0.1",8081));

        String data = "nihao";

        int dataLength = data.getBytes().length;
        ByteBuffer dataBuffer = ByteBuffer.allocate(4 + dataLength);
        dataBuffer.putInt(dataLength);
        dataBuffer.put(data.getBytes());
        dataBuffer.flip();

        long now = System.currentTimeMillis();
        while (client.write(dataBuffer) != 0) { }
        ByteBuffer head = ByteBuffer.allocate(INT_SIZE);
        while (client.read(head) != 0) {}
        ;
        head.flip();
        int headInt = head.getInt();

        ByteBuffer value = ByteBuffer.allocate(headInt);
        while (client.read(value)!=0){}

        System.out.println("call traditional data transfer cost " + (System.currentTimeMillis() - now) + " ms");
        System.out.println(headInt);
        value.flip();
        System.out.println(new String(value.array()));

        client.close();
    }
}

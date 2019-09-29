package zym.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * file channel map study
 *
 * @author 24160
 */
public class FileChannelMapStudy {

    public static final String FILE_CHANNEL_MAP_STUDY_TXT = "a.txt";
    public static final int INT_BYTES_LENGTH = 4;

    public static void main(String[] args) {
        prepareEnviroment();
        try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_CHANNEL_MAP_STUDY_TXT), StandardOpenOption.READ)) {
            long size = fileChannel.size();
            //将a.txt 文件映射到内存缓冲区，从0位置处映射，映射10个字节长度，该映射内存缓冲区只可读
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 14);
            //创建一个SocketChannel实例
            SocketChannel client = SocketChannel.open();
            //连接服务端
            client.connect(new InetSocketAddress("localhost", 8080));
            //写文件内容到服务端
            client.write(mappedByteBuffer);
            //读取文件内容 网络协议为 head + body  如6zengyi
            ByteBuffer head = ByteBuffer.allocate(INT_BYTES_LENGTH);
            while (client.read(head) != 0) {}
            //切换读写模式
            head.flip();
            //读取body
            ByteBuffer body = ByteBuffer.allocate(head.getInt());
            while (client.read(body) != 0) {}
            //切换读写模式
            body.flip();
            System.out.println(String.format("发送字节成功，服务端返回：%s", new String(body.array())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void prepareEnviroment() {
        try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_CHANNEL_MAP_STUDY_TXT), StandardOpenOption.CREATE,StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            //将a.txt 映射文件到内存映射区域，模式为可读可写
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 14);
            //放进去一个int 为10
            mappedByteBuffer.putInt(10);
            mappedByteBuffer.put("zengyiming".getBytes());
            //强制刷盘
            mappedByteBuffer.force();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

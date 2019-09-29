package zym.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;

/**
 * @author lzq
 */
public class FileChannelReadTest {
    static String testFilePath = "testFileChannelReadTest.txt";

    public static void main(String[] args) {
        Path path = Paths.get(testFilePath);
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            //read
            ByteBuffer read1 = ByteBuffer.allocate(4);

            int readed1 = fileChannel.read(read1, 0);
            System.out.println("read ByteBuffer " + readed1 + " bytes，內容：" + new String(read1.array()));

            ByteBuffer[] byteBuffersRead = new ByteBuffer[3];
            IntStream.rangeClosed(0, 2).forEach(i -> {
                byteBuffersRead[i] = ByteBuffer.allocate(5);
            });
            System.out.println("alfter read fileChannel position:" + fileChannel.position());
            //很关键 要不然又从头开始读，如下面的结果A
            fileChannel.position(readed1);
            long readed2 = fileChannel.read(byteBuffersRead, 0, byteBuffersRead.length);
            IntStream.rangeClosed(0, 2).forEach(i -> {
                byteBuffersRead[i].flip();
            });
            System.out.println(String.format("read ByteBuffers %d bytes,content:%s\n%s\n%s",readed2,
                    new String(readBytesFrromBuffer(byteBuffersRead[0])),
                    new String(readBytesFrromBuffer(byteBuffersRead[1])),
                    new String(readBytesFrromBuffer(byteBuffersRead[2]))));
        } catch (IOException e) {

        }
    }

    private static byte[] readBytesFrromBuffer(ByteBuffer byteBuffer) {
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }

}

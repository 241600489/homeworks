package zym.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author lzq
 */
public class FileChannelForceInTime {
    static String testFilePath = "testFileChannelForceInTime.txt";
    static byte[] imageBytes;
    final static String PICTUREE_PATH = "meimei.jpg";

    static {
        try {
            imageBytes = Files.readAllBytes(Paths.get(PICTUREE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {

        try  {
            Path path = Paths.get(testFilePath);
            FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            //每次写都调用force
            ByteBuffer imageBytesBuffer = ByteBuffer.allocate(imageBytes.length);
            imageBytesBuffer.put(imageBytes);
            long now = System.currentTimeMillis();
            for (int i = 0; i <= 9; i++) {
                imageBytesBuffer.flip();
                fileChannel.write(imageBytesBuffer);
                fileChannel.force(false);
                //这里为了和异步刷盘的例子保持一致，因为异步刷盘有个定时任务所以 可能时间需要长点，所以这里模拟一下
                Thread.sleep( 1000);
            }
            System.out.println("write in time force cost " + ((System.currentTimeMillis() - now)-10 * 1000) + "ms");
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}

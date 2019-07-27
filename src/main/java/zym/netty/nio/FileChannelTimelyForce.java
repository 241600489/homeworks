package zym.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileChannelTimelyForce {
    static String testFilePath = "testFileChannelTimelyForce.txt";
    static byte[] imageBytes;
    final static String PICTUREE_PATH = "meimei.jpg";

    static {
        try {
            imageBytes = Files.readAllBytes(Paths.get(PICTUREE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        try {
            Path path = Paths.get(testFilePath);;
            FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            executorService.scheduleAtFixedRate(() -> {
                try {
                    if (fileChannel.isOpen()) {
                        fileChannel.force(false);
                    }
                } catch (IOException e) {
                }
            }, 1, 1, TimeUnit.SECONDS);
            long now = System.currentTimeMillis();
            ByteBuffer imageBytesBuffer = ByteBuffer.allocate(imageBytes.length);
            imageBytesBuffer.put(imageBytes);
            for (int i = 0; i <= 9; i++) {
                imageBytesBuffer.flip();
                fileChannel.write(imageBytesBuffer);
                //异步刷盘有s个定时任务所以 可能时间需要长点，所以这里模拟一下
                Thread.sleep(1000);
            }
            System.out.println("fileChannnel write timely foce cost " + (System.currentTimeMillis() - now - 10 * 1000) + "ms");
            fileChannel.close();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }finally {
            executorService.shutdown();
        }

    }
}

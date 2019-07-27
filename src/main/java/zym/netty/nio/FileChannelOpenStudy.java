package zym.netty.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelOpenStudy {
    public static final String JAVA_NIO = "java NIO";
    static String createAndWriteAndReadPath = "createAndWriteAndReadPath.txt";
    public static void main(String[] args) {
        FileChannel readChannel = null;
        FileChannel createAndWriteChannel = null;
        FileChannel appendChannel = null;
//        FileChannel dataSyncChannel = null;
        try{
            //create and write
            createAndWriteChannel = FileChannel.open(Paths.get(createAndWriteAndReadPath), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            ByteBuffer writeBuffer = ByteBuffer.allocate(6);
            writeBuffer.put("hello,".getBytes(Charset.forName("UTF-8")));
            //切换 读写模式
            writeBuffer.flip();
            int writed1 = createAndWriteChannel.write(writeBuffer);
            System.out.println("createAndWriteChannel write in " + writed1 + " bytes");

            readChannel = FileChannel.open(Paths.get(createAndWriteAndReadPath), StandardOpenOption.READ);
            ByteBuffer readBuffer = ByteBuffer.allocate(writed1);
            int readed1 = readChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println("readChannel read " + readed1 + " bytes:" + new String(readBytesFrromBuffer(readBuffer)));
            createAndWriteChannel.close();

            appendChannel = FileChannel.open(Paths.get(createAndWriteAndReadPath), StandardOpenOption.APPEND);
            ByteBuffer appendBuffer = ByteBuffer.allocate(JAVA_NIO.length());
            appendBuffer.put(JAVA_NIO.getBytes("UTF-8"));
            appendBuffer.flip();
            int writed2 = appendChannel.write(appendBuffer);
            System.out.println("appendChannel writed in " + writed2 + " bytes");


            ByteBuffer readBuffer1 = ByteBuffer.allocate(writed2);
            int readed2 = readChannel.read(readBuffer1, readed1);
            readBuffer1.flip();
            System.out.println("readChannel readed " + readed2 + " bytes:" +
                    new String(readBytesFrromBuffer(readBuffer1)));
            appendChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
    private static byte[] readBytesFrromBuffer(ByteBuffer byteBuffer) {
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }

}

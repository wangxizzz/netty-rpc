package netty_demo.nio.heapBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <Description>
 *
 * @author wangxi
 */
public class TransferFileByChannel {
    public static void nio() {
        FileInputStream aFile = null;
        try {

            aFile = new FileInputStream("src/nio.txt");
            // channel获取数据
            FileChannel fileChannel = aFile.getChannel();
            // 初始化Buffer，设定Buffer每次可以存储数据量
            // 创建的Buffer是1024byte的，如果实际数据本身就小于1024，那么limit就是实际数据大小
            ByteBuffer buf = ByteBuffer.allocate(1024);
            // channel中的数据写入Buffer
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);

            while (bytesRead != -1) {    // 也可以使用buf.hasRemaining()
                // Buffer切换为读取模式
                buf.flip();
                // 读取数据
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }

                //buf.compact();
                // 继续将数据写入缓存区。read()不会阻塞，只是把数据读取到buffer中，如果Channel无数据直接返回
                bytesRead = fileChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        TransferFileByChannel.nio();
    }
}


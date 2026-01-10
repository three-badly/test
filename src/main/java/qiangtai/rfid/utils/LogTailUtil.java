package qiangtai.rfid.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class LogTailUtil {

    /**
     * 读取文件最后 N 行，UTF-8 安全，不乱序，不乱码
     */
    public static List<String> tail(File file, int lines) throws IOException {
        if (!file.exists()) return Collections.emptyList();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r");
             FileChannel channel = raf.getChannel()) {

            long size = channel.size();
            if (size == 0) return Collections.emptyList();

            int blockSize = 8192;               // 8K 块，可调整
            long pos = size;
            List<String> lineBuf = new ArrayList<>();

            while (pos > 0 && lineBuf.size() < lines) {
                long start = Math.max(pos - blockSize, 0);
                int len = (int) (pos - start);

                ByteBuffer buf = ByteBuffer.allocate(len);
                channel.read(buf, start);
                buf.flip();

                // 整块转字符串（UTF-8）
                String segment = new String(buf.array(), StandardCharsets.UTF_8);
                // 按行切，倒序加入
                String[] arr = segment.split("\n");
                for (int i = arr.length - 1; i >= 0; i--) {
                    if (lineBuf.size() >= lines) break;
                    lineBuf.add(arr[i]);
                }
                pos = start;
            }

            // 倒序还原行顺序
            Collections.reverse(lineBuf);
            return lineBuf;
        }
    }
    // 新增方法：支持“读取全部”或“只读末尾 N 行”
    public static List<String> readLog(File file, boolean readAll, int lines) throws IOException {
        if (!file.exists()) return Collections.emptyList();

        if (readAll && file.length() < 10 * 1024 * 1024) { // 小于10MB就读全部
            return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } else {
            return tail(file, lines); // 否则只读末尾N行
        }
    }
}
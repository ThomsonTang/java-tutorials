package com.tt.tutorials.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用{@code FileChannel}读取数据到{@code Buffer}的示例
 *
 * @author Thomson Tang
 * @version Created: 18/09/2017.
 */
public class FileChannelExample {

  public static void main(String[] args) {
    try {
      RandomAccessFile accessFile = new RandomAccessFile("/opt/test/nio-data.txt", "rw");
      FileChannel channel = accessFile.getChannel();

      // 定义了buffer的长度
      ByteBuffer buffer = ByteBuffer.allocate(48);
      int byteRead = channel.read(buffer);
      while (byteRead != -1) {
        System.out.println("Read: " + byteRead);
        buffer.flip();

        while (buffer.hasRemaining()) {
          System.out.print((char) buffer.get());
        }

        buffer.clear();
        byteRead = channel.read(buffer);
        System.out.println();
      }
      accessFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

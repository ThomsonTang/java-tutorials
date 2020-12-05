package com.tt.tutorials.java.network;

import java.io.IOException;
import java.net.Socket;

/**
 * 类说明
 *
 * @author Thomson Tang
 */
public class CheckIfPortUsed {

  public static void main(String[] args) {
    if (isRunning("127.0.0.1", 8088)) {
      System.out.println("the port is in use. ");
    } else {
      System.out.println("the port is not in use. ");
    }
  }

  public static boolean isRunning(String address, int port) {
    Socket socket;
    try {
      socket = new Socket(address, port);
      socket.close();
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}

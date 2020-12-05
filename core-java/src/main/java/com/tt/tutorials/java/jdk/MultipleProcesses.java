package com.tt.tutorials.java.jdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类说明
 *
 * @author Thomson Tang
 */
public class MultipleProcesses {

  public static void main(String[] args) {
    try {
      Process pingBaidu = Runtime.getRuntime().exec("ping www.baidu.com");
      ProcessBuilder builder = new ProcessBuilder("ping", "www.jd.com");
      Process pingJd = builder.start();
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(pingBaidu.getInputStream()));
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

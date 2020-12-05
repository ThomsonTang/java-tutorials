package com.tt.tutorials.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Java堆内存溢出异常测试
 *
 * @author Thomson Tang
 * @version Created: 16/09/2017.
 */
public class HeapOOM {

  static class OOMObject {

  }

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<>();
    while (true) {
      System.out.println("max memory = [" + Runtime.getRuntime().maxMemory() / (1000 * 1000) + "]");
      System.out
          .println("free memory = [" + Runtime.getRuntime().freeMemory() / (1000 * 1000) + "]");
      list.add(new OOMObject());
    }
  }
}

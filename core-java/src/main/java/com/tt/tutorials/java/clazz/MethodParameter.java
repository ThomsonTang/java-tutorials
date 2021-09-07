package com.tt.tutorials.java.clazz;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * Class Description.
 *
 * @author admin
 */
@Slf4j
public class MethodParameter {

  public int modifyInt(int i) {
    i = 0;
    return i;
  }

  public Integer modifyInteger(Integer i) {
    i = 0;
    return i;
  }

  public String modifyString(String s) {
    s = "modified";
    return s;
  }

  public String[] modifyArray(String[] ary) {
    if (null != ary && ary.length > 0) {
      ary[0] = "x";
      return ary;
    }
    throw new NullPointerException("parameter array is null");
  }

  public static void main(String[] args) {
    MethodParameter methodParameter = new MethodParameter();

    int i = 100;
    log.info("default int: {}", i);
    int i1 = methodParameter.modifyInt(i);
    log.info("modified int: {}", i1);
    log.info("original int: {}", i);

    Integer j = 100;
    log.info("default int: {}", j);
    int mj = methodParameter.modifyInt(j);
    log.info("modified int: {}", mj);
    log.info("original int: {}", j);

    String s = "hello";
    log.info("default string: {}", s);
    String ms = methodParameter.modifyString(s);
    log.info("modified string: {}", ms);
    log.info("original string: {}", s);

    String[] ary = new String[]{"a", "b", "c"};
    log.info("default array: {}", Arrays.toString(ary));
    String[] modifiedAry = methodParameter.modifyArray(ary);
    log.info("modified array: {}", Arrays.toString(modifiedAry));
    log.info("original array: {}", Arrays.toString(ary));
  }

}

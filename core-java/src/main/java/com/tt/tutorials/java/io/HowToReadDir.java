package com.tt.tutorials.java.io;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 类说明
 *
 * @author Thomson Tang
 */
public class HowToReadDir {

  private static final String SAMPLE_DIR_PATH = "";

  public void readInJava7() {
    try {
      Path path = Paths.get(this.getClass().getResource(SAMPLE_DIR_PATH).toURI());

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}

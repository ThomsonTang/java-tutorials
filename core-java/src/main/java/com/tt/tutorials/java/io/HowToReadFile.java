package com.tt.tutorials.java.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * How to read a file in java.
 *
 * @author Thomson Tang
 * @version Created: 25/06/2017.
 */
public class HowToReadFile {

  private static final Logger LOGGER = LoggerFactory.getLogger(HowToReadFile.class);
  private static final String SAMPLE_FILE_NAME = "nba-teams.txt";

  /**
   * java6 中读取一个文件的方法
   */
  public void readFileInJava6() {
    LOGGER.info("==========Reading a file in Java6:");
    StringBuilder content = new StringBuilder();
    URL fileUrl = this.getClass().getResource(SAMPLE_FILE_NAME);
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileUrl.getPath()));
      String line;
      while (null != (line = bufferedReader.readLine())) {
        content.append(line).append("\n");
      }
      bufferedReader.close();

      LOGGER.info("the file content:\n{}", content);
    } catch (FileNotFoundException e) {
      LOGGER.error("the file <{}> was not found.", fileUrl.getPath());
    } catch (IOException e) {
      LOGGER.error("read the file <{}> was error.", fileUrl.getPath());
    }
  }

  /**
   * java7 中读取文件的方法
   */
  public void readFileInJava7() {
    LOGGER.info("=========Reading a file in Java7:");
    try {
      Path path = Paths.get(this.getClass().getResource(SAMPLE_FILE_NAME).toURI());
      byte[] fileBytes = Files.readAllBytes(path);
      String content = new String(fileBytes);

      LOGGER.info("the file content:\n{}", content);
    } catch (URISyntaxException | IOException e) {
      LOGGER.error("read the file error.", e);
    }
  }

  public void readFileInJava8() {
    LOGGER.info("=========Reading a file in Java8:");
    try {
      Path path = Paths.get(getClass().getResource(SAMPLE_FILE_NAME).toURI());
      StringBuilder content = new StringBuilder();
      Stream<String> lines = Files.lines(path);
      lines.forEach(line -> content.append(line).append("\n"));
      lines.close();

      LOGGER.info("the file content:\n{}", content);
    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    HowToReadFile howToReadFile = new HowToReadFile();

    howToReadFile.readFileInJava6();
    howToReadFile.readFileInJava7();
    howToReadFile.readFileInJava8();
  }
}

package com.tt.tutorials.java.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类说明
 *
 * @author Thomson Tang
 */
public class HowToCreateFile {

  private static final Logger LOGGER = LoggerFactory.getLogger(HowToCreateFile.class);

  public void createDirectory(String dirs) {
    Path path = Paths.get(dirs);
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
        LOGGER.info("the dirs {} is created.", dirs);
      } catch (IOException e) {
        LOGGER.error("error occurs: ", e);
      }
    } else {
      LOGGER.info("the dirs {} is already exist.", dirs);
    }
  }

  public void createFile(String fileName) {
    Path path = Paths.get(fileName);
    try {
      if (!Files.exists(path)) {
        Path filePath = Files.createFile(path);
        LOGGER.info("the file {} is created.", filePath.getFileName());
      } else {
        LOGGER.info("the file {} is already exist.", fileName);
      }
    } catch (IOException e) {
      LOGGER.error("error occurs: ", e);
    }
  }

  public static void main(String[] args) {
    HowToCreateFile howToCreateFile = new HowToCreateFile();
    howToCreateFile.createDirectory("temp/test/resources");
    howToCreateFile.createFile("temp/test/resources/test_file.txt");
  }
}

package com.tt.java.tutorials.curator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类说明
 *
 * @author admin
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
  private final ZkClientService zkClientService;

  public Application(ZkClientService zkClientService) {
    this.zkClientService = zkClientService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    zkClientService.listenChildrenPath();
//        zkClientService.listenTreeCache();
    LOGGER.info("start to listen the children path...");
  }
}

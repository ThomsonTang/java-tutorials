package com.tt.java.tutorial.curator;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The configuration for zookeeper client.
 * <p>
 * we build the client instance by curator framework using properties from external configuration
 * file.
 *
 * @author admin
 */
@Configuration
@EnableScheduling
@Slf4j
public class AppConfig {

  @Bean
  @ConfigurationProperties(prefix = "zookeeper")
  public ZookeeperProperties zookeeperProperties() {
    return new ZookeeperProperties();
  }

  @Bean(destroyMethod = "close")
  public CuratorFramework curatorFramework(RetryPolicy retryPolicy, ZookeeperProperties properties)
      throws InterruptedException {
    CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
    builder.connectString(properties.getConnectString());
    CuratorFramework curator = builder.retryPolicy(retryPolicy).build();
    curator.start();
    log.info("blocking until connected to zookeeper for {}", properties.getConnectionTimeoutMs());
    curator.blockUntilConnected(properties.getConnectionTimeoutMs(), TimeUnit.MILLISECONDS);
    log.info("connected to zookeeper");
    return curator;
  }

  @Bean
  public RetryPolicy fixedTimesRetry(ZookeeperProperties zookeeperProperties) {
    return new RetryNTimes(zookeeperProperties.getRetryCount(),
        zookeeperProperties.getElapsedTimeMs());
  }

  @Bean(initMethod = "init", destroyMethod = "close")
  public ZkClient zkClient(ZookeeperProperties zookeeperProperties, RetryPolicy retryPolicy) {
    ZkClient zkClient = new ZkClient();
    zkClient.setConnectString(zookeeperProperties.getConnectString());
    zkClient.setRetryPolicy(retryPolicy);
    zkClient.setConnectionTimeoutMs(zookeeperProperties.getConnectionTimeoutMs());
    return zkClient;
  }
}

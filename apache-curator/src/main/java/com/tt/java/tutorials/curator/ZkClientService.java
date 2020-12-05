package com.tt.java.tutorials.curator;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * 类说明
 *
 * @author admin
 */
@Service
public class ZkClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClientService.class);
    private static final int AMOUNT = 1000;

    private final ZkClient zkClient;

    public ZkClientService(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 利用curator的cache机制设置监听点
     */
    public void listenChildrenPath() {
        PathChildrenCacheListener listener = (client, event) -> {
            ChildData data = event.getData();
            Stat stat = data.getStat();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(stat.getMtime()), ZoneId.systemDefault());
            LOGGER.info("get the event {}, data: {}, mtime: {}", event.getType(), data.getPath(), localDateTime);
            TimeUnit.MILLISECONDS.sleep(50);
        };
        zkClient.setPathChildrenCacheListener("/nums", true, listener);
    }

    public void listenTreeCache() {
        TreeCache treeCache = new TreeCache(zkClient.getClient(), "/nums");
        TreeCacheListener listener = (client, event) -> {
            ChildData data = event.getData();
            Stat stat = data.getStat();
            LocalDateTime creatTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(stat.getCtime()), ZoneId.systemDefault());
            LocalDateTime modifyTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(stat.getMtime()), ZoneId.systemDefault());

            LOGGER.info("get the event: {}, {}, {}, {}", event.getType(), data.getPath(), creatTime, modifyTime);
        };
        treeCache.getListenable().addListener(listener);
        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过一个定时任务模拟向ZK创建节点的场景
     */
    @Scheduled(fixedDelay = 5000000, initialDelay = 1500)
    public void createThousandsChildrenPath() {
        LOGGER.info("start to create {} path children.", AMOUNT);
        try {
            ZookeeperUtil.createParentIfNeed(zkClient.getClient(), "/nums");
        } catch (Exception e) {
            LOGGER.error("error occurs when create path /nums", e);
        }
        for (int i = 0; i < AMOUNT; i++) {
            createPath(i + "");
        }
        LOGGER.info("creation is over.");

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            LOGGER.error("the sleep is interrupted.", e);
        }

        for (int i = 0; i < AMOUNT; i++) {
            removePath(i + "");
        }
        LOGGER.info("remove is over.");
    }

    private void createPath(String path) {
        String fullPath = ZKPaths.makePath("/nums", path);
        LOGGER.info("the full path to create: {}", fullPath);
        try {
            ZookeeperUtil.createEphemeralIfNotExist(zkClient.getClient(), fullPath);
        } catch (Exception e) {
            LOGGER.error("error occurs when create path.", e);
        }
    }

    private void removePath(String path) {
        String fullPath = ZKPaths.makePath("/nums", path);
        LOGGER.info("the full path to delete: {}", fullPath);
        try {
            ZookeeperUtil.delete(zkClient.getClient(), fullPath);
        } catch (Exception e) {
            LOGGER.error("error occurs when delete path.", e);
        }

    }
}

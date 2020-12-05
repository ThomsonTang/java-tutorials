package com.tt.java.tutorial.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class is for creating zookeeper client bean.
 *
 * @author admin
 */
public class ZkClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkClient.class);

    private CuratorFramework client;
    private String connectString;
    private RetryPolicy retryPolicy;
    private PathChildrenCache pathChildrenCache;
    private NodeCache nodeCache;
    private int connectionTimeoutMs;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void init() throws InterruptedException {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
        builder.connectString(this.connectString);
        client = builder.retryPolicy(this.retryPolicy).namespace("thomson").build();
        client.start();
        LOGGER.info("blocking until connected to zookeeper for {}", this.connectionTimeoutMs);
        client.blockUntilConnected(this.connectionTimeoutMs, TimeUnit.MILLISECONDS);
        LOGGER.info("connected to zookeeper");
    }

    public void close() {
        if (client != null) {
            CloseableUtils.closeQuietly(client);
            LOGGER.info("the curator client is closed.");
        }
        if (pathChildrenCache != null) {
            CloseableUtils.closeQuietly(pathChildrenCache);
            LOGGER.info("the curator path children cache is closed.");
        }
        if (nodeCache != null) {
            CloseableUtils.closeQuietly(nodeCache);
            LOGGER.info("the curator node cache is closed.");
        }
    }

    public void setPathChildrenCacheListener(String path, boolean cacheData, PathChildrenCacheListener listener) {
        try {
            pathChildrenCache = new PathChildrenCache(client, path, cacheData, false, executorService);
            pathChildrenCache.getListenable().addListener(listener);
            pathChildrenCache.start();
        } catch (Exception e) {
            LOGGER.error("error occurs when add a ChildrenCacheListener for path {}.", path);
        }
    }

    public void setNodeCacheListener(String path, NodeCacheListener nodeCacheListener) {
        try {
            nodeCache = new NodeCache(client, path);
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start();
        } catch (Exception e) {
            LOGGER.error("error occurs when add a NodeCacheListener for path {}.", path);
        }
    }

    public CuratorFramework getClient() {
        return client;
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }
}

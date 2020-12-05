package com.tt.java.tutorial.curator;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Curator的基本操作方式（ZNode的增删改查）
 *
 * @author Thomson Tang
 * @version Created: 31/07/2017.
 */
public class ZookeeperUtil {
    /**
     * 检查节点是否已经存在
     *
     * @param client 客户端
     * @param path   节点路径
     * @return true 已存在，false 不存在
     * @throws Exception if error occurs
     */
    public static boolean checkExist(CuratorFramework client, String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return stat != null;
    }

    /**
     * 在指定路径上创建持久节点
     *
     * @param client 客户端
     * @param path   节点路径
     * @throws Exception if error occurs
     */
    public static void create(CuratorFramework client, String path) throws Exception {
        client.create().forPath(path);
    }

    /**
     * 在指定路径上创建持久节点，如果父节点不存在则先创建父节点
     *
     * @param client 客户度
     * @param path   节点路径
     * @throws Exception if error occurs
     */
    public static void createParentIfNeed(CuratorFramework client, String path) throws Exception {
        client.create().creatingParentsIfNeeded().forPath(path);
    }

    /**
     * 如果ZNode节点不存在，才会创建
     *
     * @param client 客户端
     * @param path   节点路径
     * @throws Exception if error occurs
     */
    public static synchronized void createIfNotExist(CuratorFramework client, String path) throws Exception {
        if (!checkExist(client, path)) {
            createParentIfNeed(client, path);
        }
    }

    /**
     * 在给定的路径和内容创建ZNode持久节点
     *
     * @param client  ZK client
     * @param path    节点路径
     * @param payload 节点数据
     * @throws Exception if error occurs
     */
    public static void create(CuratorFramework client, String path, byte[] payload) throws Exception {
        client.create().forPath(path, payload);
    }

    /**
     * 创建临时节点
     *
     * @param client 客户端
     * @param path   节点路径
     * @throws Exception if error occurs
     */
    public static void createEphemeral(CuratorFramework client, String path) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, null);
    }

    public static void createEphemeralIfNotExist(CuratorFramework client, String path) throws Exception {
        if (!checkExist(client, path)) {
            try {
                createEphemeral(client, path);
            } catch (Exception e) {
                Thread.sleep(1);
                if (!checkExist(client, path)) {
                    createEphemeral(client, path);
                }
            }
        }
    }

    /**
     * 创建有序临时节点
     *
     * @param client  客户端
     * @param path    节点路径
     * @param payload 节点数据
     * @return 有序临时节点
     * @throws Exception if error occurs
     */
    public static String createEphemeralSequential(CuratorFramework client, String path, byte[] payload) throws Exception {
        return client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    /**
     * 为指定的ZNode节点赋值
     *
     * @param client 客户端
     * @param path   节点路径
     * @param data   节点数据
     * @throws Exception if error occurs
     */
    public static void setData(CuratorFramework client, String path, String data) throws Exception {
        byte[] payLoad = SerializationUtils.serialize(data);
        client.setData().forPath(path, payLoad);
    }

    /**
     * 获取指定path的ZNode节点的数据
     *
     * @param client the zk client
     * @param path   the given path
     * @return data in the ZNode.
     * @throws Exception if error occurred.
     */
    public static String getData(CuratorFramework client, String path) throws Exception {
        byte[] payLoad = client.getData().forPath(path);
        if (null != payLoad) {
            return new String(payLoad);
        }
        return null;
    }

    /**
     * 在后台通过listener以异步方式为ZNode节点设置数据
     *
     * @param client  客户端
     * @param path    节点路径
     * @param payload 节点数据
     * @throws Exception if error occurs
     */
    public static void setDataAsync(CuratorFramework client, String path, byte[] payload) throws Exception {
        // 创建时间监听或通知的方法
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                // 处理事件的详情
            }
        };
        client.getCuratorListenable().addListener(listener);
        client.setData().inBackground().forPath(path, payload);
    }

    /**
     * 带回调方式的异步设置ZNode节点数据
     *
     * @param client   客户端
     * @param callback 回调方法
     * @param path     节点路径
     * @param payload  节点数据
     * @throws Exception if error occurs
     */
    public static void setDataAsyncWithCallback(CuratorFramework client, BackgroundCallback callback, String path, byte[] payload) throws Exception {
        client.setData().inBackground(callback).forPath(path, payload);
    }

    /**
     * 删除ZNode节点
     *
     * @param client 客户端
     * @param path   节点路径
     * @throws Exception if error occurs
     */
    public static void delete(CuratorFramework client, String path) throws Exception {
        client.delete().forPath(path);
    }

    /**
     * 确保删除ZNode节点
     *
     * @param client 客户端
     * @param path   节点路径
     * @throws Exception if error occurs
     */
    public static void guaranteedDelete(CuratorFramework client, String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    /**
     * 获取子节点并设置一个监控点
     *
     * @param client 客户端
     * @param path   节点路径
     * @return 子节点
     * @throws Exception if error occurs
     */
    public static List<String> watchedGetChildren(CuratorFramework client, String path) throws Exception {
        return client.getChildren().watched().forPath(path);
    }

    /**
     * 获取子节点并给节点设置给定的监控点
     *
     * @param client  客户端
     * @param path    节点路径
     * @param watcher 监控点
     * @return 子节点
     * @throws Exception if error occurs
     */
    public static List<String> watchedGetChildren(CuratorFramework client, String path, Watcher watcher) throws Exception {
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }
}

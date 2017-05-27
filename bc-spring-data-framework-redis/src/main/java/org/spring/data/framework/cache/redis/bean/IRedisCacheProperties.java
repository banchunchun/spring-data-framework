package org.spring.data.framework.cache.redis.bean;


public interface IRedisCacheProperties{
    int getDatabase();


    String getPassword();


    int getTimeout();

    int getExpireSeconds();


    int getPoolMaxTotal();


    int getPoolMaxIdle();


    int getPoolMaxWaitMillis();

    int getPoolMinIdle();


    String getSentinelMaster();//哨兵模式


    String getSentinelNodes();//哨兵模式

    String getClusterNodes();//集群模式，多个使用逗号分隔

    int getClusterScanInterval();//每隔多少毫秒扫描集群状态

    String getHost();//单机模式

    String getPort();//单机模式

}

package org.spring.data.framework.cache.redis.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/3/2.
 */
@ConfigurationProperties(prefix = "bc.data.redis")
public class DefaultRedisCacheProperties implements IRedisCacheProperties{
    private int             database = 0;
    private String          host;
    private String          port;
    private String          password;
    private int             timeout = 5;
    private int             expireSeconds = 1800;

    private int             poolMaxTotal = 1;
    private int             poolMaxIdle = 1;
    private int             poolMaxWaitMillis = 1;
    private int             poolMinIdle = 0;

    private String          sentinelMaster;
    private String          sentinelNodes;

    @Override
    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    @Override
    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    @Override
    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    @Override
    public int getPoolMaxWaitMillis() {
        return poolMaxWaitMillis;
    }

    public void setPoolMaxWaitMillis(int poolMaxWaitMillis) {
        this.poolMaxWaitMillis = poolMaxWaitMillis;
    }

    @Override
    public int getPoolMinIdle() {
        return poolMinIdle;
    }

    public void setPoolMinIdle(int poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
    }

    @Override
    public String getSentinelMaster() {
        return sentinelMaster;
    }

    public void setSentinelMaster(String sentinelMaster) {
        this.sentinelMaster = sentinelMaster;
    }

    @Override
    public String getSentinelNodes() {
        return sentinelNodes;
    }

    public void setSentinelNodes(String sentinelNodes) {
        this.sentinelNodes = sentinelNodes;
    }

    @Override
    public String getClusterNodes() {
        return null;
    }

    @Override
    public int getClusterScanInterval() {
        return 0;
    }
}

package org.spring.data.framework.mongo.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2017/3/3.
 */
@ConfigurationProperties(prefix = "bc.data.mongodb")
public class DefaultMongoProperties implements IMongoProperties {
    private String          defaultDatabase;
    private String          username;
    private String          password;
    private int             connectionPerHost = 100;
    private int             connectionTimeout = 30000;
    private int             maxWaitTime = 30000;
    private String          hosts;

    @Override
    public String getDefaultDatabase() {
        return defaultDatabase;
    }

    public void setDefaultDatabase(String defaultDatabase) {
        this.defaultDatabase = defaultDatabase;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getConnectionPerHost() {
        return connectionPerHost;
    }

    public void setConnectionPerHost(int connectionPerHost) {
        this.connectionPerHost = connectionPerHost;
    }

    @Override
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    @Override
    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }
}

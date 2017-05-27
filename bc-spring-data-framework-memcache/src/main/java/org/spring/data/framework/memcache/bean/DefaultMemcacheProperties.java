package org.spring.data.framework.memcache.bean;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "bc.data.memcached")
public class DefaultMemcacheProperties implements IMemcacheProperties{

    private String          hosts;
    private int             sessionIdleTimeoutMillis = 10000;
    private int             defaultExpireSeconds = 1800;
    private String          username;
    private String          password;


    @Override
    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    @Override
    public int getSessionIdleTimeoutMillis() {
        return sessionIdleTimeoutMillis;
    }

    public void setSessionIdleTimeoutMillis(int sessionIdleTimeoutMillis) {
        this.sessionIdleTimeoutMillis = sessionIdleTimeoutMillis;
    }

    @Override
    public int getDefaultExpireSeconds() {
        return defaultExpireSeconds;
    }

    public void setDefaultExpireSeconds(int defaultExpireSeconds) {
        this.defaultExpireSeconds = defaultExpireSeconds;
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
}

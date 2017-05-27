package org.spring.data.framework.memcache.service;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;


//@Component
public class MemcachedServiceImpl implements MemcachedService{
    public static final int DEFAULT_EXPIRE_SECONDS = 1800;
//    @Resource(name = "memcacheClient")
    private MemcachedClient client;

    public MemcachedServiceImpl(MemcachedClient client) {
        this.client = client;
    }

    @Override
    public void set(String key, Object value) {
        try {
            client.set(key,DEFAULT_EXPIRE_SECONDS,value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(String key, Object value, int expireSeconds) {
        try {
            client.set(key,expireSeconds,value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setByNS(String ns, String key, Object value) {
        client.beginWithNamespace(ns);
        try {
            set(key,value);
        }finally {
            client.endWithNamespace();
        }
    }

    @Override
    public void setByNS(String ns, String key, Object value, int expireSeconds) {
        client.beginWithNamespace(ns);
        try {
            set(key,value,expireSeconds);
        }finally {
            client.endWithNamespace();
        }
    }

    @Override
    public <T> T get(String key) {
        try {
            return client.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Map<String, T> get(List<String> keys) {
        try {
            return client.get(keys);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T getByNS(String ns, String key) {
        client.beginWithNamespace(ns);
        try {
            return get(key);
        }finally {
            client.endWithNamespace();
        }
    }

    @Override
    public <T> Map<String, T> getByNS(String ns, List<String> keys) {
        client.beginWithNamespace(ns);
        try {
            return get(keys);
        }finally {
            client.endWithNamespace();
        }
    }

    @Override
    public boolean delete(String key) {
        try {
            return client.delete(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByNS(String ns, String key) {
        client.beginWithNamespace(ns);
        try {
            return delete(key);
        }finally {
            client.endWithNamespace();
        }
    }
}

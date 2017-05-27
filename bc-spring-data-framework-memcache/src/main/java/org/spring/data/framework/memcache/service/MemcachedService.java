package org.spring.data.framework.memcache.service;

import java.util.List;
import java.util.Map;

/**
 */
public interface MemcachedService {
    /**
     * 设置一个值
     * @param key   键
     * @param value 值
     */
    void set(String key, Object value);

    /**
     * 设置一个值,并指定超时时间
     * @param key
     * @param value
     * @param expireSeconds
     *            过期时间(秒),若小于30天,则认为是从当前时间的相对时间,若大于30天,则认为是一个自unix epoch的绝对时间
     */
    void set(String key, Object value, int expireSeconds);


    /**
     * 获取一个值
     * @param key   键
     * @return      值
     */
    <T> T get(String key);



    /**
     * 批量获取Key
     * @param keys
     * @return
     */
    <T> Map<String, T> get(List<String> keys);

    /**
     * 使用命名空间设置
     * @param ns    命名空间
     * @param key   键
     * @param value 值
     */
    void setByNS(String ns, String key, Object value);

    /**
     *使用命名空间设置
     * @param ns            命名空间
     * @param key           键
     * @param value         值
     * @param expireSeconds 失效时间
     */
    void setByNS(String ns, String key, Object value, int expireSeconds);

    /**
     * 根据命名空间获取一个值
     * @param ns
     * @param key
     * @return 值
     */
    <T> T getByNS(String ns, String key);



    /**
     * 根据命名空间批量获取值
     * @param ns
     * @param keys
     * @return
     */
    <T> Map<String, T> getByNS(String ns, List<String> keys);


    /**
     * 删除一个键
     * @param key
     */
    boolean delete(String key);

    /**
     * 根据命名空间删除
     * @param ns
     * @param key
     * @return
     */
    boolean deleteByNS(String ns, String key);
}

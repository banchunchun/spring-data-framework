package org.spring.data.framework.memcache.bean;


/**
 * Company        :   mamahao.com
 * author         :   guxiaolong
 * Date           :   2016/7/8
 * Time           :   10:12
 * Description    :
 */
public interface IMemcacheProperties {

    String getHosts();


    int getSessionIdleTimeoutMillis();



    int getDefaultExpireSeconds();



    String getUsername();



    String getPassword();

}

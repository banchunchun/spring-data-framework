package org.spring.data.framework.memcache;

import com.google.common.collect.Lists;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import org.apache.commons.lang3.StringUtils;
import org.spring.data.framework.common.utils.SeparatorParser;
import org.spring.data.framework.memcache.bean.DefaultMemcacheProperties;
import org.spring.data.framework.memcache.bean.IMemcacheProperties;
import org.spring.data.framework.memcache.service.MemcachedService;
import org.spring.data.framework.memcache.service.MemcachedServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-27
 * Time:  上午 9:35.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class MemcachedConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public IMemcacheProperties memcacheProperties(){
        return new DefaultMemcacheProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public MemcachedService memcachedService(IMemcacheProperties properties){
        return new MemcachedServiceImpl(memcachedClient(properties));
    }


    @Bean(name = "memcacheClient")
    @ConditionalOnMissingBean(name = "memcacheClient")
    public MemcachedClient memcachedClient(IMemcacheProperties properties){
        MemcachedClient client = null;
        try {
            client = clientBuilder(properties).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    public MemcachedClientBuilder clientBuilder(IMemcacheProperties properties){
        List<InetSocketAddress> addresses = buildInetSocketAddress(properties);
        XMemcachedClientBuilder clientBuilder = new XMemcachedClientBuilder(addresses);
        clientBuilder.setSessionLocator(new KetamaMemcachedSessionLocator());

        String username = properties.getUsername();
        String password = properties.getPassword();
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            Map<InetSocketAddress, AuthInfo> authInfoMap = new HashMap();
            for (InetSocketAddress address : addresses) {
                authInfoMap.put(address, AuthInfo.plain(username, password));
            }
            clientBuilder.setAuthInfoMap(authInfoMap);
        }
        clientBuilder.getConfiguration().setSessionIdleTimeout(properties.getSessionIdleTimeoutMillis());
        return clientBuilder;
    }

    private  List<InetSocketAddress> buildInetSocketAddress(IMemcacheProperties properties) {
        if(StringUtils.isBlank(properties.getHosts())){
            throw new IllegalArgumentException("Please configure at least one host");
        }
        List<InetSocketAddress> addressList = Lists.newArrayList();
        String hosts = properties.getHosts();
        List<SeparatorParser.SplitItem> items = SeparatorParser.split2List(hosts, ",", ":");
        for (SeparatorParser.SplitItem host : items) {
            InetSocketAddress address = new InetSocketAddress(host.getFront(),Integer.valueOf(host.getBehind()));
            addressList.add(address);
        }
        return addressList;
    }
}

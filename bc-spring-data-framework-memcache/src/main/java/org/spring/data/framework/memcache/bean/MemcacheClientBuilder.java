package org.spring.data.framework.memcache.bean;

import com.google.common.collect.Lists;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import org.apache.commons.lang3.StringUtils;
import org.spring.data.framework.common.utils.SeparatorParser;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Company        :   mamahao.com
 * author         :   guxiaolong
 * Date           :   2016/10/26
 * Time           :   14:53
 * Description    :
 */
public class MemcacheClientBuilder extends XMemcachedClientBuilder {
    private IMemcacheProperties properties;
    private static  List<InetSocketAddress> addressList;

    public MemcacheClientBuilder(IMemcacheProperties properties){
        super(buildInetSocketAddress(properties));
        this.setSessionLocator(new KetamaMemcachedSessionLocator());

        String username = properties.getUsername();
        String password = properties.getPassword();
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            Map<InetSocketAddress, AuthInfo> authInfoMap = new HashMap();
            for (InetSocketAddress address : addressList) {
                authInfoMap.put(address, AuthInfo.plain(username, password));
            }
            this.setAuthInfoMap(authInfoMap);
        }
        this.getConfiguration().setSessionIdleTimeout(properties.getSessionIdleTimeoutMillis());
    }


    private static List<InetSocketAddress> buildInetSocketAddress(IMemcacheProperties properties) {
        if(StringUtils.isBlank(properties.getHosts())){
            throw new IllegalArgumentException("Please configure at least one host");
        }
        addressList = Lists.newArrayList();
        String hosts = properties.getHosts();
        List<SeparatorParser.SplitItem> items = SeparatorParser.split2List(hosts, ",", ":");
        for (SeparatorParser.SplitItem host : items) {
            InetSocketAddress address = new InetSocketAddress(host.getFront(),Integer.valueOf(host.getBehind()));
            addressList.add(address);
        }
        return addressList;
    }
}

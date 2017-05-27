package org.spring.data.framework.cache.redis.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.spring.data.framework.common.utils.SeparatorParser;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.List;

/**

 * Description    :   缓存框架配置（redis），支持事务
 */
@SuppressWarnings("ALL")
@Configuration
@AutoConfigureAfter(IRedisCacheProperties.class)
public class RedisCacheConfiguration extends CachingConfigurerSupport {
//    @Autowired
//    private IRedisCacheProperties properties;


    @Bean
    @ConditionalOnMissingBean
    public IRedisCacheProperties redisCacheProperties(){
        return new DefaultRedisCacheProperties();
    }

    @Bean(name = "redisCacheFactory")
    @Primary
    public JedisConnectionFactory redisConnectionFactory(IRedisCacheProperties properties){
        JedisConnectionFactory factory;
        //集群模式
        if(StringUtils.isNotBlank(properties.getClusterNodes())){
            factory = createClusterFactory(properties);
        }
        //哨兵模式
        else if(StringUtils.isNotBlank(properties.getSentinelMaster())
                && StringUtils.isNotBlank(properties.getSentinelNodes())){
            factory = createSentinelFactory(properties);
        }
        //单机模式
        else if(StringUtils.isNotBlank(properties.getHost())
                && StringUtils.isNotBlank(properties.getPort())){
            factory = createSingleHostFactory(properties);
        }
        else {
            throw new RuntimeException("redis 配置不正确!");
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        //最大空闲连接数
        poolConfig.setMaxIdle(properties.getPoolMaxIdle());
        //最小空闲连接数
        poolConfig.setMinIdle(properties.getPoolMinIdle());
        //池中持有的最大连接数
        poolConfig.setMaxTotal(properties.getPoolMaxTotal());
        //borrowObject 方法的最大等待时间
        poolConfig.setMaxWaitMillis(properties.getPoolMaxWaitMillis());
        //池中可用资源耗尽时, borrow 方法是否阻塞等待 maxWaitMillis 毫秒
        poolConfig.setBlockWhenExhausted(true);

        //borrowObject 时是否执行检测
        poolConfig.setTestOnBorrow(true);
        //是否检测空闲连接链接的有效性, 间隔时间为 timeBetweenEvictionRunsMillis
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTestOnCreate(false);
        poolConfig.setTestOnReturn(false);

        //空闲对象被清除需要达到的最小空闲时间
        poolConfig.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);
        //空闲检测线程,sleep 间隔多长时间,去处理与idle相关的事情
        poolConfig.setTimeBetweenEvictionRunsMillis(-1L);

        factory.setPoolConfig(poolConfig);

        factory.setDatabase(properties.getDatabase());
        factory.setTimeout(properties.getTimeout());
        factory.setUsePool(true);

        if(StringUtils.isNotBlank(properties.getPassword())){
            factory.setPassword(properties.getPassword());
        }
        return factory;
    }

    private JedisConnectionFactory createClusterFactory(IRedisCacheProperties properties) {
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        String nodes = properties.getClusterNodes();
        List<SeparatorParser.SplitItem> items = SeparatorParser.split2List(nodes, ",", "");
        for (SeparatorParser.SplitItem item : items) {
            config.addClusterNode(new RedisNode(item.getFront(), Integer.valueOf(item.getBehind())));
        }
        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        return factory;
    }

    private JedisConnectionFactory createSentinelFactory(IRedisCacheProperties properties) {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.setMaster(properties.getSentinelMaster());
        String nodes = properties.getSentinelNodes();
        List<SeparatorParser.SplitItem> items = SeparatorParser.split2List(nodes, ",", ":");
        for (SeparatorParser.SplitItem item : items) {
            config.addSentinel(new RedisNode(item.getFront(), Integer.valueOf(item.getBehind())));
        }
        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        return factory;
    }

    private JedisConnectionFactory createSingleHostFactory(IRedisCacheProperties properties) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(properties.getHost());
        factory.setPort(Integer.valueOf(properties.getPort()));
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(IRedisCacheProperties properties) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate(properties));
        cacheManager.setDefaultExpiration(properties.getExpireSeconds());
        return cacheManager;
    }

    @Bean(name = "redisCacheTemplate")
    public StringRedisTemplate redisTemplate(IRedisCacheProperties properties){
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory(properties));
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setEnableDefaultSerializer(false);
//        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setKeySerializer(new GenericToStringSerializer(Object.class));
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(new GenericToStringSerializer(Object.class));
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 自定义key生成器
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append("_").append(method.getName());
                for (Object obj : params) {
                    sb.append("_").append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}

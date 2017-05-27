package org.spring.data.framework.mongo;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.lang3.StringUtils;
import org.spring.data.framework.common.utils.SeparatorParser;
import org.spring.data.framework.mongo.bean.DefaultMongoProperties;
import org.spring.data.framework.mongo.bean.IMongoProperties;
import org.spring.data.framework.mongo.service.MongoService;
import org.spring.data.framework.mongo.service.MongoServiceImpl;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-27
 * Time:  上午 9:42.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("ALL")
@Configuration
@ConditionalOnClass(MongoClient.class)
@ConditionalOnMissingBean(type = "org.springframework.data.mongodb.MongoDbFactory")
public class MongoConfiguration implements DisposableBean {

    @Autowired(required = false)
    private MongoClientOptions options;

    private MongoClient mongoClient;
//    private MongoClient mongoClient4Read;

    @Bean
    @ConditionalOnMissingBean
    public IMongoProperties mongoProperties(){
        return new DefaultMongoProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoService mongoService(IMongoProperties properties) throws Exception {
        return new MongoServiceImpl(mongoTemplate(properties),mongoClient(properties));
    }


    @Bean(name = "mongoClient")
    @ConditionalOnMissingBean(name = "mongoClient")
    public MongoClient mongoClient(IMongoProperties properties) throws UnknownHostException {
        MongoClient mongoClient = createMongoClient(this.options,properties);
        return mongoClient;
    }

//    @Bean(name = "mongoClient4Read")
//    @ConditionalOnMissingBean(name = "mongoClient4Read")
//    public MongoClient mongoClient4Read() throws UnknownHostException {
//        MongoClient mongoClient = createMongoClient(this.options);
//        mongoClient.setReadPreference(TaggableReadPreference.secondaryPreferred());
//        return mongoClient;
//    }


    @Bean(name = "mongoTemplate")
    @Primary
    @ConditionalOnMissingBean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate(IMongoProperties properties) throws Exception {
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(properties),properties.getDefaultDatabase());
        return mongoTemplate;
    }
//
//    @Bean(name = "mongoTemplate4Read")
//    @ConditionalOnMissingBean(name = "mongoTemplate4Read")
//    public MongoTemplate mongoTemplate4Read() throws Exception {
//        final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient4Read(),properties.getDefaultDatabase());
//        return mongoTemplate;
//    }

    public MongoClient createMongoClient(MongoClientOptions options,IMongoProperties properties){
        MongoClient client = null;
        boolean needAuth = false;
        if(options == null){
            options = createClientOptions(properties);
        }
        if(StringUtils.isNotBlank(properties.getUsername()) && StringUtils.isNotBlank(properties.getPassword())){
            needAuth = true;
        }
        List<ServerAddress> adresses = createServerAddresses(properties);
        List<MongoCredential> mongoCredentials = createCredentialsList();

        client = new MongoClient(adresses,mongoCredentials,options);
        return client;
    }

    private List<ServerAddress> createServerAddresses(IMongoProperties properties) {
        String hosts = properties.getHosts();
        List<SeparatorParser.SplitItem> items = SeparatorParser.split2List(hosts, ",", ":");
        List<ServerAddress> addresses = Lists.newArrayList();
        for (SeparatorParser.SplitItem item : items) {
            ServerAddress address = new ServerAddress(item.getFront(),Integer.valueOf(item.getBehind()));
            addresses.add(address);
        }
        return addresses;
    }


    private  MongoClientOptions createClientOptions(IMongoProperties properties) {
        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClientOptions.Builder builder = options.builder();
        builder.maxWaitTime(properties.getMaxWaitTime());
        builder.connectionsPerHost(properties.getConnectionPerHost());
        builder.connectTimeout(properties.getConnectionTimeout());
        builder.socketKeepAlive(true);
        options = builder.build();
        return options;
    }

    private List<MongoCredential> createCredentialsList() {

        return Lists.newArrayList();
    }

    @Override
    public void destroy() throws Exception {
        if(mongoClient != null){
            mongoClient.close();
        }
//        if(mongoClient4Read != null){
//            mongoClient4Read.close();
//        }
    }
}

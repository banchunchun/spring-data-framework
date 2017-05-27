package org.spring.data.framework.mongo;

import com.mongodb.MongoClient;
import org.spring.data.framework.mongo.bean.IMongoProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-27
 * Time:  上午 9:42.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class MongoDataConfiguration {


    @Autowired
    private IMongoProperties properties;

    private final ApplicationContext applicationContext;


    public MongoDataConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @ConditionalOnMissingBean(MongoDbFactory.class)
    public SimpleMongoDbFactory mongoDbFactory(MongoClient mongo) throws Exception {
        String database = properties.getDefaultDatabase();
        return new SimpleMongoDbFactory(mongo,database);
    }


    @Bean
    @ConditionalOnMissingBean
    public CustomConversions customConversions() {
        return new CustomConversions(Collections.emptyList());
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoMappingContext mongoMappingContext(BeanFactory beanFactory,
                                                   CustomConversions conversions) throws ClassNotFoundException {
        MongoMappingContext context = new MongoMappingContext();
        context.setInitialEntitySet(new EntityScanner(this.applicationContext)
                .scan(Document.class, Persistent.class));
//        Class<?> strategyClass = this.properties.getFieldNamingStrategy();
        Class<?> strategyClass = null;
        if (strategyClass != null) {
            context.setFieldNamingStrategy(
                    (FieldNamingStrategy) BeanUtils.instantiate(strategyClass));
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }


    @Bean
    @ConditionalOnMissingBean(MongoConverter.class)
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
                                                       MongoMappingContext context, BeanFactory beanFactory,
                                                       CustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver,
                context);
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MongoConverter converter) throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory,converter);
    }
}

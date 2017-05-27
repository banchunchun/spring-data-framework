package org.spring.data.framework.mongo.service;

import com.mongodb.DBCollection;
import org.springframework.data.mongodb.core.MongoTemplate;


public interface MongoService {
    DBCollection getDefaultDBCollection(String collectionName);
    MongoTemplate getTemplate();
    <T> T findById(Object id, Class<T> clazz);

    DBCollection getCollection(String database, String collectionName);
//    DBCollection getDefaultDBCollection4Read(String collectionName);
//    DBCollection getCollection4Read(String database, String collectionName);
//    MongoTemplate getTemplate4Read();
}

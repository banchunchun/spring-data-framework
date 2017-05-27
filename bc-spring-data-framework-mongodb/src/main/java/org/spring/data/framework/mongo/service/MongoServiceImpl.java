package org.spring.data.framework.mongo.service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.core.MongoTemplate;



public class MongoServiceImpl implements MongoService {
//    @Resource(name = "mongoTemplate")
//    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoServiceImpl(MongoTemplate mongoTemplate, MongoClient mongoClient) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClient = mongoClient;
    }

    //    @Resource(name = "mongoTemplate4Read")
//    private MongoTemplate mongoTemplate4Read;

//    @Resource(name = "mongoClient")
//    @Autowired
    private MongoClient mongoClient;

//    @Resource(name = "mongoClient4Read")
//    private MongoClient mongoClient4Read;

    @Override
    public <T> T findById(Object id, Class<T> clazz) {
        return mongoTemplate.findById(id,clazz);
    }

    @Override
    public MongoTemplate getTemplate() {
        return mongoTemplate;
    }


    @Override
    public DBCollection getDefaultDBCollection(String collectionName) {
        DBCollection collection = mongoTemplate.getDb().getCollection(collectionName);
        return collection;
    }

    @Override
    public DBCollection getCollection(String database, String collectionName) {
        DB db = mongoClient.getDB(database);
        if(db != null){
            return db.getCollection(collectionName);
        }
        return null;
    }


//    @Override
//    public MongoTemplate getTemplate4Read() {
//        return mongoTemplate4Read;
//    }
//
//    @Override
//    public DBCollection getDefaultDBCollection4Read(String collectionName) {
//        mongoClient4Read.setReadPreference(TaggableReadPreference.primaryPreferred());
//        return null;
//    }
//
//    @Override
//    public DBCollection getCollection4Read(String database, String collectionName) {
//        DB db = mongoClient4Read.getDB(database);
//        if(db != null){
//            return db.getCollection(collectionName);
//        }
//        return null;
//    }
}

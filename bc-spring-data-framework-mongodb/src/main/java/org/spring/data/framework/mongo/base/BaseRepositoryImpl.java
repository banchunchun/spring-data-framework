package org.spring.data.framework.mongo.base;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;


public class BaseRepositoryImpl<T,ID extends Serializable>
        extends SimpleMongoRepository<T,ID>
        implements BaseRepository<T,ID> {
    private final MongoOperations mongoOperations;

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mo, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    @Override
    public <S extends T> S saveOrUpdate(S entity) {
        return save(entity);
    }

    @Override
    public MongoOperations getMongoOperations() {
        return this.mongoOperations;
    }
}

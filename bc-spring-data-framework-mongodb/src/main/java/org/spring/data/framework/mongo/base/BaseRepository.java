package org.spring.data.framework.mongo.base;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;


@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable>
        extends MongoRepository<T,ID> {


    <S extends T> S saveOrUpdate(S entity);

    MongoOperations getMongoOperations();
}

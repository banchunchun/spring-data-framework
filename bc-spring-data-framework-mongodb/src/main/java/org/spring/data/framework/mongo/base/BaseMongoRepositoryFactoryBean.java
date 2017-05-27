package org.spring.data.framework.mongo.base;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;


public class BaseMongoRepositoryFactoryBean<T extends MongoRepository<S,ID>,S extends MongoEntity,ID extends Serializable>
        extends MongoRepositoryFactoryBean<T,S,ID> {

    public BaseMongoRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    private static class BaseRepositoryFactory<S extends MongoEntity,ID extends Serializable>
        extends MongoRepositoryFactory{
        private final MongoOperations mongoOperations;
        public BaseRepositoryFactory(MongoOperations mongoOperations) {
            super(mongoOperations);
            this.mongoOperations = mongoOperations;
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            Class<?> repositoryInterface = information.getRepositoryInterface();
            MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            if (isQueryDslRepository(repositoryInterface)) {
                return new QueryDslMongoRepository(entityInformation, mongoOperations);
            } else {
                return new BaseRepositoryImpl((MongoEntityInformation<S, ID>) entityInformation, this.mongoOperations);
            }
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }

        private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
            return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        }
    }
}

package org.spring.data.framework.jpa.bean.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

/**

 */
public class BaseJpaRepositoryFactoryBean<T extends JpaRepository<S,ID>,S extends JPAEntity,ID extends Serializable>
        extends JpaRepositoryFactoryBean<T,S,ID> {
    public BaseJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    private static class BaseRepositoryFactory<S extends JPAEntity,ID extends Serializable>
        extends JpaRepositoryFactory {
        private final EntityManager entityManager;

        public BaseRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            Assert.notNull(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            Class<?> repositoryInterface = metadata.getRepositoryInterface();
            if(isQueryDslExecutor(repositoryInterface)){
                return QueryDslJpaRepository.class;
            }
            return BaseRepositoryImpl.class;
        }

        @Override
        protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            Class<?> repositoryInterface = information.getRepositoryInterface();
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            if(isQueryDslExecutor(repositoryInterface)){
                return new QueryDslJpaRepository(entityInformation,entityManager);
            }
            return new BaseRepositoryImpl(entityInformation,entityManager,repositoryInterface);
        }

        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return getTargetRepository(information,entityManager);
        }

        private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
            return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        }
    }
}

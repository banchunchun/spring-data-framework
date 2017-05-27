package org.spring.data.framework.jpa.bean.base;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**

 */
public class BaseRepositoryImpl<T extends JPAEntity,ID extends Serializable>
        extends SimpleJpaRepository<T,ID>
        implements BaseRepository<T,ID> {
    private final Class<T> domainClass;
    private final EntityManager entityManager;
    private final String   entityName;
    private Class<?> springDataRepositoryInterface;


    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.domainClass = entityInformation.getJavaType();
        this.entityName = entityInformation.getEntityName();
    }

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, Class<?> springDataRepositoryInterface) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.domainClass = entityInformation.getJavaType();
        this.entityName = entityInformation.getEntityName();
        this.springDataRepositoryInterface = springDataRepositoryInterface;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager),entityManager,null);
    }


    /*@Override
    public boolean support(String className) {
        return domainClass.getName().equals(className);
    }

    @Override
    public <S extends T> S saveOrUpdate(S entity) {
        return save(entity);
    }

    @Override
    public <S extends T> S updateSelective(S entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <S extends T> void updateSelective(S... entitys) {
        if(entitys != null){
            updateSelective(Arrays.asList(entitys));
        }
    }

    @Override
    public <S extends T> void updateSelective(List<S> entitys) {
        if(CollectionUtils.isNotEmpty(entitys)){
            for (T entity : entitys) {
                updateSelective(entity);
            }
        }
    }

    @Override
    public int executeUpdateQL(String jpql, Object... values) {
        List<Object> list = Collections.EMPTY_LIST;
        if(values != null){
            list = Arrays.asList(values);
        }
        return executeUpdateQL(jpql,list);
    }

    @Override
    public int executeUpdateQL(String jpql, List<Object> values) {
        Query query = entityManager.createQuery(jpql);
        if(CollectionUtils.isNotEmpty(values)){
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i + 1,values.get(i));
            }
        }
        return query.executeUpdate();
    }

    @Override
    public int executeUpdateQL(String jpql, Map<String, Object> params) {
        Query query = entityManager.createQuery(jpql);
        if(MapUtils.isNotEmpty(params)){
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.executeUpdate();
    }

    @Override
    public <E> E executeQueryOneQL(String jpql, Object... values) {
        List<Object> list = Collections.EMPTY_LIST;
        if(values != null){
            list = Arrays.asList(values);
        }
        return executeQueryOneQL(jpql,list);
    }

    @Override
    public <E> E executeQueryOneQL(String jpql, List<Object> values) {
        Query query = entityManager.createQuery(jpql);
        if(CollectionUtils.isNotEmpty(values)){
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i + 1,values.get(i));
            }
        }
        return (E)query.getSingleResult();
    }

    @Override
    public <E> E executeQueryOneQL(String jpql,Map<String, Object> params) {
        Query query = entityManager.createQuery(jpql);
        if(MapUtils.isNotEmpty(params)){
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return (E)query.getSingleResult();
    }

    @Override
    public <E> List<E> executeQueryListQL(String jpql, Object... values) {
        List<Object> list = Collections.EMPTY_LIST;
        if(values != null){
            list = Arrays.asList(values);
        }
        return executeQueryListQL(jpql,list);
    }

    @Override
    public <E> List<E> executeQueryListQL(String jpql, List<Object> values) {
        Query query = entityManager.createQuery(jpql);
        if(CollectionUtils.isNotEmpty(values)){
            for (int i = 0; i < values.size(); i++) {
                query.setParameter(i + 1,values.get(i));
            }
        }
        return query.getResultList();
    }

    @Override
    public <E> List<E> executeQueryListQL(String jpql,Map<String, Object> params) {
        Query query = entityManager.createQuery(jpql);
        if(MapUtils.isNotEmpty(params)){
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.getResultList();
    }

    @Override
    public Class<T> getDomainClass() {
        return domainClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
*/
    public String getEntityName() {
        return entityName;
    }

    public Class<?> getSpringDataRepositoryInterface() {
        return springDataRepositoryInterface;
    }

    public void setSpringDataRepositoryInterface(Class<?> springDataRepositoryInterface) {
        this.springDataRepositoryInterface = springDataRepositoryInterface;
    }
}

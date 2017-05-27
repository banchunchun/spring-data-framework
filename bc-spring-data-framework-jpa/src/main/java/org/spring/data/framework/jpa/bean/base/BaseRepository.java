package org.spring.data.framework.jpa.bean.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**

 */
@NoRepositoryBean
public interface BaseRepository<T extends JPAEntity,ID extends Serializable>
        extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {
    /*
    *//**
     * 该Repository的领域对象是否为classNamee类型
     * @param className
     * @return
     *//*
    boolean support(String className);

    *//**
     * 保存或更新
     * @param entity
     * @param <S>
     * @return
     *//*
    <S extends T> S saveOrUpdate(S entity);

    *//**
     * 更新实体，仅更新值不为null的变量
     * @param entity
     * @return
     *//*
    <S extends T> S updateSelective(S entity);

    *//**
     * 批量更新
     * @param entitys
     *//*
    <S extends T> void updateSelective(S... entitys);

    *//**
     * 批量更新
     * @param entitys
     *//*
    <S extends T> void updateSelective(List<S> entitys);
    *//**
     * 执行更新语句
     * @param jpql 基于jpa标准的ql语句
     * @param values ql中的?参数值,单个参数值或者多个参数值
     * @return 返回执行后受影响的数据个数
     *//*
    int executeUpdateQL(String jpql, Object... values);
    *//**
     * 执行更新语句，可以是更新或者删除操作
     * @param jpql 基于jpa标准的ql语句
     * @param values ql中的?参数值
     * @return 返回执行后受影响的数据个数
     *//*
    int executeUpdateQL(String jpql, List<Object> values);
    *//**
     * 执行更新语句
     * @param jpql 基于jpa标准的ql语句
     * @param params key表示ql中参数变量名，value表示该参数变量值
     * @return 返回执行后受影响的数据个数
     *//*
    int executeUpdateQL(String jpql, Map<String, Object> params);

    *//**
     * 执行查询一个语句
     * @param jpql
     * @param values
     * @return
     *//*
    <E> E executeQueryOneQL(String jpql, Object... values);
    <E> E executeQueryOneQL(String jpql, List<Object> values);
    <E> E executeQueryOneQL(String jpql,Map<String, Object> params);

    *//**
     * 执行查询多个语句
     * @param jpql
     * @param values
     * @return
     *//*
    <E> List<E> executeQueryListQL(String jpql, Object... values);
    <E> List<E> executeQueryListQL(String jpql, List<Object> values);
    <E> List<E> executeQueryListQL(String jpql,Map<String, Object> params);

    *//**
     * 该Repository的领域对象的类型
     * @return
     *//*
    Class<T> getDomainClass();

    *//**
     * 实体管理器
     * @return
     *//*
    EntityManager getEntityManager();*/
}

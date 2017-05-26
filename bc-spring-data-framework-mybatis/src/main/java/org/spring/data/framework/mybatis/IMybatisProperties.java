package org.spring.data.framework.mybatis;

import org.apache.ibatis.session.ExecutorType;


public interface IMybatisProperties {

    String getConfig();



    String getMapperLocations();



    String getTypeHandlersPackage();



    String getTypeAliasesPackage();



    boolean isCheckConfigLocation();

    boolean isNotEmpty();

    String getMappers();

    String getBasePackage();

    String getIdentity();


    ExecutorType getExecutorType();
}
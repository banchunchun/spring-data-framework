package org.spring.data.framework.mongo.bean;


public interface IMongoProperties {
    int DEFAULT_PORT = 27017;

    String getDefaultDatabase();


    String getUsername();


    String getPassword();



    int getConnectionPerHost();



    int getConnectionTimeout();



    int getMaxWaitTime();



    String getHosts();

}

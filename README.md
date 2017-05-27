#spring data数据中间件
    项目基于spring boot，用于简化SQL/Nosql相关配置。
    由于spring boot 默认情况下配置数据库等都是简单配置，如果需要多数据源、读写
    分离等设置，就需要手动配置。
    此项目所做的就是将这些较复杂配置整理成一个个的jar，使用者直接引入依赖，并配置
    项目的具体数据源，即可使用，避免每个项目都要写一套。
##项目地址
    https://github.com/banchunchun/spring-data-framework/
    
##项目模块

+ bc-data-framework-common
+ bc-data-framework-datasource
+ bc-data-framework-mybatis
+ bc-data-framework-jpa
+ bc-data-framework-mongo
+ bc-data-framework-redis
+ bc-data-framework-memcached
+ bc-data-framework-cache-elasticsearch

###bc-data-framework-common[公共模块]
    此模块包含一些公共的类，暂时没有具体的逻辑。
###bc-data-framework-datasource[数据源模块]
    此模块实现了多数据源配置，支持读写分离、动态数据源切换功能。
###bc-data-framework-mybatis[mybatis支持模块]
    如果使用mybatis作为ORM框架，引入此模块即可直接使用Mapper的方式操作数据库。
###bc-data-framework-jpa[JPA支持模块]
    使用JPA操作数据库，可以和mybatis支持模块同时使用。
###mamahao-data-framework-mongo[MongoDB支持模块]
    配置并实现了MongoService，同时支持获取MongoTemplate、MongoClient、DBCollection以及读写分离操作。
    另外也可以直接使用spring-data的repository方式操作mongo。
###bc-data-framework-redis[Redis支持模块]
    配置并提供了RedisService，同时支持获取RedisTemplate。
###bc-data-framework-memcached[memcached支持模块]
    配置并实现了MemcachedService,支持常用的memcache操作。

###bc-data-framework-cache-elasticsearch[ES搜索支持模块]    
    配置并实现了ElasticsearchService，同时支持获取ElasticsearchTemplate、Client操作。
    目前Client只实现了TransportClient的连接方式，NodeClient连接方式暂未实现
    另外也可以直接使用spring-data的repository方式操作elasticsearch。
    
##如何使用
###加入依赖
**使用mybatis，配置扫描的包路径需要加上org.spring.data.framework**
```
<dependency>
    <groupId>org.spring.data.framework</groupId>
    <artifactId>bc-spring-data-framework-mybatis</artifactId>
    <version>${bc-data-framework-mybatis.version}</version>
</dependency>
```
**使用JPA**
```
<dependency>
    <groupId>org.spring.data.framework</groupId>
    <artifactId>bc-spring-data-framework-jpa</artifactId>
    <version>${bc-data-framework-jpa.version}</version>
</dependency>
```
使用其他模块类似。

###配置文件
    推荐使用yml文件配置
**数据源配置**
```
bc:
        data:
            datasources:
                enabled: true                                                       ##是否开启数据源设置
                default-datasource-type: com.alibaba.druid.pool.DruidDataSource     ##默认数据源类型
                default-datasource-name: default                                    ##默认数据源名称
                groups:                                                             ##数据源组列表，包含自主从结构
                    -
                      group-name: default-group                                     ##组名称
                      items:                                                        ##组下面的数据源列表
                        -
                          name: default                                             ##数据源名称
                          master: true                                              ##是否是主节点
                          type: com.alibaba.druid.pool.DruidDataSource
                          driver-class-name: com.mysql.jdbc.Driver
                          url: jdbc:mysql://172.28.1.6:3306/db_gd_dev?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;rewriteBatchedStatements=true
                          username: root
                          password: mamahao
                        -
                          name: ms0_ds1
                          type: com.alibaba.druid.pool.DruidDataSource
                          driver-class-name: com.mysql.jdbc.Driver
                          url: jdbc:mysql://172.28.1.6:3306/db_gd_dev?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;rewriteBatchedStatements=true
                          username: root
                          password: mamahao
                        -
                          name: ms0_ds2
                          type: com.alibaba.druid.pool.DruidDataSource
                          driver-class-name: com.mysql.jdbc.Driver
                          url: jdbc:mysql://172.28.1.6:3306/db_gd_dev?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;rewriteBatchedStatements=true
                          username: root
                          password: mamahao
                    -
                      group-name: ms1
                      items:
                        -
                          name: ms1_ds0
                          master: true
                          type: com.alibaba.druid.pool.DruidDataSource
                          driver-class-name: com.mysql.jdbc.Driver
                          url: jdbc:mysql://172.28.1.6:3306/db_gd_dev?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;rewriteBatchedStatements=true
                          username: root
                          password: mamahao
                        -
                          name: ms1_ds1
                          type: com.alibaba.druid.pool.DruidDataSource
                          driver-class-name: com.mysql.jdbc.Driver
                          url: jdbc:mysql://172.28.1.6:3306/db_gd_dev?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;rewriteBatchedStatements=true
                          username: root
                          password: mamahao
                    -
                      group-name: ms2
                      items:
                        -
                          name: ms2_ds0
                          master: true
                          type: com.alibaba.druid.pool.DruidDataSource
                          driver-class-name: com.mysql.jdbc.Driver
                          url: jdbc:mysql://172.28.1.6:3306/db_gd_dev?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;rewriteBatchedStatements=true
                          username: root
                          password: mamahao
```

**mybatis配置**
```
bc:
        data:
            mybatis:
                enabled: true                                           ##是否启用mybatis
                mapper-locations: classpath*:mapper/*Mapper.xml         ##mapper文件地址
                type-aliases-package: com.mamahao.actsys.api.entity     ##实体类所在包
                base-package: com.mamahao.actsys.api.mapper             ##Mapper所在包
```
**JPA配置**
```
bc:
        data:
            jpa:
                enabled: true                                       ##是否启用JPA
                show-sql: true                                      ##是否打印SQL
                open-in-viewiew: false                              ##是否开启延迟关闭session
                hibernate:                                          ##hibernte实现的JPA相关设置
                    ddl-auto: update                                ##ddl策略
                    dialect: org.hibernate.dialect.MySQL5Dialect    ##数据库dialect
                entity-packages:                                    ##实体类的包列表
                    -
                        com.mamahao.actsys.api.entity
```
**Mongo配置**
```
bc:
        data:
            mongo:
                enabled: true                       ##是否启用mongo
                defult-database: mydb               ##默认数据库
                username: test              
                password: test123
                connection-per-host: 100            ##每个host最大连接数
                connection-timeout-millis: 30000    ##客户端超时时间（毫秒）
                max-wait-time-millis: 30000         ##客户端最大等待时间（毫秒）
                addrs:                              ##mongo地址列表
                  -
                    host: 172.28.1.3
                    port: 27017
                  -
                    host: 172.28.1.28
                    port: 27017
```
**Redis配置**
```
bc:
        data:
            redis:
                enabled: true                   ##是否启用redis
                default-database: 0             ##默认数据库下标
                password:                       ##密码
                host: 172.28.1.14               ##地址，单机时填写
                port: 6379                      ##端口，单机时填写
                timeout: 100                    ##请求超时时间
                default-expire-seconds: 1800    ##数据失效时间（秒）
                pool:                           ##连接池设置
                  max-idle: 8                   ##最大空闲连接数
                  max-total: 20                 ##最大连接数
                  max-wait-millis: 1000         ##最大等待时间（毫秒）
                  min-idle: 0                   ##最大空闲连接数
                sentinel:                       ##哨兵配置
                  master: redis01               ##主节点名称
                  nodes:                        ##哨兵节点列表
                    -
                      host: 172.28.1.15 
                      port: 6379
                    -
                      host: 172.28.1.16
                      port: 6379
```
**Memcached配置**
```
bc:
        data:
            memcached:
                enabled: true                       ##是否启用memcached
                session-idle-timeout: 10000         ##session最大空闲时间  
                default-cache-name: default_cache   ##默认缓存组名称
                default-expire-seconds: 1800        ##默认缓存时间（秒）
                addrs:                              ##memcache地址列表
                  -
                    host: 172.28.1.129              
                    port: 11211
```
**Elasticsearch配置**
```
mamahao:
  data:
    elasticsearch:
    clusterName: elasticsearch ##集群名
    clusterNodes: 172.28.1.142:3900,172.28.1.144:3900 ##节点 多个以逗号隔开
    clientPingTimeout: 10s ##ping超时时间
    addDeleteByQueryPlugin:true ##是否添加删除插件，默认为false、deleteDocuments相关方法将不能使用
    properties:  ##此属性为官方setttings中属性，配置实现参考spring-data-elasticsearch实现方式
      client.transport.sniff:false
      client.transport.ignore_cluster_name:true
      client.transport.ping_timeout:10s
      client.transport.nodes_sampler_interval:5s
    
```
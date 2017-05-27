# spring-data-framework
基于spring boot开发的数据组件集成库，目前支持：mybatis、JPA、redis、memcached、elasticsearch、mongodb和基于redis或memcached的cache,数据中间件 项目基于spring boot，用于简化SQL/Nosql相关配置。 由于spring boot 默认情况下配置数据库等都是简单配置，如果需要多数据源、读写 分离等设置，就需要手动配置。 此项目所做的就是将这些较复杂配置整理成一个个的jar，使用者直接引入依赖，并配置 项目的具体数据源，即可使用，避免每个项目都要写一套

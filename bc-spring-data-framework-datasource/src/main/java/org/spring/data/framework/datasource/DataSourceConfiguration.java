package org.spring.data.framework.datasource;

import org.spring.data.framework.datasource.bean.DefaultDatasourceProperties;
import org.spring.data.framework.datasource.bean.DynamicDataSource;
import org.spring.data.framework.datasource.bean.IDataSourceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-26
 * Time:  下午 3:05.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class DataSourceConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public IDataSourceProperties dataSourceProperties(){
        return new DefaultDatasourceProperties();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(IDataSourceProperties properties){
        DataSource dataSource = new DynamicDataSource(properties);
        return dataSource;
    }
}

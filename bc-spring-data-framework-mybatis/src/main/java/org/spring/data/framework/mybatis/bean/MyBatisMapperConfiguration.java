package org.spring.data.framework.mybatis.bean;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;


@Configuration
public class MyBatisMapperConfiguration implements EnvironmentAware {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(basePackage);
        Properties conf = new Properties();
        conf.setProperty("mappers", IMapper.class.getName());
        conf.setProperty("notEmpty", "false");
        conf.setProperty("IDENTITY", identity);
        mapperScannerConfigurer.setProperties(conf);
        return mapperScannerConfigurer;
    }


    private String basePackage;
    private String identity;
    @Override
    public void setEnvironment(Environment environment) {
        basePackage = environment.getProperty("bc.data.mybatis.base-package");
        identity = environment.getProperty("bc.data.mybatis.identity");
    }
}

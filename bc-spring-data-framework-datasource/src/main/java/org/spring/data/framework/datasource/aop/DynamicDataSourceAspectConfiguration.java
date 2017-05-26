package org.spring.data.framework.datasource.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-26
 * Time:  下午 3:07.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Configuration
public class DynamicDataSourceAspectConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspectConfiguration.class);

}

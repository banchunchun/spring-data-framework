package org.spring.data.framework.datasource.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.data.framework.datasource.helper.DynamicContextHolder;
import org.spring.data.framework.datasource.helper.TargetDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

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

    @Around(value = "execution(public * *(..)) && @annotation(ts)")
    public Object aroundForTarget(ProceedingJoinPoint pjp, TargetDataSource ts) throws Throwable {
        Object res;
        try {
            setCurrentDateSource(ts);
            res = pjp.proceed();
        }catch (Throwable e){
            throw e;
        }finally {
            resetCurrentDataSource();
        }
        return res;
    }

    private void resetCurrentDataSource() {
        DynamicContextHolder.clearCurrentDataSource();
    }

    private void setCurrentDateSource(TargetDataSource ts) {
        logger.info("==================================TargetDataSource=====================================");
        if(ts != null){
            String name = ts.name();
            if(StringUtils.isBlank(name)){
                name = ts.value();
            }
            if(DynamicContextHolder.containsDataSource(name)){
                String currentDataSource = DynamicContextHolder.getCurrentDataSource();
                if(ts.forceChange() || StringUtils.isBlank(currentDataSource)){
                    DynamicContextHolder.setCurrentDataSource(name);
                }
            }
        }
    }


    private TargetDataSource getTargetDataSource(JoinPoint jp, Method method) {
        Object[] args = jp.getArgs();
        Class<?>[] parameterTypes = new Class[args.length];
        if(args.length > 0){
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if(arg != null){
                    parameterTypes[i] = args[i].getClass();
                }
            }
        }
        TargetDataSource targetDataSource = null;
        try {
            Method targetMethod = jp.getTarget().getClass().getDeclaredMethod(method.getName(), parameterTypes);
            if(targetMethod != null){
                targetDataSource = AnnotationUtils.findAnnotation(targetMethod, TargetDataSource.class);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return targetDataSource;
    }
}

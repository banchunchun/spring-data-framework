package org.spring.data.framework.datasource.helper;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TargetDataSource {

    /**
     * 目标数据源名称
     * @return
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 目标数据源名称
     * @return
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 是否强制切换，默认为false，即默认在嵌套调用时只在第一层切换
     * @return
     */
    boolean forceChange() default false;
}

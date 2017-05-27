package org.spring.data.framework.jpa.bean;

/**

 * Description    :     JPA配置类
 */
public interface IJpaProperties {

    boolean isShowSql();

    boolean isFormatSql();


    boolean isOpenInView();



    String getEntityPackages();



    String getHibernateDdlAuto();



    String getHibernateDialect();


}
